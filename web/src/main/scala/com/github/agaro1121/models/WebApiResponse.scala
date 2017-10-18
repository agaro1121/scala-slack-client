package com.github.agaro1121.models

sealed trait WebApiResponse
case class ListUsers(ok: Boolean, members: List[User]) extends WebApiResponse
