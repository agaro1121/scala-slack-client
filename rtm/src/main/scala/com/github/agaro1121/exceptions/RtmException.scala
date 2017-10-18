package com.github.agaro1121.exceptions

sealed trait RtmException
case object BadWebsocketResponse extends RtmException