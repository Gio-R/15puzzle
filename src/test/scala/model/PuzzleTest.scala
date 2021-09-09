package model

class PuzzleSuite extends munit.FunSuite:
  test("Size less than 4 is not allowed") {
    intercept[java.lang.IllegalArgumentException](Puzzle.createRandomPuzzle(3))
  }

  test("Size can not be negative") {
    intercept[java.lang.IllegalArgumentException](Puzzle.createRandomPuzzle(-1)) 
  }

  test("Size must be a perfect square") {
    Puzzle.createRandomPuzzle(4)
    Puzzle.createRandomPuzzle(9)
    intercept[java.lang.IllegalArgumentException](Puzzle.createRandomPuzzle(5)) 
  }

  test("Solvable puzzle is recognised as such") {
    val puzzle = Puzzle(Map(((1, 1), Number(13)),
                            ((1, 2), Number(2)),
                            ((1, 3), Number(10)),
                            ((1, 4), Number(3)),
                            ((2, 1), Number(1)),
                            ((2, 2), Number(12)),
                            ((2, 3), Number(8)),
                            ((2, 4), Number(4)),
                            ((3, 1), Number(5)),
                            ((3, 2), Empty()),
                            ((3, 3), Number(9)),
                            ((3, 4), Number(6)),
                            ((4, 1), Number(15)),
                            ((4, 2), Number(14)),
                            ((4, 3), Number(11)),
                            ((4, 4), Number(7)),
                            )
                        )
    assert(Puzzle.isPuzzleSolvable(puzzle = puzzle))
  }

  test("Unsolvable puzzle is recognised as such") {
    val puzzle = Puzzle(Map(((1, 1), Number(3)),
                            ((1, 2), Number(9)),
                            ((1, 3), Number(1)),
                            ((1, 4), Number(15)),
                            ((2, 1), Number(14)),
                            ((2, 2), Number(11)),
                            ((2, 3), Number(4)),
                            ((2, 4), Number(6)),
                            ((3, 1), Number(13)),
                            ((3, 2), Empty()),
                            ((3, 3), Number(10)),
                            ((3, 4), Number(12)),
                            ((4, 1), Number(2)),
                            ((4, 2), Number(7)),
                            ((4, 3), Number(8)),
                            ((4, 4), Number(5)),
                            )
                        )
    assert(!Puzzle.isPuzzleSolvable(puzzle = puzzle))
  }

  test("Puzzle without empty tile cannot be created") {
    intercept[IllegalArgumentException](Puzzle(Map(((1, 1), Number(13)),
                                                  ((1, 2), Number(2)),
                                                  ((1, 3), Number(10)),
                                                  ((1, 4), Number(3)))
                                              )
                                        )
  }
      