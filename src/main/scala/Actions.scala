import BuildingType._
import CultType._
import TerrainType.TerrainType

object Actions {

  def placeInitialDwellings(faction: Faction, where: Tile): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.placeDwelling(where, faction)
      newState
    }
  }

  def terraform(tile: Tile, to: TerrainType): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.terraform(tile, to)
      newState
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
