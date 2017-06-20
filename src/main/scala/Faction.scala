import BuildingType.BuildingType
import ResourceType.ResourceType
import TerrainType.TerrainType

class FactionSupply(priests: Int = 5, bridge: Int = 3) {
  val supply = Map(
    ResourceType.Priest -> new GenericResource(5),
    ResourceType.Bridge -> new GenericResource(3)
  )

  def restock(resource: ResourceType): Unit = {
    supply(resource).gain(1)
  }

  def buy(resource: ResourceType): Unit = {
    supply(resource).spend(1)
  }
}

case class Cost(resource: ResourceType, n: Int)

case class Faction(terrain: TerrainType,
                   cultCost: Cost = Cost(ResourceType.Priest, 1),
                   var terraformCost: Cost = Cost(ResourceType.Worker, 3),
                   var shipCost: List[Cost] = List(Cost(ResourceType.Priest, 1), Cost(ResourceType.Gold, 4)),
                   buildingCost: Map[BuildingType, List[Cost]] = Map.empty) {
  private val factionSupply = new FactionSupply

  private val supply = Map(
    ResourceType.Gold -> new GenericResource,
    ResourceType.Priest -> new PriestResource(factionSupply),
    ResourceType.Worker -> new GenericResource,
    ResourceType.Bridge -> new BridgeResource(factionSupply),
    ResourceType.Power -> constructPowerResource
  )

  def constructPowerResource: PowerResource = {
    new PowerResource(5, 7)
  }

  def spend(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).spend(n)
  }

  def spend(cost: List[Cost]): Unit = {
    cost.foreach(c => spend(c.resource, c.n))
  }

  def gain(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).gain(n)
  }

  def gain(cost: List[Cost]): Unit = {
    cost.foreach(c => gain(c.resource, c.n))
  }

  def exchange(from: ResourceType, to: ResourceType, n: Int = 1): Unit = {
    supply(from).spend(n)
    supply(to).gain(n)
  }

  def sacrifice(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).sacrifice(n)
  }

  def numberOfTimesResourcesToSpendFor(cost: List[Cost]): Int = {
    cost.map(c => supply(c.resource).amountToSpend / c.n).min
  }

  override def clone: Faction = ???

}

