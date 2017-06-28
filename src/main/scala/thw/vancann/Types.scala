package thw.vancann

object BuildingType extends Enumeration {
  type BuildingType = Value
  val Dwelling, TradingHouse, Temple, Sanctuary, Stronghold = Value
}

object ResourceType extends Enumeration {
  type ResourceType = Value
  val Gold, Priest, Worker, Bridge, Power, Spade, Ship, VictoryPoints = Value
}

object CultType extends Enumeration {
  type CultType = Value
  val Fire, Water, Air, Earth = Value
}

object TerrainType extends Enumeration {
  type TerrainType = Value
  val Plains, Swamp, Lakes, Forest, Mountains, Wasteland, Desert, Sea = Value
}

object FactionType extends Enumeration {
  type FactionType = Value
  val Halflings, ChaosWizards = Value
}

object BonusTileType extends Enumeration {
  type BonusTileType = Value
  val Bon1, Bon2, Bon3, Bon4, Bon5, Bon6, Bon7, Bon8, Bon9 = Value
}

object ActionType extends Enumeration {
  type ActionType = Value
  val PlacePriest, AdvanceCult = Value
}

object PropertyType extends Enumeration {
  type PropertyType = Value
  val CultType, TerrainType, Hex, Faction = Value
}

object PriestSpaceType extends Enumeration {
  type PriestSpaceType = Value
  val Bonus3, Bonus2, Bonus1 = Value
}
