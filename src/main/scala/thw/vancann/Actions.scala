package thw.vancann

import thw.vancann.BuildingType.BuildingType
import thw.vancann.CultType.CultType
import thw.vancann.FactionType.FactionType
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

  def buildDwelling(tile: Tile, faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.buildDwelling(tile.hex, faction)
      newState
    }
  }

  def shippingTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      val newFaction = findFaction(newState, faction.factionType)
      newFaction.advanceShipTrack
      newState
    }
  }

  def spadeTrack(faction: Faction): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      val newFaction = findFaction(newState, faction.factionType)
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

  def upgradeBuilding(faction: Faction, tile: Tile, to: BuildingType, factionsToGainPower: List[FactionType]): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      newState.gameBoard.upgradeBuilding(tile.hex, to)
      factionsToGainPower.foreach(f => {
        val power = newState.gameBoard.possiblePowerGainFor(f, tile.hex)
        findFaction(newState, f).powerByStructure(power)
      })
      newState
    }
  }

  private def findFaction(newState: GameState, f: FactionType) = {
    newState.factions.find(_.factionType == f).get
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

  def pass(faction: Faction, newBonusTile: BonusTile): GameState => GameState = {
    gameState => {
      val newState = gameState.clone
      val oldBonusTile = faction.bonusTile
      newState.gameBoard.calculatePassBonusFor(faction: Faction, oldBonusTile.passBonus)
      findFaction(newState, faction.factionType).changeBonusTile(newBonusTile)
      newState
    }
  }
}
