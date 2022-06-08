package com.example.sportcommunity.model

import java.security.SecureRandom

data class Request(
    var id: String = SecureRandom().nextInt(1000).toString(),
    val sender: String = "",
    val receiver: String = "",
    val reqType: String = "",
    val description: String = "",
    var isApproved: Boolean = false
)
{
    fun toMap() = mapOf(
        "sender" to sender,
        "receiver" to receiver,
        "reqType" to reqType,
        "isApproved" to isApproved,
    )

}