package thw.vancann

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import thw.vancann.BuildingType._
import thw.vancann.TerrainType._
import thw.vancann.FactionType._

import scala.collection.mutable

class GameBoardTest extends FunSuite with Matchers with MockFactory with BeforeAndAfter {

  var gameBoard: GameBoard = _

  before {
    gameBoard = GameBoard(List(
      Tile(Hex(0, 0), TerrainType.Plains),
      Tile(Hex(0, 1), TerrainType.Sea),
      Tile(Hex(0, 2), TerrainType.Swamp),
      Tile(Hex(0, 3), TerrainType.Swamp),

      Tile(Hex(1, 0), TerrainType.Sea),
      Tile(Hex(1, 1), TerrainType.Wasteland),
      Tile(Hex(1, 2), TerrainType.Sea),

      Tile(Hex(2, 0), TerrainType.Forest),
      Tile(Hex(2, 1), TerrainType.Mountains),
      Tile(Hex(2, 2), TerrainType.Mountains),
      Tile(Hex(2, 3), TerrainType.Mountains)
    ), List(
      TileBridge(Hex(0, 0), Hex(0, 2)),
      TileBridge(Hex(0, 0), Hex(2, 0)),
      TileBridge(Hex(0, 2), Hex(2, 1))
    ))
  }

  test("no bridges build for faction") {
    val faction = Faction(FactionType.Halflings, TerrainType.Desert, supply = Defaults.supply)
    gameBoard.bridgesFor(faction).length shouldBe 0
  }

  test("no buildable bridges, no faction on board") {
    val faction = Faction(FactionType.Halflings, TerrainType.Desert, supply = Defaults.supply)
    gameBoard.buildableBridges(faction).length shouldBe 0
  }

  test("valid neighbours of uppper-left tile") {
    val neighs = gameBoard.neighbours(Hex(0, 0))
    neighs.length shouldBe 2
  }

  test("valid neighbours of upper-middle tile") {
    val neighs = gameBoard.neighbours(Hex(0, 1))
    neighs.length shouldBe 4
  }

  test("valid neighbours of upper-right tile") {
    val neighs = gameBoard.neighbours(Hex(0, 3))
    neighs.length shouldBe 2
  }

  test("valid neighbours of middle-left tile") {
    val neighs = gameBoard.neighbours(Hex(1, 0))
    neighs.length shouldBe 5
  }

  test("valid neighbours of middle-middle tile") {
    val neighs = gameBoard.neighbours(Hex(1, 1))
    neighs.length shouldBe 6
  }

  test("valid neighbours of middle-right tile") {
    val neighs = gameBoard.neighbours(Hex(1, 2))
    neighs.length shouldBe 5
  }

  test("valid neighbours of lower-left tile") {
    val neighs = gameBoard.neighbours(Hex(2, 0))
    neighs.length shouldBe 2
  }

  test("valid neighbours of lower-middle tile") {
    val neighs = gameBoard.neighbours(Hex(2, 1))
    neighs.length shouldBe 4
  }

  test("valid neighbours of lower-right tile") {
    val neighs = gameBoard.neighbours(Hex(2, 3))
    neighs.length shouldBe 2
  }

  test("no free placable dwellings") {
    val faction = Faction(FactionType.Halflings, TerrainType.Desert)
    gameBoard.placableDwellings(faction).length shouldBe 0
  }

  test("1 free placable dwellings") {
    val faction = Faction(FactionType.Halflings, TerrainType.Forest)
    val placable = gameBoard.placableDwellings(faction)
    placable.length shouldBe 1
    placable.head.terrain shouldBe TerrainType.Forest
  }

  test("three free placable dwellings") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains)
    val placable = gameBoard.placableDwellings(faction)
    placable.length shouldBe 3
  }

  test("build dwelling on board, no buildable tiles") {
    val faction = Faction(FactionType.Halflings, TerrainType.Desert)
    gameBoard.buildableDwellings(faction).length shouldBe 0
  }

  test("build dwelling on board, one buildable neighbour") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head.hex, faction)
    gameBoard.buildableDwellings(faction).length shouldBe 1
  }

  test("build dwelling on board, check resources spend") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    faction.gain(ResourceType.Worker, 3)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head.hex, faction)
    faction.numberOfTimesResourcesToSpendFor(List((ResourceType.Worker, 1))) shouldBe 2
  }

  test("build dwelling on board, two buildable neighbour") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles(1).hex, faction)
    gameBoard.buildableDwellings(faction).length shouldBe 2
  }

  test("build dwelling on board, no free buildable neighbour") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head.hex, faction)
    gameBoard.buildDwelling(tiles(1).hex, faction)
    gameBoard.buildDwelling(tiles(2).hex, faction)
    gameBoard.buildableDwellings(faction).length shouldBe 0
  }

  test("no buildable bridges, no bridge at tile") {
    val faction = Faction(FactionType.Halflings, TerrainType.Desert)
    gameBoard.buildableBridges(faction).length shouldBe 0
  }

  test("one bridge build for faction") {
    val faction = Faction(FactionType.Halflings, TerrainType.Plains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    gameBoard.buildDwelling(gameBoard.placableDwellings(faction).head.hex, faction)
    gameBoard.buildBridge(gameBoard.buildableBridges(faction).head, faction)
    gameBoard.bridgesFor(faction).length shouldBe 1
  }

  test("upgradable buildings for dwelling") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head.hex, faction)
    gameBoard.upgradableBuildings(faction).length shouldBe 1
  }

  test("upgrade building from dwelling to stronghold") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(Dwelling -> 10, TradingHouse -> 10),
      buildingCost = Map(Dwelling -> List((ResourceType.Worker, 1))), supply = Defaults.supply)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head.hex, faction)
    gameBoard.upgradeBuilding(tiles.head, BuildingType.TradingHouse)
    tiles.head.building shouldBe BuildingType.TradingHouse
  }

  test("terraformable for faction should only return terrain different from home terrain") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains, availableBuildings = mutable.Map(Dwelling -> 10))
    gameBoard.placeDwelling(gameBoard.placableDwellings(faction).head.hex, faction)
    val tiles = gameBoard.terraformableFor(faction)
    tiles.length shouldBe 2
  }

  test("terraformed tile should not show up in terraformable list") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains, availableBuildings = mutable.Map(Dwelling -> 10))
    gameBoard.placeDwelling(gameBoard.placableDwellings(faction).head.hex, faction)
    var tiles = gameBoard.terraformableFor(faction)
    gameBoard.terraform(tiles.head.hex, faction.terrain)
    tiles = gameBoard.terraformableFor(faction)
    tiles.length shouldBe 1
  }

  test("possible power gain for no adjacent faction") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains, availableBuildings = mutable.Map(Dwelling -> 10))
    val tile = gameBoard.placableDwellings(faction).head
    gameBoard.placeDwelling(tile.hex, faction)

    val faction2 = Faction(FactionType.Halflings, TerrainType.Forest, availableBuildings = mutable.Map(Dwelling -> 10))
    gameBoard.possiblePowerGainFor(faction2, tile) shouldBe 0
  }

  test("possible power gain for adjacent faction") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains, availableBuildings = mutable.Map(Dwelling -> 10))
    val tile = gameBoard.placableDwellings(faction).head
    gameBoard.placeDwelling(tile.hex, faction)

    val faction2 = Faction(FactionType.Halflings, TerrainType.Forest, availableBuildings = mutable.Map(Dwelling -> 10))
    gameBoard.placeDwelling(gameBoard.placableDwellings(faction2).head.hex, faction2)
    gameBoard.possiblePowerGainFor(faction2, tile) shouldBe 1
  }

  test("calculate pass bonus for no bonus") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(
        Dwelling -> 10,
        TradingHouse -> 10),
      buildingCost = Map(
        Dwelling -> List((ResourceType.Worker, 1))))

    val tile = gameBoard.placableDwellings(faction).head
    gameBoard.placeDwelling(tile.hex, faction)

    gameBoard.calculatePassBonusFor(faction, List.empty) shouldBe 0
  }

  test("calculate pass bonus for single building") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(
        Dwelling -> 10,
        TradingHouse -> 10),
      buildingCost = Map(
        Dwelling -> List((ResourceType.Worker, 1))))

    val tile = gameBoard.placableDwellings(faction).head
    gameBoard.placeDwelling(tile.hex, faction)

    gameBoard.calculatePassBonusFor(faction, List((Dwelling, 3))) shouldBe 3
  }

  test("calculate pass bonus for multiple buildings") {
    val faction = Faction(FactionType.Halflings, TerrainType.Mountains,
      availableBuildings = mutable.Map(
        Dwelling -> 10,
        TradingHouse -> 10),
      buildingCost = Map(
        Dwelling -> List((ResourceType.Worker, 1))))

    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.placeDwelling(tiles.head.hex, faction)
    gameBoard.placeDwelling(tiles(1).hex, faction)
    gameBoard.placeDwelling(tiles(2).hex, faction)
    gameBoard.upgradeBuilding(tiles.head, TradingHouse)

    gameBoard.calculatePassBonusFor(faction, List((Dwelling, 3), (TradingHouse, 2))) shouldBe 8
  }

  test("cloning unoccupied tile") {
    val tile = Tile(Hex(4, 2), Desert, building = Dwelling)
    val newTile = tile.clone
    tile shouldBe newTile
  }

  test("cloning occupied tile") {
    val tile = Tile(Hex(4, 2), Desert, Halflings, Dwelling)
    val newTile = tile.clone
    tile shouldBe newTile
  }

  test("cloning unbuild bridge") {
    val bridge = TileBridge(Hex(4, 2), Hex(1, 2))
    val newBridge = bridge.clone
    bridge shouldBe newBridge
  }

  test("cloning build bridge") {
    val bridge = TileBridge(Hex(4, 2), Hex(1, 2), Halflings)
    val newBridge = bridge.clone
    bridge shouldBe newBridge
  }
}
