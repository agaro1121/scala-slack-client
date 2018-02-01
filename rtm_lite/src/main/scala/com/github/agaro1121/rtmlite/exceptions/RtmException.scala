package com.github.agaro1121.rtmlite.exceptions

sealed trait RtmException
case object BadWebsocketResponse extends RtmException