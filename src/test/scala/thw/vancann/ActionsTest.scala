package thw.vancann

import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import thw.vancann

class ActionsTest extends FunSuite with Matchers with BeforeAndAfter {

  var gameState: GameState = _

  before {
    val gameBoard = GameBoard(Defaults.tiles, Defaults.bridges)
    val cultBoard = CultBoard(Defaults.cults)
    val factions = List(Defaults.factions.head._2)
    gameState = GameState(gameBoard, cultBoard, factions)
  }


  test("placing initial dwelling") {
    val faction = gameState.factions.head
    val tile = gameState.gameBoard.placableDwellings(faction).head
    val action = Actions.placeInitialDwelling(faction, tile)
    val newState = action(gameState)

    tile should not be newState.gameBoard.placableDwellings(faction)
  }
}
