import BuildingType.BuildingType
import TerrainType.TerrainType

object TerrainType extends Enumeration {
  type TerrainType = Value
  val Plains, Swamp, Lakes, Forest, Mountains, Wasteland, Desert, Sea = Value
}

class GameBoard {
  val bridges = List(
    Bridge(Hex(0, 0), Hex(2, 2)),
    Bridge(Hex(0, 6), Hex(2, 6)),
    Bridge(Hex(0,10), Hex(2,10)),
    Bridge(Hex(1, 0), Hex(3, 0)),
    Bridge(Hex(1, 0), Hex(2, 2)),
    Bridge(Hex(1, 3), Hex(2, 2)),
    Bridge(Hex(1, 4), Hex(2, 6))
  )
  val tiles = Map(
    Hex(0, 0) -> Tile(TerrainType.Plains),
    Hex(0, 1) -> Tile(TerrainType.Mountains),
    Hex(0, 2) -> Tile(TerrainType.Forest),
    Hex(0, 3) -> Tile(TerrainType.Lakes),
    Hex(0, 4) -> Tile(TerrainType.Desert),
    Hex(0, 5) -> Tile(TerrainType.Wasteland),
    Hex(0, 6) -> Tile(TerrainType.Plains),
    Hex(0, 7) -> Tile(TerrainType.Swamp),
    Hex(0, 8) -> Tile(TerrainType.Wasteland),
    Hex(0, 9) -> Tile(TerrainType.Forest),
    Hex(0,10) -> Tile(TerrainType.Lakes),
    Hex(0,11) -> Tile(TerrainType.Wasteland),
    Hex(0,12) -> Tile(TerrainType.Swamp),

    Hex(1, 0) -> Tile(TerrainType.Desert),
    Hex(1, 1) -> Tile(TerrainType.Sea),
    Hex(1, 2) -> Tile(TerrainType.Sea),
    Hex(1, 3) -> Tile(TerrainType.Plains),
    Hex(1, 4) -> Tile(TerrainType.Swamp),
    Hex(1, 5) -> Tile(TerrainType.Sea),
    Hex(1, 6) -> Tile(TerrainType.Sea),
    Hex(1, 7) -> Tile(TerrainType.Desert),
    Hex(1, 8) -> Tile(TerrainType.Swamp),
    Hex(1, 9) -> Tile(TerrainType.Sea),
    Hex(1,10) -> Tile(TerrainType.Sea),
    Hex(1,11) -> Tile(TerrainType.Desert),

    Hex(2, 0) -> Tile(TerrainType.Sea),
    Hex(2, 1) -> Tile(TerrainType.Sea),
    Hex(2, 2) -> Tile(TerrainType.Swamp),
    Hex(2, 3) -> Tile(TerrainType.Sea),
    Hex(2, 4) -> Tile(TerrainType.Mountains),
    Hex(2, 5) -> Tile(TerrainType.Sea),
    Hex(2, 6) -> Tile(TerrainType.Forest),
    Hex(2, 7) -> Tile(TerrainType.Sea),
    Hex(2, 8) -> Tile(TerrainType.Forest),
    Hex(2, 9) -> Tile(TerrainType.Sea),
    Hex(2,10) -> Tile(TerrainType.Mountains),
    Hex(2,11) -> Tile(TerrainType.Sea),
    Hex(2,12) -> Tile(TerrainType.Sea),

    Hex(3, 0) -> Tile(TerrainType.Forest),
    Hex(3, 1) -> Tile(TerrainType.Lakes),
    Hex(3, 2) -> Tile(TerrainType.Desert),
    Hex(3, 3) -> Tile(TerrainType.Sea),
    Hex(3, 4) -> Tile(TerrainType.Sea),
    Hex(3, 5) -> Tile(TerrainType.Wasteland),
    Hex(3, 6) -> Tile(TerrainType.Lakes),
    Hex(3, 7) -> Tile(TerrainType.Sea),
    Hex(3, 8) -> Tile(TerrainType.Wasteland),
    Hex(3, 9) -> Tile(TerrainType.Sea),
    Hex(3,10) -> Tile(TerrainType.Wasteland),
    Hex(3,11) -> Tile(TerrainType.Plains)

  )

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

  def neighbours(hex: Hex): List[Tile] = {
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

  def isOccupied(hex: Hex): Boolean = {
    tiles(hex).faction != null
  }

  def tilesFor(faction: Faction): List[Tile] = {
    tiles.values.toList.filter(_.faction == faction)
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

  def buildBridge(bridge: Bridge, faction: Faction): Unit = {
    bridge.buildBy = faction
    faction.sacrifice(ResourceType.Bridge)
  }


}

sealed case class Hex(row: Int, col: Int)
sealed case class Cube(x: Int, y: Int, z: Int) {
  def +(other: Cube) = Cube(x + other.x, y + other.y, z + other.z)
}

case class Tile(var terrain: TerrainType,
                var faction: Faction = null,
                var building: BuildingType = null)
case class Bridge(from: Hex,
                  to: Hex,
                  var buildBy: Faction = null)
