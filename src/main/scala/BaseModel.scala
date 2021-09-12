package puzzle

import puzzle.model.*

object BaseModel extends Model:
  private val basePuzzle = Puzzle.createRandomPuzzle(4)
  private var currentPuzzle = basePuzzle
  private var initialPuzzle = basePuzzle

  def createPuzzle(puzzleSize: Int): Unit =
    initialPuzzle = Puzzle.createRandomPuzzle(puzzleSize = puzzleSize)
    currentPuzzle = initialPuzzle

  def moveTile(tileNumber: Int): Boolean =
    val newPuzzle = currentPuzzle.moveTile(Number(tileNumber))
    val response = newPuzzle.equals(currentPuzzle)
    currentPuzzle = newPuzzle
    response

  def resetCurrentPuzzle(): Unit = 
    currentPuzzle = initialPuzzle

  def getCurrentPuzzleGrid(): Map[Tile, (Int, Int)] =
    currentPuzzle.tilesMap

  def isCurrentPuzzleResolved(): Boolean =
    currentPuzzle.isResolved