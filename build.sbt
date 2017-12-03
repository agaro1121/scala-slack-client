import Publish.sharedPublishSettings

val compileSettings = Seq(
  libraryDependencies ++= Dependencies.compile
)

val core = project
  .in(file("core"))
  .settings(name := "scala-slack-core")
  .settings(compileSettings)
  .settings(libraryDependencies += Dependencies.ScalaTest)
  .settings(sharedPublishSettings: _*)
  .enablePlugins(GitVersioning)


val sharedEvents = project
  .in(file("shared_events"))
  .settings(name := "scala-slack-shared-events")
  .settings(libraryDependencies ++= Dependencies.Circe)
  .dependsOn(core)
  .settings(libraryDependencies += Dependencies.ScalaTest)
  .settings(sharedPublishSettings: _*)
  .enablePlugins(GitVersioning)

val rtm = project
  .in(file("rtm"))
  .settings(name := "scala-slack-rtm")
  .settings(compileSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(libraryDependencies ++= Seq(Dependencies.ScalaTest, Dependencies.ScalaTestIt))
  .dependsOn(core, sharedEvents)
  .settings(sharedPublishSettings: _*)
  .enablePlugins(GitVersioning)

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
  .settings(Project.defaultSettings ++ Seq(
    publishArtifact:= false,
    publishLocal := {},
    publish := {}
  ))
  .enablePlugins(GitVersioning)

// publish in root := {}
// publishLocal in root := {}
