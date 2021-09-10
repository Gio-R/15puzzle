package puzzle.model

sealed trait Tile

case class Number(n: Int) extends Tile
case class Empty() extends Tile