package com.example.sportcommunity.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.User
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.reposotiry.SportCommunityRepository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class VenueOwnerViewModel constructor(appContext: Application) : AndroidViewModel(appContext) {
    private val sportCommunityRepo = SportCommunityRepository(appContext)

    private var _ownedVenues = MutableLiveData<List<Venue>>()
    var venues: LiveData<List<Venue>> = _ownedVenues

    val ownerEmail: MutableLiveData<String> = sportCommunityRepo.currentUser

    lateinit var venueToBeEdit: Venue

    val isVenueEdited: MutableLiveData<Boolean> = sportCommunityRepo.isVenueEdited

    init {
        registerVenueListener()
    }

    suspend fun addImageToFDB(uri: Uri) {
        Log.d("TAG", "addImageToFDB: $uri ")
        val imageUri = sportCommunityRepo.addVenueImage(uri)
        Log.d("TAG", "addImageToFDB: imageUri $imageUri ")
        venueToBeEdit.images?.add(imageUri.toString())
    }

    suspend fun editedVenueToFDB(venue: Venue) {
        sportCommunityRepo.editVenueToFireBase(venue = venue)
    }

    private fun registerVenueListener() {
        sportCommunityRepo.venuesDocumentRef.whereEqualTo("ownerUser", Firebase.auth.currentUser?.email)
            .addSnapshotListener { snappshot, e ->
                if (e != null) return@addSnapshotListener
                _ownedVenues.value = snappshot!!.toObjects(Venue::class.java)
            }
    }
}
