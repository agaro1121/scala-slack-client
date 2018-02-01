package com.github.agaro1121.rtm.errors

trait StartErrorMessage extends ErrorMessage

object StartErrorMessage {

  /* Team is being migrated between servers.
  See the team_migration_started event documentation for details. */
  case object MigrationInProgress extends StartErrorMessage {
    override def toString = "migration_in_progress"
  }

  def fromString(s: String): Option[StartErrorMessage] =
    s match {
    case "migration_in_progress" => Some(MigrationInProgress)
    case _ => None
  }

}