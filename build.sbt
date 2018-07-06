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

val rtmLite = project
  .in(file("rtm_lite"))
  .settings(name := "scala-slack-rtm-lite")
  .settings(compileSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(libraryDependencies ++= Seq(Dependencies.ScalaTest, Dependencies.ScalaTestIt))
  .dependsOn(core, sharedEvents)
  .settings(sharedPublishSettings: _*)
  .enablePlugins(GitVersioning)

val rtmLiteBenchmark = project
  .in(file("rtm_lite_benchmark"))
  .settings(name := "scala-slack-rtm-lite-benchmark")
//  .settings(compileSettings)
//  .configs(IntegrationTest)
//  .settings(Defaults.itSettings: _*)
//  .settings(libraryDependencies ++= Seq(Dependencies.ScalaTest, Dependencies.ScalaTestIt))
  .dependsOn(rtmLite)
//  .settings(sharedPublishSettings: _*)
//  .enablePlugins(GitVersioning)
  .enablePlugins(JmhPlugin)


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
  .settings(sharedPublishSettings: _*)
  .aggregate(core, sharedEvents, web, rtmLite)
  .settings(Defaults.coreDefaultSettings ++ Seq(
    publishArtifact:= false,
    publishLocal := {},
    publish := {},
    bintrayRelease := {},
    bintraySyncMavenCentral := {}
  ))
  .enablePlugins(GitVersioning)

useGpg := false
pgpPublicRing := baseDirectory.value / "travis" / "local.pubring.asc"
pgpSecretRing := baseDirectory.value / "travis" / "local.secring.asc"
pgpPassphrase := sys.env.get("PGP_PASS").map(_.toArray)
scalacOptions in ThisBuild += "-Ypartial-unification"
