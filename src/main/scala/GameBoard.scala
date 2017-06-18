object GameBoard {
  def distanceInSpades(from: Terrain, to: Terrain) = ???
}

case class Coord(x: String, y: Int)

sealed trait Terrain
object Plains extends Terrain
object Swamp extends Terrain
object Lakes extends Terrain
object Forest extends Terrain
object Mountains extends Terrain
object Wasteland extends Terrain
object Desert extends Terrain


case class Tile(coord: Coord, var terrain: Terrain, var faction: Faction)
