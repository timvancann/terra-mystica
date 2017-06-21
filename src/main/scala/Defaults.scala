import BuildingType._
import ResourceType._
import TerrainType._
import FactionType._
import CultType._

import scala.collection.mutable

object Defaults {

  val bridges = List(
    TileBridge(Hex(0, 0), Hex(2, 2)),
    TileBridge(Hex(0, 6), Hex(2, 6)),
    TileBridge(Hex(0, 10), Hex(2, 10)),
    TileBridge(Hex(1, 0), Hex(3, 0)),
    TileBridge(Hex(1, 0), Hex(2, 2)),
    TileBridge(Hex(1, 3), Hex(2, 2)),
    TileBridge(Hex(1, 4), Hex(2, 6))
  )

  val tiles = List(
    Tile(Hex(0, 0), Plains),
    Tile(Hex(0, 1), Mountains),
    Tile(Hex(0, 2), Forest),
    Tile(Hex(0, 3), Lakes),
    Tile(Hex(0, 4), Desert),
    Tile(Hex(0, 5), Wasteland),
    Tile(Hex(0, 6), Plains),
    Tile(Hex(0, 7), Swamp),
    Tile(Hex(0, 8), Wasteland),
    Tile(Hex(0, 9), Forest),
    Tile(Hex(0, 10), Lakes),
    Tile(Hex(0, 11), Wasteland),
    Tile(Hex(0, 12), Swamp),

    Tile(Hex(1, 0), Desert),
    Tile(Hex(1, 1), Sea),
    Tile(Hex(1, 2), Sea),
    Tile(Hex(1, 3), Plains),
    Tile(Hex(1, 4), Swamp),
    Tile(Hex(1, 5), Sea),
    Tile(Hex(1, 6), Sea),
    Tile(Hex(1, 7), Desert),
    Tile(Hex(1, 8), Swamp),
    Tile(Hex(1, 9), Sea),
    Tile(Hex(1, 10), Sea),
    Tile(Hex(1, 11), Desert),

    Tile(Hex(2, 0), Sea),
    Tile(Hex(2, 1), Sea),
    Tile(Hex(2, 2), Swamp),
    Tile(Hex(2, 3), Sea),
    Tile(Hex(2, 4), Mountains),
    Tile(Hex(2, 5), Sea),
    Tile(Hex(2, 6), Forest),
    Tile(Hex(2, 7), Sea),
    Tile(Hex(2, 8), Forest),
    Tile(Hex(2, 9), Sea),
    Tile(Hex(2, 10), Mountains),
    Tile(Hex(2, 11), Sea),
    Tile(Hex(2, 12), Sea),

    Tile(Hex(3, 0), Forest),
    Tile(Hex(3, 1), Lakes),
    Tile(Hex(3, 2), Desert),
    Tile(Hex(3, 3), Sea),
    Tile(Hex(3, 4), Sea),
    Tile(Hex(3, 5), Wasteland),
    Tile(Hex(3, 6), Lakes),
    Tile(Hex(3, 7), Sea),
    Tile(Hex(3, 8), Wasteland),
    Tile(Hex(3, 9), Sea),
    Tile(Hex(3, 10), Wasteland),
    Tile(Hex(3, 11), Plains)
  )

  val cults = Map(
    Water -> Cult(),
    Fire -> Cult(),
    Air -> Cult(),
    Earth -> Cult()
  )

  val defaultBuildings = mutable.Map(
    Dwelling -> 8,
    TradingHouse -> 5,
    Temple -> 3,
    Sanctuary -> 1,
    Stronghold -> 1
  )


  val factions = Map(
    Halflings -> Faction(
      terrain = Plains,
      cultCost = (Priest, 1),
      terraformCost = (Worker, 3),
      shipCost = List((Priest, 1), (Gold, 4)),
      buildingCost = Map(
        Dwelling -> List((Worker, 1), (Gold, 2)),
        TradingHouse -> List((Worker, 2), (Gold, 6)),
        Temple -> List((Worker, 2), (Gold, 5)),
        Sanctuary -> List((Worker, 4), (Gold, 6)),
        Stronghold -> List((Worker, 4), (Gold, 8))
      ),
      availableBuildings = defaultBuildings,
      incomePerBuilding = Map.empty
    )
  )
}
