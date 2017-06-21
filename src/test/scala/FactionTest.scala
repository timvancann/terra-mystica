import BuildingType._
import TerrainType._
import ResourceType._
import org.scalamock.scalatest.MockFactory
import org.scalatest.{BeforeAndAfter, FunSuite, Matchers}

import scala.collection.mutable

class FactionTest extends FunSuite with Matchers with BeforeAndAfter with MockFactory {

  var faction: Faction = _
  before {
    faction = Faction(mock[TerrainType],
      availableBuildings = mutable.Map(
        Dwelling -> 8,
        TradingHouse -> 4
      ))
  }

  test("gain a worker") {
    faction.gain(ResourceType.Worker)
    faction.numberOfTimesResourcesToSpendFor(List((Worker, 1))) shouldBe 1
  }

  test("gain multiple resources") {
    faction.gain(List((Worker, 2), (Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((Worker, 2), (Gold, 5))) shouldBe 1
  }

  test("has resources for single item") {
    faction.gain(List((Worker, 2), (Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((Worker, 1), (Gold, 3))) shouldBe 1
  }

  test("has resources for multiple item") {
    faction.gain(List((Worker, 2), (Gold, 5)))
    faction.numberOfTimesResourcesToSpendFor(List((Worker, 1), (Gold, 2))) shouldBe 2
  }

  test("adding initial resources") {
    faction.addInititialResource(List((Gold, 15), (Worker, 4)))
    faction.numberOfTimesResourcesToSpendFor(List((Gold, 1))) shouldBe 15
    faction.numberOfTimesResourcesToSpendFor(List((Worker, 1))) shouldBe 4
  }

  test("building a bridge should reduce total number of bridges") {
    faction.numberOfTimesResourcesToSpendFor(List((Bridge, 1))) shouldBe 3
    faction.buildBrige
    faction.numberOfTimesResourcesToSpendFor(List((Bridge, 1))) shouldBe 2
  }

  test("building a dwelling should reduce available dwellings") {
    faction.buildingsAvailableFor(Dwelling) shouldBe 8
    faction.buildDWelling
    faction.buildingsAvailableFor(Dwelling) shouldBe 7
  }

  test("first building a dwelling and upgrading it to a tradinghouse") {
    faction.buildingsAvailableFor(Dwelling) shouldBe 8
    faction.buildingsAvailableFor(TradingHouse) shouldBe 4

    faction.buildDWelling
    faction.buildingsAvailableFor(Dwelling) shouldBe 7
    faction.upgrade(Dwelling, TradingHouse)

    faction.buildingsAvailableFor(Dwelling) shouldBe 8
    faction.buildingsAvailableFor(TradingHouse) shouldBe 3
  }

  test("calculate income for a single building, single resource") {
    val faction = Faction(mock[TerrainType],
      availableBuildings = mutable.Map(
        Dwelling -> 1
      ),
      incomePerBuilding = Map(
        Dwelling -> List(List((Worker, 2)))
      ))
    faction.buildDWelling
    val income = faction.calculateIncome
    income.filter(_._1 == Worker).head._2 shouldBe 2
  }

  test("calculate income for a single building, multiple resources") {
    val faction = Faction(mock[TerrainType],
      availableBuildings = mutable.Map(
        Dwelling -> 1
      ),
      incomePerBuilding = Map(
        Dwelling -> List(List((Worker, 2), (Gold, 3)))
      ))
    faction.buildDWelling
    val income = faction.calculateIncome
    income.filter(_._1 == Worker).head._2 shouldBe 2
    income.filter(_._1 == Gold).head._2 shouldBe 3
  }

  test("calculate income for a multiple buildings, single resources") {
    val faction = Faction(mock[TerrainType],
      availableBuildings = mutable.Map(
        Dwelling -> 2,
        TradingHouse -> 1
      ),
      incomePerBuilding = Map(
        Dwelling -> List(List((Worker, 2)), List((Worker, 3))),
        TradingHouse -> List(List((Worker, 1)))
      ))
    faction.buildDWelling
    faction.buildDWelling
    faction.upgrade(Dwelling, TradingHouse)
    val income = faction.calculateIncome
    income.filter(_._1 == Worker).head._2 shouldBe 3
  }
}
