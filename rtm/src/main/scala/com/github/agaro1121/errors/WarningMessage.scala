package com.github.agaro1121.errors

trait WarningMessage

object WarningMessage {

  /* The method was called via a POST request,
  and recommended practice for the specified
  Content-Type is to include a charset parameter.
  However, no charset was present. Specifically,
  non-form-data content types (e.g. text/plain)
  are the ones for which charset is recommended*/
  case object MissingCharset extends WarningMessage {
    override def toString = "missing_charset"
  }

  /* The method was called via a POST request,
  and the specified Content-Type is not defined
  to understand the charset parameter. However,
  charset was in fact present. Specifically,
  form-data content types (e.g. multipart/form-data)
  are the ones for which charset is superfluous*/
  case object SuperfluousCharset extends WarningMessage {
    override def toString = "superfluous_charset"
  }

  def fromString(s: String): Option[WarningMessage] = {
    s match {
      case "missing_charset" => Some(MissingCharset)
      case "superfluous_charset" => Some(SuperfluousCharset)
      case _ => None
    }
  }

}
