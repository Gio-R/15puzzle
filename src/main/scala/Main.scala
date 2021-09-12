package puzzle

import cats.effect.{ExitCode, IO, IOApp, Resource}
import fs2.Stream
import io.circe.Encoder
import io.circe.syntax.*
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl.io.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.{Router, Server}
import org.http4s.server.middleware.CORS
import org.http4s.syntax.kleisli.*
import puzzle.model.Puzzle
import com.comcast.ip4s.Host
import com.comcast.ip4s.Port

object Main extends IOApp:
  private def app(): HttpApp[IO] = 
    Router.define(
      "/puzzle" -> CORS(PuzzleService(BaseModel).service)
    )(AssetService.service).orNotFound

  private def server(): Resource[IO, Server] =
    EmberServerBuilder
      .default[IO]
      .withHost(Host.fromString("0.0.0.0").get) // TODO:
      .withPort(Port.fromInt(3000).get) // TODO:
      .withHttpApp(app())
      .build

  private val program: Stream[IO, Unit] =
    for 
      server <- Stream.resource(server())
      _ <- Stream.eval(IO(println(s"Started server on: ${server.address}")))
      _ <- Stream.never[IO].covaryOutput[Unit]
    yield ()

  def run(args: List[String]) =
    program.compile.drain.as(ExitCode.Success)