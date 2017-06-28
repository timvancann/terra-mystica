package thw.vancann

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import thw.vancann.TerrainType._
import thw.vancann.ResourceType._
import thw.vancann.PriestSpaceType._
import thw.vancann.CultType._
import thw.vancann.BuildingType._

class ActionsTest extends FunSuite with Matchers with BeforeAndAfter {

  var gameState: GameState = _

  before {
    val gameBoard = GameBoard(Defaults.tiles, Defaults.bridges)
    val cultBoard = CultBoard(Defaults.cults)
    val factions = Defaults.factions.values.toList
    gameState = GameState(gameBoard, cultBoard, factions)
  }


  test("placing initial dwelling") {
    //Arrange
    val faction = gameState.factions.head
    val tile = gameState.gameBoard.placableDwellings(faction).head
    val action = Actions.placeInitialDwelling(faction, tile)

    //Act
    val newState = action(gameState)

    //Assert
    tile should not be newState.gameBoard.placableDwellings(faction).head
    tile.faction shouldBe null

    val newTile = newState.gameBoard.upgradableBuildings(faction).head
    newTile.faction shouldBe faction.factionType
  }

  test("terraforming a tile") {
    //Arrange
    val faction = gameState.factions.head
    val tile = gameState.gameBoard.placableDwellings(faction).head
    val action = Actions.terraform(tile, Wasteland)

    //Act
    val newState = action(gameState)

    //Assert
    tile.terrain shouldBe Plains
    val newTile = newState.gameBoard.findTileByHex(tile.hex)
    tile.terrain should not be newTile.terrain
    newTile.terrain shouldBe Wasteland
  }

  test("building a dwelling") {
    //Arrange
    val faction = gameState.factions.head
    val tile = gameState.gameBoard.placableDwellings(faction).head
    val action = Actions.buildDwelling(tile, faction)

    //Act
    val newState = action(gameState)

    //Assert
    tile.faction shouldBe null
    val newTile = newState.gameBoard.findTileByHex(tile.hex)
    newTile.faction shouldBe faction.factionType
  }

  test("advance ship track") {
    //Arrange
    val faction = gameState.factions.head
    val action = Actions.shippingTrack(faction)

    //Act
    val newState = action(gameState)

    //Assert
    faction.resourcesToSpend(VictoryPoints) shouldBe 20
    faction.resourcesToSpend(Ship) shouldBe 0
    val newFaction = newState.factions.head
    newFaction.resourcesToSpend(VictoryPoints) shouldBe 20 + faction.victoryPointsPerShipTrack(1)
    newFaction.resourcesToSpend(Ship) shouldBe 1
  }

  test("advance spade track") {
    //Arrange
    val faction = gameState.factions.head
    val action = Actions.spadeTrack(faction)

    //Act
    val newState = action(gameState)

    //Assert
    faction.resourcesToSpend(VictoryPoints) shouldBe 20
    faction.resourcesToSpend(Spade) shouldBe 0
    val newFaction = newState.factions.head
    newFaction.resourcesToSpend(VictoryPoints) shouldBe 20 + faction.victoryPointsPerSpadeTrack(1)
    newFaction.resourcesToSpend(Spade) shouldBe 1
  }

  test("place priest on cult") {
    //Arrange
    val faction = gameState.factions.head
    val action = Actions.placePriestOnCult(faction, (Fire, Bonus2))

    //Act
    val newState = action(gameState)

    //Assert
    newState.cultBoard.cults(Fire).availablePriestSpaces.length shouldBe 4
    newState.cultBoard.cults(Fire).availablePriestSpaces.count(_.priestSpaceType == Bonus2) shouldBe 2
  }

  test("upgrading building no power gain for any faction") {
    //Arrange
    val faction = gameState.factions.head
    val tile = gameState.gameBoard.placableDwellings(faction).head
    gameState.gameBoard.placeDwelling(tile.hex, faction)
    val action = Actions.upgradeBuilding(faction, tile, TradingHouse, List.empty)

    //Act
    val newState = action(gameState)

    //Assert
    gameState.gameBoard.findTileByHex(tile.hex).building shouldBe Dwelling
    newState.gameBoard.findTileByHex(tile.hex).building shouldBe TradingHouse
  }

  test("upgrading building with power gain for a faction") {
    //Arrange
    val faction = gameState.factions.head
    val board = gameState.gameBoard
    val tile = board.placableDwellings(faction).head
    board.placeDwelling(tile.hex, faction)

    val otherFaction = gameState.factions.tail.head
    board.placeDwelling(board.placableDwellings(otherFaction).head.hex, otherFaction)

    val action = Actions.upgradeBuilding(faction, tile, TradingHouse, List(otherFaction.factionType))

    //Act
    val newState = action(gameState)

    //Assert
    gameState.gameBoard.findTileByHex(tile.hex).building shouldBe Dwelling
    newState.gameBoard.findTileByHex(tile.hex).building shouldBe TradingHouse
  }
}
