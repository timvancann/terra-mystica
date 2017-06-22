package thw.vancann

import thw.vancann.FactionType.Halflings

object Game {

  var state = GameState(
    GameBoard(Defaults.tiles, Defaults.bridges),
    CultBoard(Defaults.cults),
    List(Defaults.factions(Halflings))
  )

  def play() = {

  }

  def applyMove(funs: Seq[(GameState) => GameState], currentState: GameState): GameState = {
    funs.foldLeft(currentState)((state, fun) => fun(state))
  }
}
