package thw.vancann

import thw.vancann.BonusTileType._
import thw.vancann.BuildingType._
import thw.vancann.CultType._
import thw.vancann.FactionType._
import thw.vancann.ResourceType._
import thw.vancann.TerrainType._
import thw.vancann.PriestSpaceType._

import scala.collection.mutable

object Defaults {

  def bridges = List(
    TileBridge(Hex(0, 0), Hex(2, 2)),
    TileBridge(Hex(0, 6), Hex(2, 6)),
    TileBridge(Hex(0, 10), Hex(2, 10)),
    TileBridge(Hex(1, 0), Hex(3, 0)),
    TileBridge(Hex(1, 0), Hex(2, 2)),
    TileBridge(Hex(1, 3), Hex(2, 2)),
    TileBridge(Hex(1, 4), Hex(2, 6))
  )
  def tiles = List(
    Tile(Hex(0, 0), Plains),
    Tile(Hex(0, 1), Mountains),
    Tile(Hex(0, 2), Forest),
    Tile(Hex(0, 3), Lakes),
    Tile(Hex(0, 4), Desert),
    Tile(Hex(0, 5), Wasteland),
    Tile(Hex(0, 6), Plains),
    Tile(Hex(0, 7), Swamp),
    Tile(Hex(0, 8), Wasteland),
    Tile(Hex(0, 9), Forest),
    Tile(Hex(0, 10), Lakes),
    Tile(Hex(0, 11), Wasteland),
    Tile(Hex(0, 12), Swamp),

    Tile(Hex(1, 0), Desert),
    Tile(Hex(1, 1), Sea),
    Tile(Hex(1, 2), Sea),
    Tile(Hex(1, 3), Plains),
    Tile(Hex(1, 4), Swamp),
    Tile(Hex(1, 5), Sea),
    Tile(Hex(1, 6), Sea),
    Tile(Hex(1, 7), Desert),
    Tile(Hex(1, 8), Swamp),
    Tile(Hex(1, 9), Sea),
    Tile(Hex(1, 10), Sea),
    Tile(Hex(1, 11), Desert),

    Tile(Hex(2, 0), Sea),
    Tile(Hex(2, 1), Sea),
    Tile(Hex(2, 2), Swamp),
    Tile(Hex(2, 3), Sea),
    Tile(Hex(2, 4), Mountains),
    Tile(Hex(2, 5), Sea),
    Tile(Hex(2, 6), Forest),
    Tile(Hex(2, 7), Sea),
    Tile(Hex(2, 8), Forest),
    Tile(Hex(2, 9), Sea),
    Tile(Hex(2, 10), Mountains),
    Tile(Hex(2, 11), Sea),
    Tile(Hex(2, 12), Sea),

    Tile(Hex(3, 0), Forest),
    Tile(Hex(3, 1), Lakes),
    Tile(Hex(3, 2), Desert),
    Tile(Hex(3, 3), Sea),
    Tile(Hex(3, 4), Sea),
    Tile(Hex(3, 5), Wasteland),
    Tile(Hex(3, 6), Lakes),
    Tile(Hex(3, 7), Sea),
    Tile(Hex(3, 8), Wasteland),
    Tile(Hex(3, 9), Sea),
    Tile(Hex(3, 10), Wasteland),
    Tile(Hex(3, 11), Plains)
  )

  def cultPriestSpaces = List(PriestSpace(Bonus3, 3), PriestSpace(Bonus2, 2), PriestSpace(Bonus2, 2), PriestSpace(Bonus2, 2), PriestSpace(Bonus1, 1))
  def cultProgressSpaces: Seq[ProgressSpace] = (0 to 10)
    .map {
      case i@3 => ProgressSpace(i, 1)
      case i@5 => ProgressSpace(i, 2)
      case i@7 => ProgressSpace(i, 2)
      case i@10 => ProgressSpace(i, 3)
      case i => ProgressSpace(i, 0)
    }

  def cults = Map(
    Water -> Cult(cultPriestSpaces, cultProgressSpaces),
    Fire -> Cult(cultPriestSpaces, cultProgressSpaces),
    Air -> Cult(cultPriestSpaces, cultProgressSpaces),
    Earth -> Cult(cultPriestSpaces, cultProgressSpaces)
  )

  def defaultBuildings = mutable.Map(
    Dwelling -> 8,
    TradingHouse -> 5,
    Temple -> 3,
    Sanctuary -> 1,
    Stronghold -> 1
  )

  def supply = {
    Map(
      Gold -> GenericResource(0),
      Priest -> new PriestResource(0, 5),
      Worker -> GenericResource(0),
      Bridge -> new BridgeResource(0, 3),
      Power -> new PowerResource(5, 7),
      Ship -> GenericResource(0),
      Spade -> GenericResource(0),
      VictoryPoints -> GenericResource(20)
    )
  }
  def factions = Map(
    Halflings -> Faction(
      factionType = Halflings,
      terrain = Plains,
      cultCost = (Priest, 1),
      terraformCost = List((Worker, 3), (Worker, 2), (Worker, 1)),
      shipCost = List((Priest, 1), (Gold, 4)),
      buildingCost = Map(
        Dwelling -> List((Worker, 1), (Gold, 2)),
        TradingHouse -> List((Worker, 2), (Gold, 6)),
        Temple -> List((Worker, 2), (Gold, 5)),
        Sanctuary -> List((Worker, 4), (Gold, 6)),
        Stronghold -> List((Worker, 4), (Gold, 8))
      ),
      availableBuildings = defaultBuildings,
      incomePerBuilding = Map.empty,
      supply = supply
    ),
    ChaosWizards -> Faction(
      factionType = ChaosWizards,
      // TODO: change this to wasteland
      terrain = Mountains,
      cultCost = (Priest, 1),
      terraformCost = List((Worker, 3), (Worker, 2), (Worker, 1)),
      shipCost = List((Priest, 1), (Gold, 4)),
      buildingCost = Map(
        Dwelling -> List((Worker, 1), (Gold, 2)),
        TradingHouse -> List((Worker, 2), (Gold, 6)),
        Temple -> List((Worker, 2), (Gold, 5)),
        Sanctuary -> List((Worker, 4), (Gold, 8)),
        Stronghold -> List((Worker, 4), (Gold, 6))
      ),
      availableBuildings = defaultBuildings,
      incomePerBuilding = Map.empty,
      supply = supply
    )
  )

  def bonusTiles = Map(
    Bon1 -> BonusTile(
      BonusTileActions.gainSpade,
      List((Gold, 2))
    ),
    Bon2 -> BonusTile(
      BonusTileActions.cultTrack,
      income = List((Gold, 4))
    ),
    Bon3 -> BonusTile(
      income = List((Gold, 6))
    ),
    Bon4 -> BonusTile(
      income = List((Power, 3)),
      passiveBonus = List((Ship, 1))
    ),
    Bon5 -> BonusTile(
      income = List((Worker, 1), (Power, 3))
    ),
    Bon6 -> BonusTile(
      income = List((Worker, 2)),
      passBonus = List((Stronghold, 4), (Sanctuary, 4))
    ),
    Bon7 -> BonusTile(
      income = List((Worker, 1)),
      passBonus = List((TradingHouse, 2))
    ),
    Bon8 -> BonusTile(
      income = List((Priest, 1))
    ),
    Bon9 -> BonusTile(
      income = List((Gold, 2)),
      passBonus = List((Dwelling, 1))
    )
  )
}
