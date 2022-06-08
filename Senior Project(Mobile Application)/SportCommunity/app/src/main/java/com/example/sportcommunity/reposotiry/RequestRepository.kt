package com.example.sportcommunity.reposotiry

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.Team
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class RequestRepository(private val context: Context) {
    private var auth: FirebaseAuth = Firebase.auth
    val db by lazy { FirebaseFirestore.getInstance() }
    val RequestDocumentRef by lazy { db.collection("requests") }

//    val TeamMemberDocumentRef by lazy { db.collection("teamsmember") }

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    val isRequestAdded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    suspend fun addRequestToFireBase(request: Request) {

//        user.email?.let {
//            usersDocumentRef.document(it).set(user)
        if (!request.sender.isNullOrEmpty() && !request.receiver.isNullOrEmpty()) {
            try {
                RequestDocumentRef
                    .document(request.id).set(request)
                    .addOnSuccessListener { documentReference ->
                        Log.d("TAG", "DocumentSnapshot added with ID: $documentReference")
                        isRequestAdded.value = true

                    }
                    .addOnFailureListener { e ->
                        Log.w("TAG", "Error adding document", e)
                    }.await()
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                isRequestAdded.postValue(false)
            }
        } else {
            isRequestAdded.postValue(false)
        }
    }

    suspend fun deleteRequestToFireBase(request: Request): Boolean {
        return try {
            RequestDocumentRef.document(request.id).delete()
            RequestDocumentRef.document(request.id).update(request.toMap())
            true
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            false
        }
    }

    suspend fun updateRequestToFireBase(request: Request) {
        RequestDocumentRef.document(request.id).update(request.toMap())
    }
}

