package thw.vancann

import org.scalatest.{FunSuite, Matchers}

class ResourceTest extends FunSuite with Matchers {


  test("spending generic resource") {
    val victim = new GenericResource(4)

    victim.spend(3)
    victim.amount shouldBe 1
  }

  test("Gaining generic resource") {
    val victim = new GenericResource(4)

    victim.gain(3)
    victim.amount shouldBe 7
  }

  test("Sacrificing generic resource") {
    val victim = new GenericResource(4)

    victim.sacrifice(3)
    victim.amount shouldBe 1
  }

  test("spending priest resource") {
    val victim = new PriestResource(2, 5)

    victim.spend(1)
    victim.amount shouldBe 1
    victim.factionSupply shouldBe 6
  }

  test("Gaining priest resource") {
    val victim = new PriestResource(2, 5)

    victim.gain(1)
    victim.amount shouldBe 3
    victim.factionSupply shouldBe 4
  }

  test("Sacrificing priest resource") {
    val victim = new PriestResource(2, 5)

    victim.sacrifice(1)
    victim.amount shouldBe 1
    victim.factionSupply shouldBe 5
  }

  test("spending power resource") {
    val victim = new PowerResource(5, 7, 3)

    victim.spend(1)
    victim.stage1 shouldBe 6
    victim.stage2 shouldBe 7
    victim.stage3 shouldBe 2
  }

  test("Gaining power resource only stage1 to stage2") {
    val victim = new PowerResource(2, 3, 0)

    victim.gain(1)
    victim.stage1 shouldBe 1
    victim.stage2 shouldBe 4
    victim.stage3 shouldBe 0
  }

  test("Gaining power resource overflow stage1") {
    val victim = new PowerResource(2, 3, 0)

    victim.gain(3)
    victim.stage1 shouldBe 0
    victim.stage2 shouldBe 4
    victim.stage3 shouldBe 1
  }

  test("Gaining power resource overflow all stages") {
    val victim = new PowerResource(2, 3, 0)

    victim.gain(10)
    victim.stage1 shouldBe 0
    victim.stage2 shouldBe 0
    victim.stage3 shouldBe 5
  }

  test("Sacrificing power resource") {
    val victim = new PowerResource(5, 7, 4)

    victim.sacrifice(1)
    victim.stage1 shouldBe 5
    victim.stage2 shouldBe 5
    victim.stage3 shouldBe 5
  }

  test("cloning generic resource") {
    val victim = GenericResource(5)
    val cloned = victim.clone
    victim shouldBe cloned
  }

  test("cloning priest resource") {
    val victim = new PriestResource(5, 10)
    val cloned = victim.clone
    victim.amount shouldBe cloned.amount
    victim.factionSupply shouldBe cloned.factionSupply
  }

  test("cloning bridge resource") {
    val victim = new BridgeResource(5, 4)
    val cloned = victim.clone
    victim.amount shouldBe cloned.amount
    victim.factionSupply shouldBe cloned.factionSupply
  }

  test("cloning power resource") {
    val victim = new PowerResource(7, 6, 5)
    val cloned = victim.clone
    victim.stage1 shouldBe cloned.stage1
    victim.stage2 shouldBe cloned.stage2
    victim.stage3 shouldBe cloned.stage3
  }
}
