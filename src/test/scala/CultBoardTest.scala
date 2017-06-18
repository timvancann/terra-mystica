import org.scalamock.proxy.ProxyMockFactory
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FunSuite, Matchers}


class CultBoardTest extends FunSuite with MockFactory with Matchers {

  test("Test default available order spaces") {
    val spaces = CultBoard.fire.availableOrderSpaces
    spaces.length shouldBe 5
  }

  test("Test adding priest") {
    val spaces = CultBoard.fire.availableOrderSpaces
    val faction = mock[Faction]
    faction.removePriest _ expects 1
    CultBoard.fire.placePriest(faction, spaces.head)
  }
}
