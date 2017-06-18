trait FactionSupply {
  def addPriest(n: Int = 1): Unit = {

  }
}

trait Faction {

  def supply: FactionSupply

  def removePriest(n: Int = 1): Unit = {

  }

}
