package com.example.sportcommunity.model

data class Team(
    var TeamName: String? = "",
    var Users: MutableList<String> = mutableListOf<String>(),
    var description: String? = "",
    var capacity: String = "", // num of Team members
    var leader : String? = ""
)