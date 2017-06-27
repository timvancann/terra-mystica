package thw.vancann

import thw.vancann.CultType.CultType
import thw.vancann.FactionType.FactionType
import thw.vancann.PriestSpaceType._

import scala.collection.mutable.ListBuffer

case class PriestSpace(priestSpaceType: PriestSpaceType, bonus: Int, var faction: FactionType = null) {
  override def clone: PriestSpace = PriestSpace(priestSpaceType, bonus, faction)
}

case class ProgressSpace(n: Int, var powerBonus: Int, factions: ListBuffer[FactionType] = ListBuffer.empty[FactionType]) {
  override def clone: ProgressSpace = ProgressSpace(n, powerBonus, factions.clone())
}

case class Cult(private val priestSpaces: Seq[PriestSpace] = List.empty, private val progressSpaces: Seq[ProgressSpace] = List.empty) {

  def availablePriestSpaces: Seq[PriestSpace] = priestSpaces.filter(_.faction == null)

  def addFaction(faction: Faction, n: Int = 0): Unit = {
    progressSpaces(n).factions += faction.factionType
  }

  def placePriest(faction: Faction, spaceType: PriestSpaceType): Unit = {
    val space = availablePriestSpaces
      .filter(_.priestSpaceType == spaceType)
      .head

    if (spaceType == Bonus1) {
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
    progressSpaces(current).factions -= faction.factionType
    val next = Math.min(10, current + n)
    progressSpaces(next).factions += faction.factionType

    faction.gain(ResourceType.Power, calculatPowerGain(current, next))
  }

  def calculatPowerGain(start: Int, end: Int): Int = {
    progressSpaces
      .filter(p => p.n > start & p.n <= end)
      .map(_.powerBonus)
      .sum
  }

  def currentProgress(faction: Faction): Int = progressSpaces.find(s => s.factions.contains(faction.factionType)).get.n

  override def clone: Cult = Cult(priestSpaces.map(_.clone), progressSpaces.map(_.clone))
}


case class CultBoard(cults: Map[CultType, Cult]) {
  def placePriest(faction: Faction, where: (CultType, PriestSpaceType)): Unit = {
    cults(where._1).placePriest(faction, where._2)
  }

  def addFaction(faction: Faction): Unit = {
    cults.foreach(c => c._2.addFaction(faction))
  }
  def advance(faction: Faction, cultType: CultType): Unit = {
    cults(cultType).advance(faction, 1)
  }

  override def clone: CultBoard = CultBoard(cults.map(kv => kv._1 -> kv._2.clone))
}
