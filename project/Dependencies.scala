import sbt._

object Dependencies {

  object Versions {
    val AkkaHttp = "10.1.5"
    val Akka = "2.5.17"
    val Circe = "0.10.0"
    val Cats = "1.4.0"
    val PureConfig = "0.9.2"
    val ScalaLogging = "3.9.0"
    val LogBack = "1.2.3"
    val AkkaHttpCirce = "1.22.0"
    val ScalaTest = "3.0.5"
  }


  val AkkaHttp = "com.typesafe.akka" %% "akka-http" % Versions.AkkaHttp
  lazy val AkkaStream = "com.typesafe.akka" %% "akka-stream" % Versions.Akka
  lazy val AkkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Versions.Akka

  val Cats = "org.typelevel" %% "cats-core" % Versions.Cats

  val Circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-shapes"
  ).map(_ % Versions.Circe)

  val PureConfig = "com.github.pureconfig" %% "pureconfig" % Versions.PureConfig

  val AkkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % Versions.AkkaHttpCirce
  private val ScalaTestBase = "org.scalatest" %% "scalatest" % Versions.ScalaTest
  val ScalaTest = ScalaTestBase % "test"
  val ScalaTestIt = ScalaTestBase % "it"
  val ScalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % Versions.ScalaLogging
  val Logback =  "ch.qos.logback" % "logback-classic" % Versions.LogBack

  val compile = Seq(
     AkkaHttp
    ,AkkaStream
    ,AkkaTestkit
    ,Cats
    ,PureConfig
    ,AkkaHttpCirce
    ,ScalaLogging
    ,Logback) ++ Circe
}
