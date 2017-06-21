import CultType._
import PropertyType._
import ResourceType._

object BonusTiles {

  trait BonusTile {
    def action(faction: Faction, properties: Map[PropertyType, Any]): GameState => GameState
    def income: List[(ResourceType, Int)]
  }

  object Bon2 extends BonusTile {
    override def action(faction: Faction, properties: Map[PropertyType, Any]): (GameState) => GameState =
      gameState => {
        val newState = gameState.clone
        newState.cultBoard.advance(faction, properties(PropertyType.CultType).asInstanceOf[CultType])
        newState
      }
    override def income: List[(ResourceType, Int)] = List((Gold, 2))
  }

}
