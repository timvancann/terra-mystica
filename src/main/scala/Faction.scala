trait FactionSupply {
  def addPriest(n: Int = 1) = ???
}

trait Faction {

  val supply: FactionSupply

  def removePriest(n: Int = 1) = ???

}
