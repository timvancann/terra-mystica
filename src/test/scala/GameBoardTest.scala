import TerrainType.TerrainType
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

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
      Bridge(Hex(0, 0), Hex(0, 2)),
      Bridge(Hex(0, 0), Hex(2, 0)),
      Bridge(Hex(0, 2), Hex(2, 1))
    ))
  }

  test("no bridges build for faction") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.bridgesFor(faction).length shouldBe 0
  }

  test("no buildable bridges, no faction on board") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
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
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.placableDwellings(faction).length shouldBe 0
  }

  test("1 free placable dwellings") {
    val faction = Faction(terrain = TerrainType.Forest, dwellingCost = List.empty)
    val placable = gameBoard.placableDwellings(faction)
    placable.length shouldBe 1
    placable.head.terrain shouldBe TerrainType.Forest
  }

  test("three free placable dwellings") {
    val faction = Faction(terrain = TerrainType.Mountains, dwellingCost = List.empty)
    val placable = gameBoard.placableDwellings(faction)
    placable.length shouldBe 3
  }

  test("build dwelling on board, no buildable tiles") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.buildableDwellings(faction).length shouldBe 0
  }

  test("build dwelling on board, one buildable neighbour") {
    val faction = Faction(terrain = TerrainType.Mountains, dwellingCost = List.empty)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head, faction)
    gameBoard.buildableDwellings(faction).length shouldBe 1
  }

  test("build dwelling on board, two buildable neighbour") {
    val faction = Faction(terrain = TerrainType.Mountains, dwellingCost = List.empty)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles(1), faction)
    gameBoard.buildableDwellings(faction).length shouldBe 2
  }

  test("build dwelling on board, no free buildable neighbour") {
    val faction = Faction(terrain = TerrainType.Mountains, dwellingCost = List.empty)
    val tiles = gameBoard.placableDwellings(faction)
    gameBoard.buildDwelling(tiles.head, faction)
    gameBoard.buildDwelling(tiles(1), faction)
    gameBoard.buildDwelling(tiles(2), faction)
    gameBoard.buildableDwellings(faction).length shouldBe 0
  }


  test("no buildable bridges, no bridge at tile") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.buildableBridges(faction).length shouldBe 0
  }

  test("one bridge build for faction") {
    val faction = Faction(terrain = TerrainType.Plains, dwellingCost = List.empty)
    gameBoard.buildDwelling(gameBoard.placableDwellings(faction).head, faction)
    gameBoard.buildBridge(gameBoard.buildableBridges(faction).head, faction)
    gameBoard.bridgesFor(faction).length shouldBe 1
  }

}
