import BuildingType._
import ResourceType.Bridge
import TerrainType._

case class GameBoard(private val tiles: List[Tile], private val bridges: List[TileBridge]) {

  private val cubeDirections = List(
    Cube(1, -1, 0), Cube(1, 0, -1), Cube(0, 1, -1),
    Cube(-1, 1, 0), Cube(-1, 0, 1), Cube(0, -1, 1)
  )

  private def cubeToHex(cube: Cube) = {
    val col = cube.x + (cube.z - (cube.z & 1)) / 2
    val row = cube.z
    Hex(row, col)
  }

  private def hexToCube(hex: Hex) = {
    val x = hex.col - (hex.row - (hex.row & 1)) / 2
    val z = hex.row
    val y = -x - z
    Cube(x, y, z)
  }

  def neighbours(hex: Hex): List[Tile] = {
    // todo neighbours by bridge and boat
    val (evenCol, oddCol) = tiles.map(_.hex).partition(h => (h.row & 1) == 0)
    val maxRow = tiles.map(_.hex.row).max

    def rowOnBoard(hex: Hex): Boolean = {
      hex.row >= 0 & hex.row <= maxRow
    }

    def colOnBoard(hex: Hex): Boolean = {
      val maxCol = if ((hex.row & 1) == 0) evenCol.map(_.col).max else oddCol.map(_.col).max
      hex.col >= 0 & hex.col <= maxCol
    }

    val cube = hexToCube(hex)
    val cubes = cubeDirections.map(c => c + cube)
    val hexes = cubes.map(cubeToHex)
    val filtered = hexes.filter(h => rowOnBoard(h) & colOnBoard(h))
    filtered.map(h => tiles.find(_.hex == h).get)
  }

  def canBuildBridge(from: Hex, to: Hex): Boolean = {
    bridges.find(b => (b.from == from & b.to == to) | (b.to == from & b.from == to))
      .exists(_.buildBy == null)
  }

  def isOccupied(tile: Tile): Boolean = {
    tile.faction != null
  }

  def tilesOccupiedBy(faction: Faction): List[Tile] = {
    tiles.filter(isOwnedBy(faction, _))
  }

  def bridgesFor(faction: Faction): List[TileBridge] = {
    bridges.filter(_.buildBy == faction)
  }

  def terraform(tile: Tile, to: TerrainType): Unit = {
    tile.terrain = to
  }

  private def unoccupiedNeighboursFor(faction: Faction) = {
    tilesOccupiedBy(faction)
      .flatMap(t => neighbours(t.hex))
      .filter(t => !isOccupied(t))
  }

  def terraformableFor(faction: Faction): List[Tile] = {
    unoccupiedNeighboursFor(faction)
      .filter(t => t.terrain != faction.terrain)
      .filter(t => t.terrain != Sea)
  }

  def upgradableBuildings(faction: Faction): List[Tile] = {
    tilesOccupiedBy(faction)
      .filter(t => t.building == Dwelling | t.building == TradingHouse | t.building == Temple)
  }

  def upgradeBuilding(tile: Tile, to: BuildingType): Unit = {
    tile.building = to
  }

  def possiblePowerGainFor(faction: Faction, tile: Tile): Int = {
    neighbours(tile.hex)
      .filter(_.faction == faction)
      .map(t => {
        t.building match {
          case BuildingType.Stronghold | BuildingType.Sanctuary => 3
          case BuildingType.Temple | BuildingType.TradingHouse => 2
          case _ => 1
        }
      }).sum
  }

  def placableDwellings(faction: Faction): List[Tile] = {
    tiles.filter(t => hasCorrectTerrainFor(faction, t))
      .filter(t => !isOccupied(t))
  }

  def placeDwelling(tile: Tile, faction: Faction): Unit = {
    tile.faction = faction
    tile.building = Dwelling
    faction.buildDWelling
  }

  def buildableDwellings(faction: Faction): List[Tile] = {
    unoccupiedNeighboursFor(faction).filter(t => hasCorrectTerrainFor(faction, t))
  }

  def buildDwelling(tile: Tile, faction: Faction): Unit = {
    placeDwelling(tile, faction)
    tile.faction = faction
    faction.spend(faction.buildingCost(Dwelling))
  }

  def buildableBridges(faction: Faction): Seq[TileBridge] = {
    tiles.filter(t => isOwnedBy(faction, t))
      .flatMap(t => bridges.filter(b => b.to == t.hex | b.from == t.hex))
      .filter(b => b.buildBy == null)
  }

  def buildBridge(bridge: TileBridge, faction: Faction): Unit = {
    bridge.buildBy = faction
    faction.sacrifice(Bridge)
  }


  private def hasCorrectTerrainFor(faction: Faction, tile: Tile) = {
    tile.terrain == faction.terrain
  }

  private def isOwnedBy(faction: Faction, tile: Tile) = {
    tile.faction == faction
  }

  override def clone: GameBoard = GameBoard(tiles.map(_.clone), bridges.map(_.clone))
}

sealed case class Hex(row: Int, col: Int)

sealed case class Cube(x: Int, y: Int, z: Int) {
  def +(other: Cube) = Cube(x + other.x, y + other.y, z + other.z)
}

case class Tile(hex: Hex,
                var terrain: TerrainType,
                var faction: Faction = null,
                var building: BuildingType = null) {
  override def clone: Tile = Tile(hex, terrain, faction.clone, building)
}

case class TileBridge(from: Hex,
                      to: Hex,
                      var buildBy: Faction = null) {
  override def clone: TileBridge = TileBridge(from, to, buildBy.clone)
}
