import TerrainType.TerrainType
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

class FactionTest extends FunSuite with Matchers with BeforeAndAfter with MockFactory{

  var faction: Faction = _
  before {
    faction = Faction(
      mock[TerrainType],
      mock[Cost],
      mock[Cost],
      List.empty
    )
  }

  test("gain a worker") {
    faction.gain(ResourceType.Worker)
    faction.numberOfTimesResourcesToSpendFor(List(Cost(ResourceType.Worker, 1))) shouldBe 1
  }
}
