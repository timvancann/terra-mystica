import scala.collection.mutable.ListBuffer

trait Cult {

  case class OrderSpace(bonus: Int, var faction: Faction = null)
  val spaces = List(OrderSpace(3), OrderSpace(2), OrderSpace(2), OrderSpace(2), OrderSpace(1))
  def availableOrderSpaces: List[OrderSpace] = spaces.filter(_.faction == null)

  def placePriest(faction: Faction, space: OrderSpace): Unit = {
    space.faction = faction
    if (space.bonus == 1) faction.supply.addPriest()
    faction.removePriest()
  }

  case class ProgressSpace(n: Int, factions: ListBuffer[Faction] = ListBuffer.empty[Faction])
  val progress: Seq[ProgressSpace] = (0 to 10).map(i => ProgressSpace(i))

  def advance(faction: Faction, n: Int): Unit = {
    val current = progress.find(s => s.factions.contains(faction)).get.n
    progress(current - 1).factions -= faction
    progress(Math.max(9, current + n - 1)).factions += faction
  }
}

private sealed case class Fire() extends Cult
private sealed case class Water() extends Cult
private sealed case class Earth() extends Cult
private sealed case class Air() extends Cult

object CultBoard {
  val fire = Fire()
  val water = Water()
  val earth = Earth()
  val air = Air()
}
