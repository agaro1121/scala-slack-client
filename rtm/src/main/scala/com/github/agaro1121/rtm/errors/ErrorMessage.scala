package com.github.agaro1121.rtm.errors

trait ErrorMessage //TODO better name?
object ErrorMessage {

  /* No authentication token provided. */
  case object NotAuthed extends ErrorMessage {
    override def toString = "not_authed"
  }

  /* Invalid authentication token. */
  case object InvalidAuth extends ErrorMessage {
    override def toString = "invalid_auth"
  }

  /* Authentication token is for a deleted user or team. */
  case object AccountInactive extends ErrorMessage {
    override def toString = "account_inactive"
  }

  /* The method was passed an argument whose name
  falls outside the bounds of common decency.
  This includes very long names and names with
  non-alphanumeric characters other than _.
  If you get this error, it is typically an
  indication that you have made a very malformed API call*/
  case object InvalidArgName extends ErrorMessage {
    override def toString = "invalid_arg_name"
  }

  /* The method was passed a PHP-style array
  argument (e.g. with a name like foo[7]).
  These are never valid with the Slack API.*/
  case object InvalidArrayArg extends ErrorMessage {
    override def toString = "invalid_array_arg"
  }

  /* The method was called via a POST request,
  but the charset specified in the Content-Type
  header was invalid. Valid charset names are:
  utf-8 iso-8859-1 */
  case object InvalidCharset extends ErrorMessage {
    override def toString = "invalid_charset"
  }

  /* The method was called via a POST request
  with Content-Type application/x-www-form-urlencoded
  or multipart/form-data, but the form data
  was either missing or syntactically invalid*/
  case object InvalidFormData extends ErrorMessage {
    override def toString = "invalid_form_data"
  }

  /* The method was called via a POST request,
  but the specified Content-Type was invalid.
  Valid types are:
  - application/x-www-form-urlencoded
  - multipart/form-data text/plain. */
  case object InvalidPostType extends ErrorMessage {
    override def toString = "invalid_post_type"
  }

  /* The method was called via a POST request
  and included a data payload, but the request
  did not include a Content-Type header*/
  case object MissingPostType extends ErrorMessage {
    override def toString = "missing_post_type"
  }

  /* The team associated with your request
  is currently undergoing migration to an
  Enterprise Organization. Web API and other
  platform operations will be intermittently
  unavailable until the transition is complete*/
  case object TeamAddedToOrg extends ErrorMessage {
    override def toString = "team_added_to_org"
  }

  /* The method was called via a POST request,
  but the POST data was either missing or truncated.*/
  case object RequestTimeout extends ErrorMessage {
    override def toString = "request_timeout"
  }

  def fromString(s: String): Option[ErrorMessage] =
    s match {
      case "not_authed" => Some(NotAuthed)
      case "invalid_auth" => Some(InvalidAuth)
      case "account_inactive" => Some(AccountInactive)
      case "invalid_arg_name" => Some(InvalidArgName)
      case "invalid_array_arg" => Some(InvalidArrayArg)
      case "invalid_charset" => Some(InvalidCharset)
      case "invalid_form_data" => Some(InvalidFormData)
      case "invalid_post_type" => Some(InvalidPostType)
      case "missing_post_type" => Some(MissingPostType)
      case "team_added_to_org" => Some(TeamAddedToOrg)
      case "request_timeout" => Some(RequestTimeout)
      case _ => None
    }

}