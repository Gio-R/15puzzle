package puzzle.model

import scala.collection.mutable as Mutable

class Puzzle private (tiles: Map[Tile, (Int, Int)]):
  require(tiles.size > 3, "Too few tiles in map")
  require(scala.math.sqrt(tiles.size).isValidInt, "Tiles can not form a square")
  for
    i <- 1 to scala.math.sqrt(tiles.size).toInt
    j <- 1 to scala.math.sqrt(tiles.size).toInt
  do
    require(tiles.values.toSet.contains((i, j)), "Illegal tile position")
  require(tiles.keys
               .filter(_.isInstanceOf[Number])
               .map(_.asInstanceOf[Number].n)
               .toSet
               .equals((1 to tiles.size - 1).toSet), 
          "Not all numbered tiles are present")
  require(tiles.contains(Empty()), "There is no Empty tile in map")

  private val size = scala.math.sqrt(tiles.size).toInt
  private val _tilesMap = Mutable.Map(tiles.toSeq: _*)

  def tilesMap = _tilesMap.toMap

  def moveTile(tile: Number): Boolean =
    require(tile.n < size * size, "Tile number too big for this puzzle")
    if areAdjacent(tile, Empty()) then
      switchTiles(tile, Empty())
      true
    else
      false
   
  def isResolved: Boolean =
    val orderedTiles = _tilesMap.map((t, p) => (((p._1 - 1) * this.size + p._2), t))
                                .filter((p, t) => t != Empty())
                                .filter((p, t) => t.isInstanceOf[Number])
                                .map((p, t) => (p, t.asInstanceOf[Number]))
                                .toSeq
                                .sortBy(_._1)
    orderedTiles.size == this.size * this.size - 1 && orderedTiles.forall((p, t) => p == t.n)

  private def areAdjacent(tile1: Tile, tile2: Tile): Boolean =
    val tile1Position = getTilePosition(tile1)
    val tile2Position = getTilePosition(tile2)
    (tile1Position._1 == tile2Position._1 && math.abs(tile1Position._2 - tile2Position._2) == 1)
      || (tile1Position._2 == tile2Position._2 && math.abs(tile1Position._1 - tile2Position._1) == 1)

  private def switchTiles(tile1: Tile, tile2: Tile): Unit = 
    val tile1Position = getTilePosition(tile1)
    val tile2Position = getTilePosition(tile2)
    _tilesMap(tile1) = tile2Position
    _tilesMap(tile2) = tile1Position

  private def getTilePosition(tile: Tile): (Int, Int) =
    assert(_tilesMap.contains(tile), "Tile is not in the puzzle")
    _tilesMap.get(tile).get

end Puzzle

object Puzzle:
  def createRandomPuzzle(puzzleSize: Int): Puzzle =
    require(puzzleSize > 3, "Puzzle too small")
    require(scala.math.sqrt(puzzleSize).isValidInt, "Puzzle is not a square")
    var tiles = generateRandomTilesSequence(tileMaxNumber = puzzleSize - 1)
    while (!isPuzzleSolvable(Puzzle(tiles)))
      tiles = generateRandomTilesSequence(tileMaxNumber = puzzleSize - 1)
    Puzzle(tiles)

  def createPuzzleFromTiles(tiles: Map[Tile, (Int, Int)]): Puzzle =
    Puzzle(tiles)

  private def generateRandomTilesSequence(tileMaxNumber: Int): Map[Tile, (Int, Int)] =
    val size = scala.math.sqrt(tileMaxNumber + 1).toInt
    val tiles = Mutable.ArrayBuffer()
                       .appendAll((1 to tileMaxNumber))
                       .map(Number(_))
                       .filter(_.isInstanceOf[Tile])
                       .map(_.asInstanceOf[Tile])
    val map = Mutable.Map[Tile, (Int, Int)]()
    var emptyPositioned = false
    while (!emptyPositioned)
      map.clear()
      for 
        i <- 1 to size
        j <- 1 to size
      do
        val tile: Tile = if !emptyPositioned && scala.util.Random.nextBoolean() then
          emptyPositioned = true
          Empty()
        else
          val index = scala.util.Random.between(0, tiles.size)
          val tile = tiles(index)
          tiles -= tile
          tile
        map.addOne((tile, (i, j)))
    map.toMap

  def isPuzzleSolvable(puzzle: Puzzle): Boolean =
    val emptyTileRow = puzzle.tilesMap.get(Empty()).map(_._1)
    assert(emptyTileRow.isDefined, "There is no empty tile in the puzzle")
    val orderedTiles = puzzle.tilesMap
                             .map((t, p) => (((p._1 - 1) * puzzle.size + p._2), t))
                             .toSeq
                             .sortBy(_._1)
                             .map(_._2)
    val orderedNumberedTiles = orderedTiles.filter(_.isInstanceOf[Number]).map(_.asInstanceOf[Number])
    var inversions = 0
    for
      i <- 0 to orderedNumberedTiles.size - 2
      j <- i + 1 to orderedNumberedTiles.size - 1
      if orderedNumberedTiles(i).n > orderedNumberedTiles(j).n
    do
      inversions += 1
    // println(s"puzzle size ${puzzle.size}; inversions $inversions; emtpyTileRow $emptyTileRow")
    ((puzzle.size % 2 != 0 && inversions % 2 == 0)
      || (puzzle.size % 2 == 0 && (
        (inversions % 2 == 0 && emptyTileRow.forall(_ % 2 == 0))
          || (inversions % 2 != 0 && emptyTileRow.forall(_ % 2 != 0))
      )))
end Puzzle
      
         



