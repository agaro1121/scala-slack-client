import Publish.sharedPublishSettings

organization in ThisBuild := "com.github.agaro1121"
scalaVersion in ThisBuild := "2.12.4"
crossScalaVersions in ThisBuild := Seq("2.11.11", scalaVersion.value)

val compileSettings = Seq(
  libraryDependencies ++= Dependencies.compile
)

val core = project
  .in(file("core"))
  .settings(name := "scala-slack-core")
  .settings(compileSettings)
  .settings(libraryDependencies += Dependencies.ScalaTest)
  .settings(sharedPublishSettings: _*)

val sharedEvents = project
  .in(file("shared_events"))
  .settings(name := "scala-slack-shared-events")
  .settings(libraryDependencies ++= Dependencies.Circe)
  .dependsOn(core)
  .settings(libraryDependencies += Dependencies.ScalaTest)
  .settings(sharedPublishSettings: _*)

val rtm = project
  .in(file("rtm"))
  .settings(name := "scala-slack-rtm")
  .settings(compileSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(libraryDependencies ++= Seq(Dependencies.ScalaTest, Dependencies.ScalaTestIt))
  .dependsOn(core, sharedEvents)
  .settings(sharedPublishSettings: _*)

val web = project
  .in(file("web"))
  .settings(name := "scala-slack-web")
  .settings(compileSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(libraryDependencies ++= Seq(Dependencies.ScalaTest, Dependencies.ScalaTestIt))
  .dependsOn(core)
  .settings(sharedPublishSettings: _*)

val root = project
  .in(file("."))
  .aggregate(core, sharedEvents, rtm, web)
  .settings(publishArtifact := false)
  .settings(publish := {})
  .settings(publishLocal := {})
  .enablePlugins(GitVersioning)
  .settings(sharedPublishSettings: _*)
