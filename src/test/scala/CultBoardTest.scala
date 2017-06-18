import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}


class CultBoardTest extends FunSuite with MockFactory with Matchers with BeforeAndAfter{

  var cult: Cult = _

  before {
    cult = new Cult
  }

  test("Test default available order spaces") {
    val spaces = cult.availableOrderSpaces
    spaces.length shouldBe 5
  }

  test("Test adding faction") {
    val faction = mock[Faction]
    cult.addFaction(faction)

    cult.currentProgress(faction) shouldBe 0
  }

  test("Test adding priest on non-1 bonus space") {
    var spaces = cult.availableOrderSpaces
    val faction = mock[Faction]
    faction.removePriest _ expects 1

    cult.addFaction(faction)
    cult.placePriest(faction, spaces.head)

    spaces = cult.availableOrderSpaces
    spaces.length shouldBe 4

    cult.currentProgress(faction) shouldBe 3
  }

  test("Test adding priest on 1-bonus space") {
    val space = cult.availableOrderSpaces.find(_.bonus == 1).get
    val faction = mock[Faction]
    val supply = mock[FactionSupply]
    supply.addPriest _ expects 1
    faction.removePriest _ expects 1
    faction.supply _ expects () returns supply

    cult.addFaction(faction)
    cult.placePriest(faction, space)

    val spaces = cult.availableOrderSpaces
    spaces.length shouldBe 5

    cult.currentProgress(faction) shouldBe 1
  }
}
