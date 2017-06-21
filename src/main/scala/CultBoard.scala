import CultType.CultType

import scala.collection.mutable.ListBuffer

case class OrderSpace(bonus: Int, var faction: Faction = null)

case class Cult() {

  private val spaces = List(OrderSpace(3), OrderSpace(2), OrderSpace(2), OrderSpace(2), OrderSpace(1))

  private case class ProgressSpace(n: Int, var powerBonus: Int, factions: ListBuffer[Faction] = ListBuffer.empty[Faction])

  private val progress: Seq[ProgressSpace] = (0 to 10)
    .map {
      case i@3 => ProgressSpace(i, 1)
      case i@5 => ProgressSpace(i, 2)
      case i@7 => ProgressSpace(i, 2)
      case i@10 => ProgressSpace(i, 3)
      case i => ProgressSpace(i, 0)
    }

  def availableOrderSpaces: List[OrderSpace] = spaces.filter(_.faction == null)

  def addFaction(faction: Faction, n: Int = 0): Unit = {
    progress(n).factions += faction
  }

  def placePriest(faction: Faction, space: OrderSpace): Unit = {
    if (space.bonus == 1) {
      faction.spend(ResourceType.Priest)
    } else {
      space.faction = faction
      faction.sacrifice(ResourceType.Priest)
    }
    advance(faction, space.bonus)
  }

  def advance(faction: Faction, n: Int): Unit = {
    // TODO: add checking for town
    val current = currentProgress(faction)
    progress(current).factions -= faction
    val next = Math.min(10, current + n)
    progress(next).factions += faction

    faction.gain(ResourceType.Power, calculatPowerGain(current, next))
  }

  def calculatPowerGain(start: Int, end: Int): Int = {
    progress
      .filter(p => p.n > start & p.n <= end)
      .map(_.powerBonus)
      .sum
  }

  def currentProgress(faction: Faction): Int = progress.find(s => s.factions.contains(faction)).get.n
}


case class CultBoard(cults: Map[CultType, Cult]) {
  def placePriest(faction: Faction, where: (CultType, OrderSpace)): Unit = {
    cults(where._1).placePriest(faction, where._2)
  }

  def advance(faction: Faction, cultType: CultType): Unit = {
    cults(cultType).advance(faction, 1)
  }

  override def clone: CultBoard = ???
}
