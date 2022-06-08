package com.example.sportcommunity.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

data class User(
    var email:String? = "",
    @Exclude val password:String="",
    var fname:String? = "",
    var lName:String? = "",
    var city: String? = "",
    var favSports: MutableList<String> = mutableListOf(),
    var requests: MutableList<Request> = mutableListOf(),
    var friends: MutableList<String> = mutableListOf<String>(), // list of Emails
    var userType: String = ""
){
    fun toMap() = mapOf(
        "email" to email,
        "fname" to fname,
        "lname" to lName,
        "city" to city,
     //   "imageUrl" to imageUrl,
        "favSports" to favSports,
        "requests" to requests,
        "friends" to friends,
        "userType" to userType,
    )

}

