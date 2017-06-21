import BuildingType.BuildingType
import TerrainType.TerrainType
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

class FactionTest extends FunSuite with Matchers with BeforeAndAfter with MockFactory {

  var faction: Faction = _
  before {
    faction = Faction(mock[TerrainType], buildingCost = Map.empty)
  }

  test("gain a worker") {
    faction.gain(ResourceType.Worker)
    faction.numberOfTimesResourcesToSpendFor(List((ResourceType.Worker, 1))) shouldBe 1
  }

  test("gain multiple resources") {
    faction.gain(List((ResourceType.Worker, 2), (ResourceType.Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((ResourceType.Worker, 2), (ResourceType.Gold, 5))) shouldBe 1
  }

  test("has resources for single item") {
    faction.gain(List((ResourceType.Worker, 2), (ResourceType.Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((ResourceType.Worker, 1), (ResourceType.Gold, 3))) shouldBe 1
  }

  test("has resources for multiple item") {
    faction.gain(List((ResourceType.Worker, 2), (ResourceType.Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((ResourceType.Worker, 1), (ResourceType.Gold, 2))) shouldBe 2
  }
}
