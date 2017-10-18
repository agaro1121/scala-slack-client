package com.github.agaro1121.client

sealed trait RtmStatus extends Product with Serializable

object RtmStatus {

  final case object Success extends RtmStatus

  final case object Failure extends RtmStatus

}