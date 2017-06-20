import BuildingType._
import ResourceType._
import TerrainType._
import FactionType._

object Defaults {

  val bridges = List(
    Bridge(Hex(0, 0), Hex(2, 2)),
    Bridge(Hex(0, 6), Hex(2, 6)),
    Bridge(Hex(0, 10), Hex(2, 10)),
    Bridge(Hex(1, 0), Hex(3, 0)),
    Bridge(Hex(1, 0), Hex(2, 2)),
    Bridge(Hex(1, 3), Hex(2, 2)),
    Bridge(Hex(1, 4), Hex(2, 6))
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

  val factions = Map(
    Halflings -> Faction(
      Plains,
      Cost(Priest, 1),
      Cost(Worker, 3),
      List(Cost(Priest, 1), Cost(Gold, 4)),
      Map(
        Dwelling -> List(Cost(Worker, 1), Cost(Gold, 2)),
        TradingHouse -> List(Cost(Worker, 2), Cost(Gold, 6)),
        Temple -> List(Cost(Worker, 2), Cost(Gold, 5)),
        Sanctuary -> List(Cost(Worker, 4), Cost(Gold, 6)),
        Stronghold -> List(Cost(Worker, 4), Cost(Gold, 8))
      )
    )
  )
}
