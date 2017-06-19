object BuildingType extends Enumeration {
  type BuildingType = Value
  val Dwelling, TradingHouse, Temple, Sanctuary, Stronghold = Value
}

object ResourceType extends Enumeration {
  type ResourceType = Value
  val Coin, Priest, Worker, Bridge, Power = Value
}

object CultType extends Enumeration {
  type CultType = Value
  val Fire, Water, Air, Earth = Value
}

object TerrainType extends Enumeration {
  type TerrainType = Value
  val Plains, Swamp, Lakes, Forest, Mountains, Wasteland, Desert, Sea = Value
}

