package puzzle.model

class PuzzleSuite extends munit.FunSuite:
  val testMap = 
    
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

  test("All tiles are inserted") {
    val maxTileNumber = 15
    val puzzle = Puzzle.createRandomPuzzle(puzzleSize = maxTileNumber + 1)
    assert(puzzle.tilesMap.contains(Empty()))
    val numbers = puzzle.tilesMap
                        .keys
                        .filter(_.isInstanceOf[Number])
                        .map(_.asInstanceOf[Number].n)
                        .toSet
    assertEquals((1 to maxTileNumber).toSet, numbers)
  }

  test("Solvable puzzle is recognised as such") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(13), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(10), (1, 3)),
                                                  (Number(3), (1, 4)),
                                                  (Number(1), (2, 1)),
                                                  (Number(12), (2, 2)),
                                                  (Number(8), (2, 3)),
                                                  (Number(4), (2, 4)),
                                                  (Number(5), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(9), (3, 3)),
                                                  (Number(6), (3, 4)),
                                                  (Number(15), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(11), (4, 3)),
                                                  (Number(7), (4, 4)),
                                                 )
                                             )                                       
    assert(Puzzle.isPuzzleSolvable(puzzle = puzzle))
  }

  test("Unsolvable puzzle is recognised as such") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(3), (1, 1)),
                                                  (Number(9), (1, 2)),
                                                  (Number(1), (1, 3)),
                                                  (Number(15), (1, 4)),
                                                  (Number(14), (2, 1)),
                                                  (Number(11), (2, 2)),
                                                  (Number(4), (2, 3)),
                                                  (Number(6), (2, 4)),
                                                  (Number(13), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(10), (3, 3)),
                                                  (Number(12), (3, 4)),
                                                  (Number(2), (4, 1)),
                                                  (Number(7), (4, 2)),
                                                  (Number(8), (4, 3)),
                                                  (Number(5), (4, 4)),
                                                 )
                                              )
    assert(!Puzzle.isPuzzleSolvable(puzzle = puzzle))
  }

  test("Puzzle without empty tile cannot be created") {
    intercept[IllegalArgumentException](Puzzle.createPuzzleFromTiles(Map((Number(1), (1, 1)),
                                                                         (Number(2), (1, 2)),
                                                                         (Number(3), (1, 3)),
                                                                         (Number(4), (1, 4)),
                                                                        ) 
                                                                    )
                                        )
  }

  test("Tiles map with missing numbered tiles are not accepted") {
    intercept[IllegalArgumentException](Puzzle.createPuzzleFromTiles(Map((Number(1), (1, 1)),
                                                                         (Number(2), (1, 2)),
                                                                         (Number(5), (1, 3)),
                                                                         (Empty(), (1, 4)),
                                                                        ) 
                                                                    )
                                       )
  }

  test("Puzzle is correctly created from a map of tiles") {
    val tilesMap = Map((Number(13), (1, 1)),
                       (Number(2), (1, 2)),
                       (Number(10), (1, 3)),
                       (Number(3), (1, 4)),
                       (Number(1), (2, 1)),
                       (Number(12), (2, 2)),
                       (Number(8), (2, 3)),
                       (Number(4), (2, 4)),
                       (Number(5), (3, 1)),
                       (Empty(), (3, 2)),
                       (Number(9), (3, 3)),
                       (Number(6), (3, 4)),
                       (Number(15), (4, 1)),
                       (Number(14), (4, 2)),
                       (Number(11), (4, 3)),
                       (Number(7), (4, 4)),
                      )
    val puzzle = Puzzle.createPuzzleFromTiles(tilesMap)                                       
    assertEquals(obtained = puzzle.tilesMap, expected = tilesMap)
  }

  test("Numbered tile and empty tile can be switched if adjacent on the same line") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(13), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(10), (1, 3)),
                                                  (Number(3), (1, 4)),
                                                  (Number(1), (2, 1)),
                                                  (Number(12), (2, 2)),
                                                  (Number(8), (2, 3)),
                                                  (Number(4), (2, 4)),
                                                  (Number(5), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(9), (3, 3)),
                                                  (Number(6), (3, 4)),
                                                  (Number(15), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(11), (4, 3)),
                                                  (Number(7), (4, 4)),
                                                  )
                                              )  
    assert(puzzle.moveTile(Number(9)))
    assertEquals(puzzle.tilesMap.get(Number(9)), Some((3, 2)))
    assertEquals(puzzle.tilesMap.get(Empty()), Some((3, 3)))
    assert(puzzle.moveTile(Number(9)))
    assertEquals(puzzle.tilesMap.get(Number(9)), Some((3, 3)))
    assertEquals(puzzle.tilesMap.get(Empty()), Some((3, 2)))
  }

  test("Numbered tile and empty tile can be switched if adjacent on the same column") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(13), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(10), (1, 3)),
                                                  (Number(3), (1, 4)),
                                                  (Number(1), (2, 1)),
                                                  (Number(12), (2, 2)),
                                                  (Number(8), (2, 3)),
                                                  (Number(4), (2, 4)),
                                                  (Number(5), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(9), (3, 3)),
                                                  (Number(6), (3, 4)),
                                                  (Number(15), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(11), (4, 3)),
                                                  (Number(7), (4, 4)),
                                                  )
                                              )  
    assert(puzzle.moveTile(Number(12)))
    assertEquals(puzzle.tilesMap.get(Number(12)), Some((3, 2)))
    assertEquals(puzzle.tilesMap.get(Empty()), Some((2, 2)))
    assert(puzzle.moveTile(Number(12)))
    assertEquals(puzzle.tilesMap.get(Number(12)), Some((2, 2)))
    assertEquals(puzzle.tilesMap.get(Empty()), Some((3, 2)))
  }
  
  test("Numbered tile and empty tile can not be switched if not adjacent in any way") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(13), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(10), (1, 3)),
                                                  (Number(3), (1, 4)),
                                                  (Number(1), (2, 1)),
                                                  (Number(12), (2, 2)),
                                                  (Number(8), (2, 3)),
                                                  (Number(4), (2, 4)),
                                                  (Number(5), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(9), (3, 3)),
                                                  (Number(6), (3, 4)),
                                                  (Number(15), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(11), (4, 3)),
                                                  (Number(7), (4, 4)),
                                                  )
                                              )  
    assert(!puzzle.moveTile(Number(7)))
  }

  test("Non existent tile cannot be switched") {
    val puzzle = Puzzle.createRandomPuzzle(9)
    intercept[IllegalArgumentException](puzzle.moveTile(Number(10)))
  }

  test("Not resolved puzzle is recognized as such") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(13), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(10), (1, 3)),
                                                  (Number(3), (1, 4)),
                                                  (Number(1), (2, 1)),
                                                  (Number(12), (2, 2)),
                                                  (Number(8), (2, 3)),
                                                  (Number(4), (2, 4)),
                                                  (Number(5), (3, 1)),
                                                  (Empty(), (3, 2)),
                                                  (Number(9), (3, 3)),
                                                  (Number(6), (3, 4)),
                                                  (Number(15), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(11), (4, 3)),
                                                  (Number(7), (4, 4)),
                                                  )
                                              ) 
    assert(!puzzle.isResolved)
  }

  test("Resolved puzzle is recognized as such") {
    val puzzle = Puzzle.createPuzzleFromTiles(Map((Number(1), (1, 1)),
                                                  (Number(2), (1, 2)),
                                                  (Number(3), (1, 3)),
                                                  (Number(4), (1, 4)),
                                                  (Number(5), (2, 1)),
                                                  (Number(6), (2, 2)),
                                                  (Number(7), (2, 3)),
                                                  (Number(8), (2, 4)),
                                                  (Number(9), (3, 1)),
                                                  (Number(10), (3, 2)),
                                                  (Number(11), (3, 3)),
                                                  (Number(12), (3, 4)),
                                                  (Number(13), (4, 1)),
                                                  (Number(14), (4, 2)),
                                                  (Number(15), (4, 3)),
                                                  (Empty(), (4, 4)),
                                                  )
                                              ) 
    assert(puzzle.isResolved)
  }
      