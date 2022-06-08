package com.example.sportcommunity.model

import kotlinx.serialization.Serializable

@Serializable
data class Venue(
    var venueId: String = "",
    val type: String? = "",
    val name: String? = "",
    val street: String? = "",
    val city: String? = "",
    val location: List<Double> = listOf(25.0, 52.0),
    val contactNumber: String? = "",
    val price: Double? = 0.0,
    val facilities: MutableList<String>? = mutableListOf(),
    var timeSlots: MutableMap<String, MutableList<Int>> = mutableMapOf<String, MutableList<Int>>(),
    val size: String? = "",
    var images: MutableList<String>? = mutableListOf(),
    val ownerUser: String = "",
    var views: Int = 0,
    var rating: Double = 5.0
)
