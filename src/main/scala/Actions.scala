import ActionType._
import BuildingType.BuildingType
import CultType.CultType

object Actions {

  def generatePlacePriestOnCult(gameState: GameState, faction: Faction): Seq[(ActionType, (GameState) => GameState)] = {
    val cost = faction.cultCost
    val n = faction.numberOfTimesResourcesToSpendFor(List(cost))
    if (n > 0) {
      val spaces: Seq[(CultType, OrderSpace)] = (for {
        cult <- gameState.cultBoard.cults
        space <- cult._2.availableOrderSpaces
        if cult._2.currentProgress(faction) < 10
      } yield (cult._1, space)) (collection.breakOut)
      spaces.map(s => (PlacePriest, placePriestOnCult(faction, s)))
    } else {
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

  def upgradeBuilding(to: BuildingType, tile: Tile): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.upgradeBuilding(tile, to)
      newState
    }
  }
}
