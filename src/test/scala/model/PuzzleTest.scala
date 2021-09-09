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
      // val puzzle = Puzzle(Map((1, 1), Number(13)),
      //                         (1, 2), Number(13)),
      //                         (1, 3), Number(13)),
      //                         (1, 4), Number(13)),
      //                         (2, 1), Number(13)),
      //                         (2, 2), Number(13)),
      //                         (2, 3), Number(13)),
      //                         (2, 4), Number(13)),
      //                         (3, 1), Number(13)),
      //                         (3, 2), Number(13)),
      //                         (3, 3), Number(13)),
      //                         (3, 4), Number(13)),
      //                         (4, 1), Number(13)),
      //                         (4, 2), Number(13)),
      //                         (4, 3), Number(13)),
      //                         (4, 4), Number(13)),
      //                        )
    }

    test("Unsolvable puzzle is recognised as such") {

    }
      