package com.github.agaro1121.models


// TODO: Is this a common format ??? (error: String)
case class Error(ok: Boolean, error: /*ErrorMessage*/String) //TODO: Create marshaller for ErrorMessage