package com.example.sportcommunity.viewmodel

import android.app.Application
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.model.ChatEvent
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.reposotiry.EventRepository
import com.example.sportcommunity.reposotiry.TeamRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*

class EventViewModel (appContext: Application) : AndroidViewModel(appContext) {

    private val eventRepository = EventRepository(appContext)

    private var _events = MutableLiveData<List<Event>>()
    var events: LiveData<List<Event>> = _events

    init {
        registerEventlistener()
    }

    private fun registerEventlistener() {
        eventRepository.EventDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _events.value = snappshot!!.toObjects(Event::class.java)
        }
    }

//    var events = mutableListOf<Event>()

    suspend fun addEvent(event: Event) {
//        events.add(event)
        eventRepository.addEventToFireBase(event)
    }
    // delete ,update





//  var events = mutableListOf<Event>(
//    //  Event(eventName = "Ramadan football  competition",eventDateTime = "20/4  9:00",eventLocation = "")
//  )
lateinit var event: Event
////    fun addEvent(event: Event) = events.add(event)
//        // delete ,update
//        val comments = mutableStateOf<List<ChatEvent>>(listOf())
//
//    val auth: FirebaseAuth =  Firebase.auth
//    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
//    val storage: FirebaseStorage =  Firebase.storage




}