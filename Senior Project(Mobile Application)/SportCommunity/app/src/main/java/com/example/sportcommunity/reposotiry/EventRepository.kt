package com.example.sportcommunity.reposotiry

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.Venue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class EventRepository(private val context: Context) {
    private var auth: FirebaseAuth = Firebase.auth
    val db by lazy { FirebaseFirestore.getInstance() }
    val EventDocumentRef by lazy { db.collection("events") }


    private val storage = Firebase.storage
    private val storageRef = storage.reference

    val isEventAdded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    suspend fun addEventToFireBase(event: Event) {
        EventDocumentRef.add(event)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                isEventAdded.value = true

            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }.await()
    }

}