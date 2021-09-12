package puzzle

import cats.effect.IO
import io.circe.Encoder
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityDecoder.*
import org.http4s.dsl.io.*
import puzzle.model.*
import com.typesafe.scalalogging.Logger

implicit val encodeCollection: Encoder[Puzzle] = (puzzle: Puzzle) => 
  puzzle.tilesMap
        .toList
        .map((t, p) => 
          t match
            case Number(n) => p.*:(n) 
            case Empty() => p.*:(0)
        )
        .asJson

class PuzzleService(model: Model):
  val service: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "current" => 
        Logger(webServerLoggerName).debug("requested current puzzle")
        Ok.apply(model.getCurrentPuzzle().asJson)

      case req @ POST -> Root / "new" =>
        req.decode[Int] { i =>
          Logger(webServerLoggerName).debug(s"requested new puzzle of size $i")
          if i > 3 && scala.math.sqrt(i).isValidInt then
            model.createPuzzle(i)
            Created.apply(model.getCurrentPuzzle().asJson) // TODO: return puzzle representation
          else
            BadRequest()
        }

      case req @ POST -> Root / "current" / "moveTile" =>
        req.decode[Int] { i =>
          Logger(webServerLoggerName).debug(s"requested tile $i move")
          try 
            if model.moveTile(i) then
              Ok.apply(model.getCurrentPuzzle().asJson)
            else
              Ok()
          catch
            case e: Exception => BadRequest()
        }

      case req @ POST -> Root / "current" / "reset" =>
        Logger(webServerLoggerName).debug("requested puzzle reset")
        model.resetCurrentPuzzle()
        Ok.apply(model.getCurrentPuzzle().asJson)
    }