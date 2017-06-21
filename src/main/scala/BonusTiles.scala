import BuildingType.BuildingType
import CultType._
import PropertyType.PropertyType
import ResourceType._
import TerrainType.TerrainType

case class BonusTile(action: Map[PropertyType, Any] => GameState => GameState = _ => gameState => gameState,
                     income: List[(ResourceType, Int)] = List.empty,
                     passiveBonus: Faction => Unit = faction => Unit,
                     passBonus: List[(BuildingType, Int)] = List.empty) {
  var faction: Faction = _
  var hasBeenUsed: Boolean = false
}

object BonusTileActions {

  def gainSpade(properties: Map[PropertyType, Any]): (GameState) => GameState =
    gameState => {
      val newState = gameState.clone
      gameState.gameBoard.terraform(
        properties(PropertyType.Tile).asInstanceOf[Tile],
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
