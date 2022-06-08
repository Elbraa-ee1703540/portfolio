package com.example.sportcommunity.viewmodel


import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.*
import com.example.sportcommunity.reposotiry.SportCommunityRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SportCommunityViewModel constructor(appContext: Application) : AndroidViewModel(appContext) {

    var day = MutableLiveData<MutableList<Int>>(
        mutableStateListOf(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        )
    )
    private val sportCommunityRepo = SportCommunityRepository(appContext)

    private var _venues = MutableLiveData<List<Venue>>()
    var venues: LiveData<List<Venue>> = _venues

    private var _bookings = MutableLiveData<List<Booking>>()
    var bookings: LiveData<List<Booking>> = _bookings

    private var _users = MutableLiveData<List<User>>()
    var users: LiveData<List<User>> = _users
    var currentUser by mutableStateOf<User?>(null)
    var otherUser by mutableStateOf<User?>(null)
    var request by mutableStateOf<Request?>(null)
    var usersfrind by mutableStateOf<User?>(null)
//    var otherUser : User = TODO()
//    val auth: FirebaseAuth
//        get() {
//            TODO()
//        }
//    val db: FirebaseFirestore
//        get() {
//            TODO()
//        }
//    val storage: FirebaseStorage
//        get() {
//            TODO()
//        }


    //    val venues = mutableStateListOf<Venue>(*sportCommunityRepo.getVenues().toTypedArray())
//    lateinit var user:
    val isSignedIn: MutableLiveData<Boolean> = sportCommunityRepo.isSignedIn
    val isSignedUp: MutableLiveData<Boolean> = sportCommunityRepo.isSignedUp
    val isBookingAdded: MutableLiveData<Boolean> = sportCommunityRepo.isBookingAdded
    lateinit var showedVenueDetails: Venue

    lateinit var venueToBeAdded: Venue

    init {
        registerUserlistener()
        registerVenuelistener()
        registerBookinglistener()
    }

    suspend fun registerNewUser(
        email: String,
        password: String,
        fName: String,
        lName: String,
        city: String,
        favSports: MutableList<String>,
        requests: MutableList<Request>,
        friends: MutableList<String>,
        userType: String
    ) {
        val user = User(
            email,
            password,
            fName,
            lName,
            city,
            favSports,
            requests,
            friends,
            userType
        )
        sportCommunityRepo.registerNewUser(user)
        currentUser = user
    }

    suspend fun signIn(userName: String, password: String) {
        currentUser = null

        sportCommunityRepo.signIn(userName, password)
        currentUser = sportCommunityRepo.getUser(userName)
    }

    suspend fun addVenueToFDB(venue: Venue) {
        sportCommunityRepo.addVenueToFireBase(venue = venue)
    }

    suspend fun addBookingToFDB(booking: Booking) {
        sportCommunityRepo.addBookingToFireBase(booking = booking)
    }

    suspend fun addImageToFDB(uri: Uri) {
        Log.d("TAG", "addImageToFDB: $uri ")
        val imageUri = sportCommunityRepo.addVenueImage(uri)
        Log.d("TAG", "addImageToFDB: imageUri $imageUri ")
        venueToBeAdded.images?.add(imageUri.toString())
    }

    private fun registerVenuelistener() {
        sportCommunityRepo.venuesDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _venues.value = snappshot!!.toObjects(Venue::class.java)
        }
    }

    private fun registerUserlistener() {
        sportCommunityRepo.usersDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _users.value = snappshot!!.toObjects(User::class.java)
        }
    }

    private fun registerBookinglistener() {
        sportCommunityRepo.bookingDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _bookings.value = snappshot!!.toObjects(Booking::class.java)
        }
    }

    fun signOut() {
        Firebase.auth.signOut()
    }


    fun editProfile(user: User) {
        currentUser?.fname = user.fname
        currentUser?.lName = user.lName
        currentUser?.city = user.city
        currentUser?.favSports = user.favSports
        UpdateUser()
    }

    fun UpdateUser() {
        currentUser?.let { sportCommunityRepo.UpdateCurrentUser(it) }
    }

    fun UpdateotherUser(user: User) {
        user.let { sportCommunityRepo.UpdateCurrentUser(it) }
    }

    suspend fun getUser(email: String): User? {

        return sportCommunityRepo.getUser(email)

    }
}

