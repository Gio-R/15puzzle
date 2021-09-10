package model

import scala.collection.mutable as Mutable

class Puzzle private (val tiles: Map[Tile, (Int, Int)]):
  require(tiles.size > 3)
  require(scala.math.sqrt(tiles.size).isValidInt)
  require(tiles.contains(Empty()))

  private val size = scala.math.sqrt(tiles.size).toInt
  private val _tilesMap = Mutable.Map(tiles.toSeq: _*)

  def tilesMap = _tilesMap.toMap

  def moveTile(tileNumber: Int): Boolean = ???
    // require(tileNumber < size * size)

end Puzzle

object Puzzle:
  def createRandomPuzzle(puzzleSize: Int): Puzzle =
    require(puzzleSize > 3)
    require(scala.math.sqrt(puzzleSize).isValidInt)
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
    val emptyTileRow = puzzle.tiles.get(Empty()).map(_._1)
    assert(emptyTileRow.isDefined)
    val orderedTiles = puzzle.tiles
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
      
         



