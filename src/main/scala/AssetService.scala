package puzzle

import cats.effect.IO
import org.http4s.{HttpRoutes, StaticFile}
import org.http4s.dsl.io.*
import org.http4s.server.staticcontent.*
import java.io.File
import com.typesafe.scalalogging.Logger

/**
 * This service is responsible for serving static files. We serve anything found
 * in the src/main/resources subdirectory
 * interface.
 */
object AssetService:
  def service: HttpRoutes[IO] =
    HttpRoutes.of[IO] {
      case request @ GET -> Root =>
        Logger(webServerLoggerName).debug("requested static root")
        StaticFile.fromFile(new File("src/main/resources/index.html"), Some(request)).getOrElseF(NotFound())
      case request @ GET -> Root / fileName =>
        Logger(webServerLoggerName).debug(s"requested $fileName")
        StaticFile.fromFile(new File(s"src/main/resources/$fileName"), Some(request)).getOrElseF(NotFound())
    }
