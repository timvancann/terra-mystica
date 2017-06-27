package thw.vancann

import thw.vancann.BuildingType.BuildingType
import thw.vancann.CultType.CultType
import thw.vancann.PriestSpaceType.PriestSpaceType
import thw.vancann.TerrainType.TerrainType

object Actions {

  def placeInitialDwelling(faction: Faction, where: Tile): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.placeDwelling(where.hex, faction)
      newState
    }
  }

  def terraform(tile: Tile, to: TerrainType): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.terraform(tile.hex, to)
      newState
    }
  }

  def buildDwelling(tile: Tile, faction:Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.buildDwelling(tile.hex, faction)
      newState
    }
  }

  def shippingTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      val newFaction = newState.factions.find(_.factionType == faction.factionType).get
      newFaction.advanceShipTrack
      newState
    }
  }

  def spadeTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      val newFaction = newState.factions.find(_.factionType == faction.factionType).get
      newFaction.advanceSpadeTrack
      newState
    }
  }

  def placePriestOnCult(faction: Faction, where: (CultType, PriestSpaceType)): GameState => GameState = {
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

  def pass(faction: Faction, bonusTile: BonusTile): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      gameState.gameBoard.calculatePassBonusFor(faction: Faction, bonusTile.passBonus)
      faction.changeBonusTile(bonusTile)
      newState
    }
  }
}
