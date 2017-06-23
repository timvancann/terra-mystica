package thw.vancann

import thw.vancann.CultType.CultType
import thw.vancann.FactionType.FactionType

import scala.collection.mutable.ListBuffer

case class PriestSpace(bonus: Int, var faction: FactionType = null) {
  override def clone: PriestSpace = PriestSpace(bonus, faction)
}

case class ProgressSpace(n: Int, var powerBonus: Int, factions: ListBuffer[FactionType] = ListBuffer.empty[FactionType]) {
  override def clone: ProgressSpace = ???
}

case class Cult(private val spaces: Seq[PriestSpace] = List.empty, private val progress: Seq[ProgressSpace] = List.empty) {

  def availableOrderSpaces: Seq[PriestSpace] = spaces.filter(_.faction == null)

  def addFaction(faction: Faction, n: Int = 0): Unit = {
    progress(n).factions += faction.factionType
  }

  def placePriest(faction: Faction, space: PriestSpace): Unit = {
    if (space.bonus == 1) {
      faction.spend(ResourceType.Priest)
    } else {
      space.faction = faction.factionType
      faction.sacrifice(ResourceType.Priest)
    }
    advance(faction, space.bonus)
  }

  def advance(faction: Faction, n: Int): Unit = {
    // TODO: add checking for town
    val current = currentProgress(faction)
    progress(current).factions -= faction.factionType
    val next = Math.min(10, current + n)
    progress(next).factions += faction.factionType

    faction.gain(ResourceType.Power, calculatPowerGain(current, next))
  }

  def calculatPowerGain(start: Int, end: Int): Int = {
    progress
      .filter(p => p.n > start & p.n <= end)
      .map(_.powerBonus)
      .sum
  }

  def currentProgress(faction: Faction): Int = progress.find(s => s.factions.contains(faction.factionType)).get.n

  override def clone: Cult = Cult(spaces.map(_.clone), progress.map(_.clone))
}


case class CultBoard(cults: Map[CultType, Cult]) {
  def placePriest(faction: Faction, where: (CultType, PriestSpace)): Unit = {
    cults(where._1).placePriest(faction, where._2)
  }

  def advance(faction: Faction, cultType: CultType): Unit = {
    cults(cultType).advance(faction, 1)
  }

  override def clone: CultBoard = CultBoard(cults.map(kv => kv._1 -> kv._2.clone))
}
