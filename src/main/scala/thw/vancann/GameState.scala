package thw.vancann

case class GameState(gameBoard: GameBoard, cultBoard: CultBoard, factions: List[Faction]) {

  factions.foreach(cultBoard.addFaction)

  override def clone: GameState = {
    GameState(gameBoard.clone, cultBoard.clone, factions.map(_.clone))
  }
}
