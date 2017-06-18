import scala.collection.mutable.ListBuffer

trait Cult {

  case class OrderSpace(bonus: Int, var faction: Faction = null)
  private val spaces = List(OrderSpace(3), OrderSpace(2), OrderSpace(2), OrderSpace(2), OrderSpace(1))

  private case class ProgressSpace(n: Int, factions: ListBuffer[Faction] = ListBuffer.empty[Faction])
  private val progress: Seq[ProgressSpace] = (0 to 10).map(i => ProgressSpace(i))

  def availableOrderSpaces: List[OrderSpace] = spaces.filter(_.faction == null)

  def placePriest(faction: Faction, space: OrderSpace): Unit = {
    space.faction = faction
    if (space.bonus == 1) faction.supply.addPriest()
    faction.removePriest()
  }

  def advance(faction: Faction, n: Int): Unit = {
    val current = progress.find(s => s.factions.contains(faction)).get.n
    progress(current - 1).factions -= faction
    progress(Math.max(9, current + n - 1)).factions += faction
  }
}

protected object Fire extends Cult
object Water extends Cult
object Earth extends Cult
object Air extends Cult

object CultBoard {
  val fire = Fire
  val water = Water
  val earth = Earth
  val air = Air
}
