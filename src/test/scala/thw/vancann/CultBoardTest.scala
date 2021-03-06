package thw.vancann

import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}
import thw.vancann.TerrainType.TerrainType


class CultBoardTest extends FunSuite with MockFactory with Matchers with BeforeAndAfter {

  var cult: Cult = _
  var faction: Faction = _

  before {
    cult = Cult(Defaults.cultPriestSpaces, Defaults.cultProgressSpaces)
    faction = Faction(FactionType.Halflings, mock[TerrainType], supply = Defaults.supply)

    cult.addFaction(faction)
  }

  test("Test default available order spaces") {
    val spaces = cult.availablePriestSpaces
    spaces.length shouldBe 5
  }

  test("Test adding faction") {
    cult.currentProgress(faction) shouldBe 0
  }

  test("Test powergain no power") {
    cult.calculatPowerGain(0, 2) shouldBe 0
  }

  test("Test powergain single power transition") {
    cult.calculatPowerGain(0, 3) shouldBe 1
  }

  test("Test powergain multiple power transitions") {
    cult.calculatPowerGain(3, 9) shouldBe 4
  }

  test("Test powergain all power transitions") {
    cult.calculatPowerGain(0, 10) shouldBe 8
  }

  test("Test adding priest on non-1 bonus space") {
    var spaces = cult.availablePriestSpaces

    cult.placePriest(faction, spaces.head.priestSpaceType)

    spaces = cult.availablePriestSpaces
    spaces.length shouldBe 4

    cult.currentProgress(faction) shouldBe 3
  }

  test("Test adding priest on 1-bonus space") {
    val space = cult.availablePriestSpaces.reverse.head

    cult.placePriest(faction, space.priestSpaceType)

    val spaces = cult.availablePriestSpaces
    spaces.length shouldBe 5

    cult.currentProgress(faction) shouldBe 1
  }
}
