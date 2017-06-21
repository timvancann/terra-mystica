import BuildingType._
import ResourceType._
import TerrainType._

import scala.collection.mutable

class FactionSupply(priests: Int = 5, bridge: Int = 3) {
  val supply = Map(
    Priest -> new GenericResource(5),
    Bridge -> new GenericResource(3)
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
                   var terraformCost: (ResourceType, Int) = (Worker, 3),
                   var shipCost: List[(ResourceType, Int)] = List((Priest, 1), (Gold, 4)),
                   buildingCost: Map[BuildingType, List[(ResourceType, Int)]] = Map.empty,
                   availableBuildings: mutable.Map[BuildingType, Int] = mutable.Map.empty,
                   incomePerBuilding: Map[BuildingType, List[(ResourceType, Int)]] = Map.empty) {
  private val factionSupply = new FactionSupply

  private val supply = Map(
    Gold -> new GenericResource,
    Priest -> new PriestResource(factionSupply),
    Worker -> new GenericResource,
    Bridge -> new BridgeResource(factionSupply),
    Power -> constructPowerResource
  )

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

  def buildBrige(): Unit = {
    supply(Bridge).spend(1)
  }

  def buildDWelling(): Unit = {
    availableBuildings(Dwelling) -= 1
  }

  def upgrade(from: BuildingType, to: BuildingType) {
    availableBuildings(from) += 1
    availableBuildings(to) -= 1
  }

  override def clone: Faction = ???

}

