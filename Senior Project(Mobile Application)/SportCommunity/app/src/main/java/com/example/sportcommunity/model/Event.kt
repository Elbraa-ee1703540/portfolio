package com.example.sportcommunity.model

data class Event(
    val eventId: String? = "",
    val eventName: String? = "",
    val eventLocation: String? = "",
//    val eventLocation: List<Double> = listOf(25.0, 52.0),
    val eventDateTime: String? = "",
    var description: String? = "",
    var capacity: Int = 0, // capacity of events (limited no of users
    var ageRange: String = "", // range of ages that can join specific event
    var owner: String = "",
    var members: MutableList<String> = mutableListOf<String>()
)