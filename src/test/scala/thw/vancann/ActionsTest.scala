package thw.vancann

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import thw.vancann.TerrainType._

class ActionsTest extends FunSuite with Matchers with BeforeAndAfter {

  var gameState: GameState = _

  before {
    val gameBoard = GameBoard(Defaults.tiles, Defaults.bridges)
    val cultBoard = CultBoard(Defaults.cults)
    val factions = List(Defaults.factions.head._2)
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
}
