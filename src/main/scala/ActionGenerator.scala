import ActionType.ActionType
import CultType.CultType

object ActionGenerator {

  def generateAll(gameState: GameState, faction: Faction): Seq[(GameState) => GameState] = {
    List(
      generatePlacePriestOnCult(gameState, faction),
      generatePlaceInitialDwellings(gameState, faction)
    )
    .flatten
  }

  def generatePlacePriestOnCult(gameState: GameState, faction: Faction): Seq[(GameState) => GameState] = {
    val cost = faction.cultCost
    val n = faction.numberOfTimesResourcesToSpendFor(List(cost))
    if (n > 0) {
      val spaces: Seq[(CultType, OrderSpace)] = (for {
        cult <- gameState.cultBoard.cults
        space <- cult._2.availableOrderSpaces
        if cult._2.currentProgress(faction) < 10
      } yield (cult._1, space)) (collection.breakOut)
      spaces.map(s => Actions.placePriestOnCult(faction, s))
    } else {
      List.empty
    }
  }

  def generatePlaceInitialDwellings(gameState: GameState, faction: Faction): Seq[(GameState) => GameState] = {
    val tiles = gameState.gameBoard.placableDwellings(faction)
    tiles.map(t => Actions.placeInitialDwellings(faction, t))
  }

  //  def generateTerraform = (gameState: GameState, faction: Faction) => (_:Seq[(GameState) => GameState]) => {
  //    val tiles = gameState.gameBoard
  //
  //  }
}
