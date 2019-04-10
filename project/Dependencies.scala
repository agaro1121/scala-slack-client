import sbt._

object Dependencies {

  object Versions {
    val AkkaHttp = "10.1.8"
    val Akka = "2.5.22"
    val Circe = "0.11.1"
    val Cats = "1.6.0"
    val PureConfig = "0.10.2"
    val ScalaLogging = "3.9.2"
    val LogBack = "1.2.3"
    val AkkaHttpCirce = "1.25.2"
    val ScalaTest = "3.0.7"
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
