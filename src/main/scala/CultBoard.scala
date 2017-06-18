import scala.collection.mutable.ListBuffer

case class OrderSpace(bonus: Int, var faction: Faction = null)
class Cult {

  private val spaces = List(OrderSpace(3), OrderSpace(2), OrderSpace(2), OrderSpace(2), OrderSpace(1))

  private case class ProgressSpace(n: Int, factions: ListBuffer[Faction] = ListBuffer.empty[Faction])
  private val progress: Seq[ProgressSpace] = (0 to 10).map(i => ProgressSpace(i))

  def availableOrderSpaces: List[OrderSpace] = spaces.filter(_.faction == null)

  def addFaction(faction: Faction, n: Int = 0): Unit = {
    progress(n).factions += faction
  }

  def placePriest(faction: Faction, space: OrderSpace): Unit = {
    if (space.bonus == 1) faction.supply.addPriest() else space.faction = faction
    faction.removePriest()
    advance(faction, space.bonus)
  }

  def advance(faction: Faction, n: Int): Unit = {
    val current = currentProgress(faction)
    progress(current).factions -= faction
    progress(Math.min(10, current + n)).factions += faction
  }

  def currentProgress(faction: Faction): Int = progress.find(s => s.factions.contains(faction)).get.n
}


object CultBoard {
  val fire = new Cult
  val water = new Cult
  val earth = new Cult
  val air = new Cult
}
