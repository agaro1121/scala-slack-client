package com.github.agaro1121.web.models

import com.github.agaro1121.core.models.User

sealed trait WebApiResponse
case class ListUsers(ok: Boolean, members: List[User]) extends WebApiResponse
