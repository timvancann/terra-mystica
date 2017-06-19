import ResourceType.ResourceType

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

trait Faction {

  private val factionSupply = new FactionSupply

  private val supply = Map(
    ResourceType.Coin   -> new GenericResource,
    ResourceType.Priest -> new PriestResource(factionSupply),
    ResourceType.Worker -> new GenericResource,
    ResourceType.Bridge -> new BridgeResource(factionSupply),
    ResourceType.Power  -> constructPowerResource
  )

  def constructPowerResource: PowerResource = {
    new PowerResource(5, 7)
  }

  def spend(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).spend(n)
  }

  def gain(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).gain(n)
  }

  def exchange(from: ResourceType, to: ResourceType, n: Int = 1): Unit = {
    supply(from).spend(n)
    supply(to).gain(n)
  }

  def sacrifice(resource: ResourceType, n: Int = 1): Unit = {
    supply(resource).sacrifice(n)
  }
}

object Dwellers extends Faction
