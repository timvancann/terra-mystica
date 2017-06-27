package thw.vancann

import thw.vancann.CultType.CultType
import thw.vancann.PriestSpaceType.PriestSpaceType

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
      val spaces: Seq[(CultType, PriestSpaceType)] = (for {
        cult <- gameState.cultBoard.cults
        space <- cult._2.availablePriestSpaces
        if cult._2.currentProgress(faction) < 10
      } yield (cult._1, space.priestSpaceType)) (collection.breakOut)
      spaces.map(s => Actions.placePriestOnCult(faction, s))
    } else {
      List.empty
    }
  }

  def generatePlaceInitialDwellings(gameState: GameState, faction: Faction): Seq[(GameState) => GameState] = {
    val tiles = gameState.gameBoard.placableDwellings(faction)
    tiles.map(t => Actions.placeInitialDwelling(faction, t))
  }

  //  def generateTerraform = (gameState: GameState, faction: Faction) => (_:Seq[(GameState) => GameState]) => {
  //    val tiles = gameState.gameBoard
  //
  //  }
}
