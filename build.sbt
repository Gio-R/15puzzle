import Dependencies._

val Http4sVersion = "1.0.0-M23"
val CirceVersion = "0.14.1"

ThisBuild / scalaVersion     := "3.0.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = (project in file("."))
  .settings(
    name := "15puzzle",
    libraryDependencies ++= Seq(
      "org.scalameta"   %% "munit"               % "0.7.29" % Test,
      "ch.qos.logback"  % "logback-classic"      % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
      ("org.http4s"     %% "http4s-ember-server" % Http4sVersion).cross(CrossVersion.for3Use2_13),
      ("org.http4s"     %% "http4s-circe"        % Http4sVersion).cross(CrossVersion.for3Use2_13),
      ("org.http4s"     %% "http4s-dsl"          % Http4sVersion).cross(CrossVersion.for3Use2_13),
      ("io.circe"       %% "circe-parser"        % CirceVersion).cross(CrossVersion.for3Use2_13),
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
