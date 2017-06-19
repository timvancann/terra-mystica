object Defaults {

  val bridges = List(
    Bridge(Hex(0, 0), Hex(2, 2)),
    Bridge(Hex(0, 6), Hex(2, 6)),
    Bridge(Hex(0,10), Hex(2,10)),
    Bridge(Hex(1, 0), Hex(3, 0)),
    Bridge(Hex(1, 0), Hex(2, 2)),
    Bridge(Hex(1, 3), Hex(2, 2)),
    Bridge(Hex(1, 4), Hex(2, 6))
  )

  val tiles = Map(
    Hex(0, 0) -> Tile(TerrainType.Plains),
    Hex(0, 1) -> Tile(TerrainType.Mountains),
    Hex(0, 2) -> Tile(TerrainType.Forest),
    Hex(0, 3) -> Tile(TerrainType.Lakes),
    Hex(0, 4) -> Tile(TerrainType.Desert),
    Hex(0, 5) -> Tile(TerrainType.Wasteland),
    Hex(0, 6) -> Tile(TerrainType.Plains),
    Hex(0, 7) -> Tile(TerrainType.Swamp),
    Hex(0, 8) -> Tile(TerrainType.Wasteland),
    Hex(0, 9) -> Tile(TerrainType.Forest),
    Hex(0,10) -> Tile(TerrainType.Lakes),
    Hex(0,11) -> Tile(TerrainType.Wasteland),
    Hex(0,12) -> Tile(TerrainType.Swamp),

    Hex(1, 0) -> Tile(TerrainType.Desert),
    Hex(1, 1) -> Tile(TerrainType.Sea),
    Hex(1, 2) -> Tile(TerrainType.Sea),
    Hex(1, 3) -> Tile(TerrainType.Plains),
    Hex(1, 4) -> Tile(TerrainType.Swamp),
    Hex(1, 5) -> Tile(TerrainType.Sea),
    Hex(1, 6) -> Tile(TerrainType.Sea),
    Hex(1, 7) -> Tile(TerrainType.Desert),
    Hex(1, 8) -> Tile(TerrainType.Swamp),
    Hex(1, 9) -> Tile(TerrainType.Sea),
    Hex(1,10) -> Tile(TerrainType.Sea),
    Hex(1,11) -> Tile(TerrainType.Desert),

    Hex(2, 0) -> Tile(TerrainType.Sea),
    Hex(2, 1) -> Tile(TerrainType.Sea),
    Hex(2, 2) -> Tile(TerrainType.Swamp),
    Hex(2, 3) -> Tile(TerrainType.Sea),
    Hex(2, 4) -> Tile(TerrainType.Mountains),
    Hex(2, 5) -> Tile(TerrainType.Sea),
    Hex(2, 6) -> Tile(TerrainType.Forest),
    Hex(2, 7) -> Tile(TerrainType.Sea),
    Hex(2, 8) -> Tile(TerrainType.Forest),
    Hex(2, 9) -> Tile(TerrainType.Sea),
    Hex(2,10) -> Tile(TerrainType.Mountains),
    Hex(2,11) -> Tile(TerrainType.Sea),
    Hex(2,12) -> Tile(TerrainType.Sea),

    Hex(3, 0) -> Tile(TerrainType.Forest),
    Hex(3, 1) -> Tile(TerrainType.Lakes),
    Hex(3, 2) -> Tile(TerrainType.Desert),
    Hex(3, 3) -> Tile(TerrainType.Sea),
    Hex(3, 4) -> Tile(TerrainType.Sea),
    Hex(3, 5) -> Tile(TerrainType.Wasteland),
    Hex(3, 6) -> Tile(TerrainType.Lakes),
    Hex(3, 7) -> Tile(TerrainType.Sea),
    Hex(3, 8) -> Tile(TerrainType.Wasteland),
    Hex(3, 9) -> Tile(TerrainType.Sea),
    Hex(3,10) -> Tile(TerrainType.Wasteland),
    Hex(3,11) -> Tile(TerrainType.Plains)

  )
}
