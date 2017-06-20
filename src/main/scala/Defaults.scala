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

  val tiles = List(
    Tile(Hex(0, 0), TerrainType.Plains),
    Tile(Hex(0, 1), TerrainType.Mountains),
    Tile(Hex(0, 2), TerrainType.Forest),
    Tile(Hex(0, 3), TerrainType.Lakes),
    Tile(Hex(0, 4), TerrainType.Desert),
    Tile(Hex(0, 5), TerrainType.Wasteland),
    Tile(Hex(0, 6), TerrainType.Plains),
    Tile(Hex(0, 7), TerrainType.Swamp),
    Tile(Hex(0, 8), TerrainType.Wasteland),
    Tile(Hex(0, 9), TerrainType.Forest),
    Tile(Hex(0,10), TerrainType.Lakes),
    Tile(Hex(0,11), TerrainType.Wasteland),
    Tile(Hex(0,12), TerrainType.Swamp),

    Tile(Hex(1, 0), TerrainType.Desert),
    Tile(Hex(1, 1), TerrainType.Sea),
    Tile(Hex(1, 2), TerrainType.Sea),
    Tile(Hex(1, 3), TerrainType.Plains),
    Tile(Hex(1, 4), TerrainType.Swamp),
    Tile(Hex(1, 5), TerrainType.Sea),
    Tile(Hex(1, 6), TerrainType.Sea),
    Tile(Hex(1, 7), TerrainType.Desert),
    Tile(Hex(1, 8), TerrainType.Swamp),
    Tile(Hex(1, 9), TerrainType.Sea),
    Tile(Hex(1,10), TerrainType.Sea),
    Tile(Hex(1,11), TerrainType.Desert),

    Tile(Hex(2, 0), TerrainType.Sea),
    Tile(Hex(2, 1), TerrainType.Sea),
    Tile(Hex(2, 2), TerrainType.Swamp),
    Tile(Hex(2, 3), TerrainType.Sea),
    Tile(Hex(2, 4), TerrainType.Mountains),
    Tile(Hex(2, 5), TerrainType.Sea),
    Tile(Hex(2, 6), TerrainType.Forest),
    Tile(Hex(2, 7), TerrainType.Sea),
    Tile(Hex(2, 8), TerrainType.Forest),
    Tile(Hex(2, 9), TerrainType.Sea),
    Tile(Hex(2,10), TerrainType.Mountains),
    Tile(Hex(2,11), TerrainType.Sea),
    Tile(Hex(2,12), TerrainType.Sea),

    Tile(Hex(3, 0), TerrainType.Forest),
    Tile(Hex(3, 1), TerrainType.Lakes),
    Tile(Hex(3, 2), TerrainType.Desert),
    Tile(Hex(3, 3), TerrainType.Sea),
    Tile(Hex(3, 4), TerrainType.Sea),
    Tile(Hex(3, 5), TerrainType.Wasteland),
    Tile(Hex(3, 6), TerrainType.Lakes),
    Tile(Hex(3, 7), TerrainType.Sea),
    Tile(Hex(3, 8), TerrainType.Wasteland),
    Tile(Hex(3, 9), TerrainType.Sea),
    Tile(Hex(3,10), TerrainType.Wasteland),
    Tile(Hex(3,11), TerrainType.Plains)
  )
}
