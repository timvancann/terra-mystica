case class GameState(gameBoard: GameBoard, cultBoard: CultBoard, factions: List[Faction]) {

  override def clone: GameState = {
    GameState(gameBoard.clone, cultBoard.clone, factions.map(_.clone))
  }
}
