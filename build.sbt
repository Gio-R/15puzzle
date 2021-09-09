import Dependencies._

ThisBuild / scalaVersion     := "3.0.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .settings(
    name := "15puzzle",
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29"
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
