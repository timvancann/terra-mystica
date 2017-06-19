import TerrainType.TerrainType
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

class GameBoardTest extends FunSuite with Matchers with MockFactory with BeforeAndAfter {

  var gameBoard: GameBoard = _

  before {
    gameBoard = GameBoard(Map(
      Hex(0, 0) -> Tile(TerrainType.Plains),
      Hex(0, 1) -> Tile(TerrainType.Sea),
      Hex(0, 2) -> Tile(TerrainType.Swamp),

      Hex(1, 0) -> Tile(TerrainType.Sea),
      Hex(1, 1) -> Tile(TerrainType.Wasteland),

      Hex(2, 0) -> Tile(TerrainType.Forest),
      Hex(2, 1) -> Tile(TerrainType.Lakes),
      Hex(2, 2) -> Tile(TerrainType.Mountains)
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

  test("build dwelling on board, no buildable tiles") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.buildableDwellings(faction).length shouldBe 0
  }

  test("no buildable bridges, no bridge at tile") {
    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = List.empty)
    gameBoard.buildableBridges(faction).length shouldBe 0
  }

//  test("one bridge build for faction") {
//    val faction = Faction(terrain = TerrainType.Desert, dwellingCost = mock[List[Cost]])
//    gameBoard.buildBridge(gameBoard.head, faction)
//    gameBoard.bridgesFor(faction).length shouldBe 1
//  }

}
