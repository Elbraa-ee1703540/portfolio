package com.example.sportcommunity.reposotiry

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.Booking
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.User
import com.example.sportcommunity.model.Venue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await

class SportCommunityRepository(private val context: Context) {
    var auth: FirebaseAuth = Firebase.auth
    val db by lazy { FirebaseFirestore.getInstance() }
    val venuesDocumentRef by lazy { db.collection("venues") }
    val usersDocumentRef by lazy { db.collection("users") }
    val bookingDocumentRef by lazy { db.collection("booking") }

    //    private val userCollectionRef by lazy {
//        Firebase.firestore.collection("users")
//    }
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    val isSignedIn: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val isSignedUp: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isVenueAdded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isBookingAdded: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val isVenueEdited: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val currentUser: MutableLiveData<String> by lazy {
        MutableLiveData("")
    }


//    private fun readData(filename: String) =
//        context.assets.open(filename).bufferedReader().use { it.readText() }

//    fun getVenues() =
//        Json.decodeFromString<List<Venue>>(readData("venues.json"))

    fun getCurrentUser(): FirebaseUser? {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            return currentUser
        }
        return null
    }

    fun UpdateUser(user: User) {

//        user.email?.let {
//            FirebaseFirestore.getInstance().collection("users")
//                .document(it).update(user.toMap())
//        }

//        user.email?.let { usersDocumentRef.document(it).update(user.toMap()) }
    }


    fun UpdateCurrentUser(user: User): FirebaseUser? {

        user.email?.let {
            usersDocumentRef.document(it).get().addOnSuccessListener {
                this.getCurrentUser()
                if (it.exists()) {
                    it.reference.update(user.toMap())
                        .addOnSuccessListener {

                        }
                }
            }
        }
        return null
    }

    suspend fun getVenues(): List<Venue> =
        venuesDocumentRef.get().await().toObjects(Venue::class.java)

    suspend fun registerNewUser(user: User) {
        try {
            auth.createUserWithEmailAndPassword(user.email ?: "", user.password)
                .addOnCompleteListener { task ->
                    isSignedUp.value = if (task.isSuccessful) {
                        Log.d("TAG", "createUserWithEmail:success")


                        user.email?.let {
                            usersDocumentRef.document(it).get().addOnSuccessListener {
                                this.getCurrentUser()
                                if (it.exists()) {
                                    it.reference.update(user.toMap())
                                        .addOnSuccessListener {

                                        }
                                }
                            }
                        }
                        // Add a new document with a generated ID
                        user.email?.let {
                            usersDocumentRef.document(it).set(user)

//                        userCollectionRef.document(it).set(user)
                                .addOnSuccessListener { documentReference ->
                                    Log.d(
                                        "TAG",
                                        "DocumentSnapshot added with ID: $documentReference"
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Log.w("TAG", "Error adding document", e)
                                }
                        }
                        true
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                        false
                    }
                }.await()
        } catch (e: Exception) {
            Log.e("TAG", "LoginButton Error: $e ")
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
            isSignedUp.postValue(false)
        }
    }

    suspend fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .continueWith { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        isSignedIn.value = true
                        currentUser.value = task.result.user?.email
                    } else {
                        Log.w("TAG", "signInWithEmail:failure", task.exception)
                        isSignedIn.value = false
                    }
                }.await()
        } catch (e: Exception) {
            Log.e("TAG", "LoginButton Error: $e ")
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    suspend fun getUser(email: String): User? {
        return usersDocumentRef.document(email).get().await().toObject(User::class.java)
    }


    fun signOut() {

    }

    suspend fun addVenueToFireBase(venue: Venue) {
        try {
            venuesDocumentRef.add(venue)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    venuesDocumentRef.document(documentReference.id)
                        .update("venueId", documentReference.id)
                    isVenueAdded.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }.await()
        } catch (e: Exception) {
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    suspend fun editVenueToFireBase(venue: Venue) {
        try {
            venuesDocumentRef.document(venue.venueId).set(venue)
                .addOnSuccessListener { documentReference ->
//                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference}")
                    isVenueEdited.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error editing document", e)
                }.await()
        } catch (e: Exception) {
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    suspend fun addVenueImage(uri: Uri): Uri {
        var imageUri = uri
// Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child("${imageUri}.jpg")
        val uploadTask = mountainsRef.putFile(imageUri)

        try {
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                mountainsRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.d("Tag", imageUri.toString())
                    if (downloadUri != null) {
                        imageUri = downloadUri
                    }
                } else {
                    Log.d("FAILED", imageUri.toString())
                }
            }.await()
        } catch (e: Exception) {
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
        Log.d("Tag", "imageUri: ${imageUri.toString()}")
        return imageUri
    }

    suspend fun addBookingToFireBase(booking: Booking) {
        try {
            bookingDocumentRef.add(booking)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                    bookingDocumentRef.document(documentReference.id)
                        .update("bookingId", documentReference.id)
                    isBookingAdded.value = true
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }.await()
        } catch (e: Exception) {
            Toast.makeText(
                context, "Error: ${e.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
