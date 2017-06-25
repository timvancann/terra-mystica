package thw.vancann

import thw.vancann.BuildingType.BuildingType
import thw.vancann.CultType.CultType
import thw.vancann.PropertyType.PropertyType
import thw.vancann.ResourceType.ResourceType
import thw.vancann.TerrainType.TerrainType

case class BonusTile(action: Map[PropertyType, Any] => GameState => GameState = _ => gameState => gameState,
                     income: List[(ResourceType, Int)] = List.empty,
                     passiveBonus: List[(ResourceType, Int)] = List.empty,
                     passBonus: List[(BuildingType, Int)] = List.empty) {
  var faction: Faction = _
  var hasBeenUsed: Boolean = false
}

object BonusTileActions {

  def gainSpade(properties: Map[PropertyType, Any]): (GameState) => GameState =
    gameState => {
      val newState = gameState.clone
      gameState.gameBoard.terraform(
        properties(PropertyType.Hex).asInstanceOf[Hex],
        properties(PropertyType.TerrainType).asInstanceOf[TerrainType])
      newState
    }

   def cultTrack(properties: Map[PropertyType, Any]): (GameState) => GameState =
    gameState => {
      val newState = gameState.clone
      newState.cultBoard.advance(
        properties(PropertyType.Faction).asInstanceOf[Faction],
        properties(PropertyType.CultType).asInstanceOf[CultType])
      newState
    }
}
