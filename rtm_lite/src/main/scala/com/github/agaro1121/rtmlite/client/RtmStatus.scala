package com.github.agaro1121.rtmlite.client

private[client] sealed trait RtmStatus extends Product with Serializable

private[client] object RtmStatus {

  final case object Success extends RtmStatus

  final case object Failure extends RtmStatus

}