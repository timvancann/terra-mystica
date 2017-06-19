import BuildingType.BuildingType
import TerrainType.TerrainType

import scala.collection.immutable

case class GameBoard(private val tiles: Map[Hex, Tile], private val bridges: List[Bridge]) {

  private val cubeDirections = List(
    Cube( 1, -1,  0),Cube( 1,  0, -1), Cube(0,  1, -1),
    Cube(-1,  1,  0),Cube(-1,  0,  1), Cube(0, -1,  1)
  )

  private def cubeToHex(cube: Cube) = {
    val col = cube.x + (cube.z - (cube.z & 1)) / 2
    val row = cube.z
    Hex(col, row)
  }

  private def hexToCube(hex: Hex) = {
    val x = hex.col - (hex.row - (hex.row & 1)) / 2
    val z = hex.row
    val y = -x - z
    Cube(x, y, z)
  }

  private def neighbours(hex: Hex): List[Tile] = {
    val cube = hexToCube(hex)
    cubeDirections.map(c => c + cube)
      .map(cubeToHex)
      .filter(h => h.row > 0 & (h.col > 0 & (if ((h.row & 1) != 0) h.col<12 else h.col<13)))
      .map(tiles)
  }

  def canBuildBridge(from: Hex, to: Hex): Boolean = {
    bridges.find(b => (b.from == from & b.to == to) | (b.to == from & b.from == to))
      .exists(_.buildBy == null)
  }

  def isOccupied(tile: Tile): Boolean = {
    tile.faction != null
  }

  def tilesOccupiedBy(faction: Faction): Map[Hex, Tile] = {
    tiles.filter(isOwnedBy(faction, _))
  }

  def bridgesFor(faction: Faction): List[Bridge] = {
    bridges.filter(_.buildBy == faction)
  }

  def terraform(tile: Tile, to: TerrainType): Unit = {
    tile.terrain = to
  }

  def placeDwelling(tile: Tile, faction: Faction): Unit = {
    tile.faction = faction
    tile.building = BuildingType.Dwelling
  }

  def upgradeBuilding(tile: Tile, to: BuildingType): Unit = {
    tile.building = to
  }

  def placableDwellings(faction: Faction): List[Tile] = {
    tiles.values.toList.filter(t => hasCorrectTerrainFor(faction, t))
    .filter(t => !isOccupied(t))
  }

  def buildableDwellings(faction: Faction): List[Tile] = {
    tilesOccupiedBy(faction)
      .keys.toList
      .flatMap(neighbours)
      .filter(t => !isOccupied(t))
  }

  def buildableBridges(faction: Faction): Seq[Bridge] = {
    tiles.filter(t => isOwnedBy(faction, t))
      .flatMap(t => bridges.filter(b => b.to == t._1 | b.from == t._1))
      .filter(b => b.buildBy == null).toSeq
  }

  private def hasCorrectTerrainFor(faction: Faction, tile:  Tile) = {
    tile.terrain == faction.terrain
  }

  private def isOwnedBy(faction: Faction, tile: (Hex, Tile)) = {
    tile._2.faction == faction
  }

  def buildBridge(bridge: Bridge, faction: Faction): Unit = {
    bridge.buildBy = faction
    faction.sacrifice(ResourceType.Bridge)
  }

  def buildDwelling(tile: Tile, faction: Faction): Unit = {
    tile.faction = faction
    val cost = faction.dwellingCost
    // TODO: add cost spending
//    faction.spend(cost)
  }

  override def clone: GameBoard = ???
}

sealed case class Hex(row: Int, col: Int)
sealed case class Cube(x: Int, y: Int, z: Int) {
  def +(other: Cube) = Cube(x + other.x, y + other.y, z + other.z)
}

case class Tile(var terrain: TerrainType,
                var faction: Faction = null,
                var building: BuildingType = null) {
  override def clone(): Tile = ???
}

case class Bridge(from: Hex,
                  to: Hex,
                  var buildBy: Faction = null) {
  override def clone(): Bridge = ???
}
