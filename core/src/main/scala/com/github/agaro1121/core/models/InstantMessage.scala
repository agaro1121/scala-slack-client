package com.github.agaro1121.core.models

case class InstantMessage(
  id: String,
  is_im: Boolean,
  user: String,
  created: Double,
  is_user_deleted: Boolean
)

//TODO: which is it?!?!?!?
// Below from RTM.start Response

/*
  {
      "id": "D57S094JG",
      "created": 1493773047,
      "is_im": true,
      "is_org_shared": false,
      "user": "USLACKBOT",
      "has_pins": false,
      "last_read": "0000000000.000000",
      "latest": null,
      "unread_count": 0,
      "unread_count_display": 0,
      "is_open": true
    },
* */