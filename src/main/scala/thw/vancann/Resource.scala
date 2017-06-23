package thw.vancann

trait Resource {
  def spend(n: Int): Unit

  def gain(n: Int): Unit

  def sacrifice(n: Int): Unit

  def amountToSpend: Int

  override def clone: Resource
}

case class GenericResource(var amount: Int = 0) extends Resource {
  override def spend(n: Int): Unit = amount -= n

  override def gain(n: Int): Unit = amount += n

  override def sacrifice(n: Int): Unit = amount -= n

  def amountToSpend: Int = amount

  override def clone: GenericResource = GenericResource(amount)
}

class PriestResource(amount: Int = 0, var factionSupply: Int = 5) extends GenericResource(amount) {
  override def gain(n: Int): Unit = {
    super.gain(n)
    factionSupply -= n
  }

  override def spend(n: Int = 1): Unit = {
    super.spend(n)
    factionSupply += n
  }
  override def clone: PriestResource = new PriestResource(amount, factionSupply)
}

class BridgeResource(amount: Int = 0, var factionSupply: Int = 3) extends GenericResource(amount) {
  override def sacrifice(n: Int): Unit = factionSupply -= 1
  override def amountToSpend: Int = factionSupply
  override def clone: BridgeResource = new BridgeResource(amount, factionSupply)
}

class PowerResource(var stage1: Int = 5, var stage2: Int = 7: Int, var stage3: Int = 0) extends Resource {
  override def spend(n: Int): Unit = {
    stage3 -= n
    stage1 += n
  }

  override def gain(n: Int): Unit = {
    var toGain = n
    val stage1To2 = Math.min(toGain, stage1)
    stage1 -= stage1To2
    stage2 += stage1To2
    toGain -= stage1To2

    val stage2To3 = Math.min(toGain, stage2)
    stage2 -= stage2To3
    stage3 += stage2To3
  }

  override def sacrifice(n: Int): Unit = {
    stage2 -= n * 2
    stage3 += n
  }

  def amountToSpend: Int = stage3

  override def clone: PowerResource = new PowerResource(stage1, stage2, stage3)
}