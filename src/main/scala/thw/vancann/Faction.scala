package thw.vancann

import thw.vancann.BuildingType._
import thw.vancann.FactionType.FactionType
import thw.vancann.ResourceType._
import thw.vancann.TerrainType._

import scala.collection.mutable

case class Faction(factionType: FactionType,
                   terrain: TerrainType,
                   cultCost: (ResourceType, Int) = (Priest, 1),
                   terraformCost: List[(ResourceType, Int)] = List((Worker, 3), (Worker, 2), (Worker, 1)),
                   shipCost: List[(ResourceType, Int)] = List((Priest, 1), (Gold, 4)),
                   buildingCost: Map[BuildingType, List[(ResourceType, Int)]] = Map.empty,
                   availableBuildings: mutable.Map[BuildingType, Int] = mutable.Map.empty,
                   incomePerBuilding: Map[BuildingType, List[List[(ResourceType, Int)]]] = Map.empty,
                   victoryPointsPerShipTrack: List[Int] = List(0, 2, 3, 4),
                   victoryPointsPerSpadeTrack: List[Int] = List(0, 6, 6),
                   hasSpadeTrack: Boolean = true,
                   private val supply: Map[ResourceType, Resource] = Map.empty,
                   var bonusTile: BonusTile = BonusTile()
                  ) {


  def addInititialResource(resources: List[(ResourceType, Int)]): Unit = {
    resources.foreach(r => supply(r._1).gain(r._2))
  }

  def spend(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).spend(n)
  }

  def spend(cost: List[(ResourceType, Int)]): Unit = {
    cost.foreach(c => spend(c._1, c._2))
  }

  def gain(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).gain(n)
  }

  def gain(cost: List[(ResourceType, Int)]): Unit = {
    cost.foreach(c => gain(c._1, c._2))
  }

  def exchange(from: ResourceType, to: ResourceType, n: Int = 1): Unit = {
    supply(from).spend(n)
    supply(to).gain(n)
  }

  def sacrifice(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).sacrifice(n)
  }

  def numberOfTimesResourcesToSpendFor(cost: List[(ResourceType, Int)]): Int = {
    cost.map(c => resourcesToSpend(c._1) / c._2).min
  }

  def resourcesToSpend(resourceType: ResourceType): Int = {
    supply(resourceType).amountToSpend
  }

  def buildingsAvailableFor(buildingType: BuildingType): Int = {
    availableBuildings(buildingType)
  }

  def buildBrige: Unit = {
    supply(Bridge).sacrifice(1)
  }

  def buildDWelling: Unit = {
    availableBuildings(Dwelling) -= 1
  }

  def upgrade(from: BuildingType, to: BuildingType) {
    availableBuildings(from) += 1
    availableBuildings(to) -= 1
  }

  def calculateIncome: List[(ResourceType, Int)] = {
    def sumIncome(lst: List[(ResourceType, Int)]) = {
      lst
        .groupBy(_._1)
        .map(kv => kv._1 -> kv._2.map(_._2).sum)
        .toList
    }

    val buildingIncome = incomePerBuilding.map(kv =>
      kv._2
        .reverse
        .drop(availableBuildings(kv._1))
        .flatten
        .groupBy(_._1)
        .map(kv => kv._1 -> kv._2.map(_._2).sum)
        .toList
    ).toList.flatten

    val bonusTileIncome = bonusTile.income
    sumIncome(buildingIncome ++ bonusTileIncome)
  }

  def advanceShipTrack: Unit = {
    supply(Ship).gain(1)
    supply(VictoryPoints).gain(victoryPointsPerShipTrack(supply(Ship).amountToSpend))
  }

  def advanceSpadeTrack: Unit = {
    supply(Spade).gain(1)
    supply(VictoryPoints).gain(victoryPointsPerSpadeTrack(supply(Spade).amountToSpend))
  }

  def powerByStructure(power: Int): Unit = {
    supply(Power).gain(power)
    supply(VictoryPoints).spend(power - 1)
  }

  def changeBonusTile(newBonusTile: BonusTile): Unit = {
    bonusTile.passiveBonus.foreach(b => supply(b._1).spend(b._2))
    bonusTile.hasBeenUsed = false
    bonusTile.faction = null

    bonusTile = newBonusTile
    newBonusTile.faction = this.factionType

    bonusTile.passiveBonus.foreach(b => supply(b._1).gain(b._2))
  }

  override def clone: Faction = Faction(
    factionType, terrain, cultCost, terraformCost, shipCost, buildingCost, availableBuildings.clone(),
    incomePerBuilding, victoryPointsPerShipTrack, victoryPointsPerSpadeTrack, hasSpadeTrack,
    supply.map(kv => kv._1 -> {
      kv._1 match {
        case Priest => kv._2.asInstanceOf[PriestResource].clone
        case Bridge => kv._2.asInstanceOf[BridgeResource].clone
        case Power => kv._2.asInstanceOf[PowerResource].clone
        case _ => kv._2.asInstanceOf[GenericResource].clone
      }
    }),
    bonusTile
  )

}

