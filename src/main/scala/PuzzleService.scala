package puzzle

import cats.effect.IO
import io.circe.Encoder
import io.circe.syntax.*
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityDecoder.*
import org.http4s.dsl.io.*

class PuzzleService(model: Model):
  val service: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case GET -> Root / "current" => Ok() // TODO: return puzzle representation

      case req @ POST -> Root / "new" =>
        req.decode[Int] { i =>
          if i > 3 && scala.math.sqrt(i).isValidInt then
            model.createPuzzle(i)
            Created() // TODO: return puzzle representation
          else
            BadRequest()
        }

      case req @ POST -> Root / "current" / "moveTile" =>
        req.decode[Int] { i =>
          try 
            if model.moveTile(i) then
              Ok()
            else
              BadRequest()
          catch
            case e: Exception => BadRequest()
        }
    }