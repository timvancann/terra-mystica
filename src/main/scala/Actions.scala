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

  def buildDwelling(tile: Tile, faction:Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.buildDwelling(tile, faction)
      newState
    }
  }

  def shippingTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      faction.advanceShipTrack
      newState
    }
  }

  def spadeTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      faction.advanceSpadeTrack
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

  def upgradeBuilding(faction: Faction, tile: Tile, to: BuildingType, factionsToGainPower: List[Faction]): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      gameState.gameBoard.upgradeBuilding(tile, to)
      factionsToGainPower.foreach(f => {
        val power = gameState.gameBoard.possiblePowerGainFor(f, tile)
        f.powerByStructure(power)
      })
      newState
    }
  }

  def powerAction(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      // TODO: implement
      newState
    }
  }

  def specialAction(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      // todo: implement
      newState
    }
  }

  def pass(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      // todo implement
      newState
    }
  }
}
