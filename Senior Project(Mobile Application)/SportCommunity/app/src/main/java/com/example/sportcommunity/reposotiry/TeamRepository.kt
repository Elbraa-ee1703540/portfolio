package com.example.sportcommunity.reposotiry

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.Venue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class TeamRepository(private val context: Context) {
    private var auth: FirebaseAuth = Firebase.auth
    val db by lazy { FirebaseFirestore.getInstance() }
    val TeamDocumentRef by lazy { db.collection("teams") }
    val TeamMemberDocumentRef by lazy { db.collection("teamsmember") }

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    val isTeamAdded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    suspend fun addTeamToFireBase(team: Team) {
        try {
            TeamDocumentRef.add(team)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    isTeamAdded.value = true

                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }.await()
        } catch (e: Exception) {
            isTeamAdded.postValue(false)
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}