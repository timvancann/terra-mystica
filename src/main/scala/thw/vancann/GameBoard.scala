package thw.vancann

import thw.vancann.BuildingType.{BuildingType, Dwelling, _}
import thw.vancann.FactionType.FactionType
import thw.vancann.ResourceType.Bridge
import thw.vancann.TerrainType.{TerrainType, _}

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

  def isOccupied(tile: Tile): Boolean = {
    tile.faction != null
  }

  def tilesOccupiedBy(faction: Faction): List[Tile] = {
    tiles.filter(isOwnedBy(faction, _))
  }

  def bridgesFor(faction: Faction): List[TileBridge] = {
    bridges.filter(_.buildBy == faction.factionType)
  }

  def terraform(hex: Hex, to: TerrainType): Unit = {
    val t = findTileByHex(hex)
    t.terrain = to
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

  def upgradeBuilding(hex: Hex, to: BuildingType): Unit = {
    findTileByHex(hex).building = to
  }

  def possiblePowerGainFor(factionType: FactionType, hex: Hex): Int = {
    neighbours(hex)
      .filter(_.faction == factionType)
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

  def placeDwelling(hex: Hex, faction: Faction): Unit = {
    val t = findTileByHex(hex)
    t.faction = faction.factionType
    t.building = Dwelling
    faction.buildDWelling
  }

  def findTileByHex(hex: Hex): Tile = {
    tiles.find(_.hex == hex).get
  }

  def buildableDwellings(faction: Faction): List[Tile] = {
    unoccupiedNeighboursFor(faction).filter(t => hasCorrectTerrainFor(faction, t))
  }

  def buildDwelling(hex: Hex, faction: Faction): Unit = {
    val t = findTileByHex(hex)
    placeDwelling(t.hex, faction)
    t.faction = faction.factionType
    faction.spend(faction.buildingCost(Dwelling))
  }

  def buildableBridges(faction: Faction): Seq[TileBridge] = {
    tiles.filter(t => isOwnedBy(faction, t))
      .flatMap(t => bridges.filter(b => b.to == t.hex | b.from == t.hex))
      .filter(b => b.buildBy == null)
  }

  def buildBridge(bridge: TileBridge, faction: Faction): Unit = {
    bridge.buildBy = faction.factionType
    faction.sacrifice(Bridge)
  }

  def calculatePassBonusFor(faction: Faction, passBonus: List[(BuildingType, Int)]): Int = {
    val buildingsCount = tilesOccupiedBy(faction)
      .groupBy(_.building)
      .mapValues(_.length)

    passBonus
      .filter(b => buildingsCount.contains(b._1))
      .map(b => buildingsCount(b._1) * b._2)
      .sum
  }

  private def hasCorrectTerrainFor(faction: Faction, tile: Tile) = {
    tile.terrain == faction.terrain
  }

  private def isOwnedBy(faction: Faction, tile: Tile) = {
    tile.faction == faction.factionType
  }

  override def clone: GameBoard = GameBoard(tiles.map(_.clone), bridges.map(_.clone))
}

sealed case class Hex(row: Int, col: Int)

sealed case class Cube(x: Int, y: Int, z: Int) {
  def +(other: Cube) = Cube(x + other.x, y + other.y, z + other.z)
}

case class Tile(hex: Hex,
                var terrain: TerrainType,
                var faction: FactionType = null,
                var building: BuildingType = null) {
  override def clone: Tile = Tile(hex, terrain, faction, building)
}

case class TileBridge(from: Hex,
                      to: Hex,
                      var buildBy: FactionType = null) {
  override def clone: TileBridge = TileBridge(from, to, buildBy)
}
