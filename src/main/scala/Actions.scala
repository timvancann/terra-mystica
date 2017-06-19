import CultType.CultType

object Actions {

  def generatePlacePriestOnCult(gameState: GameState, faction: Faction): Seq[(GameState) => GameState] = {
    val cost = faction.costForCult
    val n = faction.numberOfTimesResourcesToSpendFor(List(cost))
    if (n > 0) {
      val spaces: Seq[(CultType, OrderSpace)] = (for {
              cult <- gameState.cultBoard.cults
              space <- cult._2.availableOrderSpaces
              if cult._2.currentProgress(faction) < 10
            } yield (cult._1, space))(collection.breakOut)
      spaces.map(s => placePriestOnCult(faction, s))
    }
    else {
      List.empty
    }
  }

  def placePriestOnCult(faction: Faction, where: (CultType, OrderSpace)): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.cultBoard.placePriest(faction, where)
      newState
    }
  }

  def upgradeBuilding(gameState: GameState, faction: Faction) = ???

}

case class DummyF() extends Faction {
  override def costForTerraform: Cost = Cost(ResourceType.Worker, 3)

  override def costForDwelling: List[Cost] = List(Cost(ResourceType.Worker, 2), Cost(ResourceType.Coin, 3))

  override def costForTradingHouse: List[Cost] = List(Cost(ResourceType.Worker, 2), Cost(ResourceType.Coin, 3))
}
