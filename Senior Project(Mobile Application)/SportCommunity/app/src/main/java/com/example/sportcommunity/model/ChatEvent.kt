package com.example.sportcommunity.model

data class ChatEvent (
    val chatId:String? = null,
    // i need eventid to witch this  ChatEvent attach to
    val eventID:String? = null,
    // i will need the username for the user that create the chat
    val username:String? = null,
    // i will ned text  for chat says
    val text:String? = null,

    val timestamp:Long? = null,
        )