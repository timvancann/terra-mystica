import BonusTileType.BonusTileType
import BuildingType._
import ResourceType._
import TerrainType._

import scala.collection.mutable

class FactionSupply(priests: Int = 5, bridge: Int = 3) {
  val supply = Map(
    Priest -> new GenericResource(priests),
    Bridge -> new GenericResource(bridge)
  )

  def restock(resource: ResourceType): Unit = {
    supply(resource).gain(1)
  }

  def buy(resource: ResourceType): Unit = {
    supply(resource).spend(1)
  }
}

case class Faction(terrain: TerrainType,
                   cultCost: (ResourceType, Int) = (Priest, 1),
                   terraformCost: List[(ResourceType, Int)] = List((Worker, 3), (Worker, 2), (Worker, 1)),
                   shipCost: List[(ResourceType, Int)] = List((Priest, 1), (Gold, 4)),
                   buildingCost: Map[BuildingType, List[(ResourceType, Int)]] = Map.empty,
                   availableBuildings: mutable.Map[BuildingType, Int] = mutable.Map.empty,
                   incomePerBuilding: Map[BuildingType, List[List[(ResourceType, Int)]]] = Map.empty,
                   private val victoryPointsPerShipTrack: List[Int] = List(2, 3, 4),
                   private val victoryPointsPerSpadeTrack: List[Int] = List(6, 6),
                   hasSpadeTrack: Boolean = true
                  ) {
  private val factionSupply = new FactionSupply

  var victoryPoints = 20
  var shipTrack = 0
  var spadeTrack = 0
  var bonusTile: BonusTile = _

  private val supply = Map(
    Gold -> new GenericResource,
    Priest -> new PriestResource(factionSupply),
    Worker -> new GenericResource,
    Bridge -> new BridgeResource(factionSupply),
    Power -> constructPowerResource
  )

  def addInititialResource(resources: List[(ResourceType, Int)]): Unit = {
    resources.foreach(r => supply(r._1).gain(r._2))
  }

  def constructPowerResource: PowerResource = {
    new PowerResource(5, 7)
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
    cost.map(c => supply(c._1).amountToSpend / c._2).min
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
    incomePerBuilding.map(kv =>
      kv._2
        .reverse
        .drop(availableBuildings(kv._1))
        .flatten
        .groupBy(_._1)
        .map(kv => kv._1 -> kv._2.map(_._2).sum)
        .toList
    ).toList.flatten
      .groupBy(_._1)
      .map(kv => kv._1 -> kv._2.map(_._2).sum)
      .toList
  }

  def advanceShipTrack: Unit = {
    shipTrack += 1
    victoryPoints += victoryPointsPerShipTrack(shipTrack)
  }

  def advanceSpadeTrack: Unit = {
    spadeTrack += 1
    victoryPoints += victoryPointsPerSpadeTrack(spadeTrack)
  }

  def powerByStructure(power: Int): Unit = {
    supply(Power).gain(power)
    victoryPoints -= (power - 1)
  }

  def changeBonusTile(newBonusTile: BonusTileType) = {
  }

  override def clone: Faction = ???

}

