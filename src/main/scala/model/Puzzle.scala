package model

class Puzzle private (val tiles: Map[(Int, Int), Tile]):
  private val size = scala.math.sqrt(tiles.size).toInt
  private val _tilesMap = scala.collection.mutable.Map(tiles.toSeq: _*)

  require(tiles.size > 3)
  require(scala.math.sqrt(tiles.size).isValidInt)
  require(tiles.find(_._2 == Empty()).map(_._1._1).isDefined)

  def tilesMap = _tilesMap.toMap

  
end Puzzle

object Puzzle:
  def createRandomPuzzle(puzzleSize: Int): Puzzle =
    require(puzzleSize > 3)
    require(scala.math.sqrt(puzzleSize).isValidInt)
    var tiles = generateRandomTilesSequence(tileMaxNumber = puzzleSize - 1)
    while (!isPuzzleSolvable(Puzzle(tiles)))
      tiles = generateRandomTilesSequence(tileMaxNumber = puzzleSize - 1)
    Puzzle(tiles)

  def createPuzzleFromTiles(tiles: Map[(Int, Int), Tile]): Puzzle =
    Puzzle(tiles)

  private def generateRandomTilesSequence(tileMaxNumber: Int): Map[(Int, Int), Tile] =
    val size = scala.math.sqrt(tileMaxNumber + 1).toInt
    val tiles = (1 to tileMaxNumber).map(Number(_))
    val map = scala.collection.mutable.Map[(Int, Int), Tile]()
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
          tiles(0)
        map.addOne(((i, j), tile))
    map.toMap

  def isPuzzleSolvable(puzzle: Puzzle): Boolean =
    val emptyTileRow = puzzle.tiles.find(_._2 == Empty()).map(_._1._1)
    assert(emptyTileRow.isDefined)
    val orderedTiles = puzzle.tiles
                             .map((p, t) => (((p._1 - 1) * puzzle.size + p._2), t))
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
      
         



