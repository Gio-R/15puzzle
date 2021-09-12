package puzzle

import puzzle.model.*

trait Model:
  def createPuzzle(puzzleSize: Int): Unit

  def moveTile(tileNumber: Int): Boolean

  def resetCurrentPuzzle(): Unit

  def getCurrentPuzzleGrid(): Map[Tile, (Int, Int)]

  def isCurrentPuzzleResolved(): Boolean

