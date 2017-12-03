import com.typesafe.sbt.SbtGit.git
import sbt._
import sbt.Keys._
import bintray.BintrayKeys._

object Publish {

  private val VersionRegex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r
  private val MilestoneRegex = "^M[0-9]$".r
  private lazy val versioningSettings: Seq[sbt.Def.SettingsDefinition] =
    Seq(
      git.baseVersion := "0.1.3-SNAPSHOT",
      git.useGitDescribe := true,
      git.uncommittedSignifier := None,
      git.gitTagToVersionNumber := {
        case VersionRegex(v, "") => Some(v) //e.g. 1.0.0
        case VersionRegex(v, s)
          if MilestoneRegex.findFirstIn(s).isDefined => Some(s"$v-$s") //e.g. 1.0.0-M1
        case VersionRegex(v, "SNAPSHOT") => Some(s"$v-SNAPSHOT") //e.g. 1.0.0-SNAPSHOT
        case VersionRegex(v, s) => Some(s"$v-$s-SNAPSHOT") //e.g. 1.0.0-2-commithash-SNAPSHOT
        case _ => None
      }
    )

  lazy val sharedPublishSettings: Seq[SettingsDefinition]  = Seq(
    publishTo := {
      if (isSnapshot.value){
        Some("Artifactory Realm" at "http://oss.jfrog.org/artifactory/oss-snapshot-local")
      }
      else {
        publishTo.value
      }
    },
    bintrayPackage := name.value,
    credentials ++= List(Path.userHome / ".bintray" / ".artifactory").filter(_.exists).map(Credentials(_)), //For snapshots
    pomIncludeRepository := { _ => false }, //remove optional dependencies from our pom
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    homepage := Some(url("https://github.com/agaro1121/scala-slack-client")),
    scmInfo := Some(ScmInfo(url("https://github.com/agaro1121/scala-slack-client"), "git@github.com:agaro1121/scala-slack-client.git")),
    developers := List(Developer("agaro1121", "Anthony Garo", "agaro1121@gmail.com", url("https://github.com/agaro1121"))),
    publishArtifact in Test := false,
    bintrayReleaseOnPublish := false,
    publishMavenStyle := true,
    bintrayRepository := organization.value,
    bintrayOrganization in bintray := None,
    organization in ThisBuild := "com.github.agaro1121"
  ) //++ versioningSettings //TODO: for some reason adding this stops snapshots from being published

}
