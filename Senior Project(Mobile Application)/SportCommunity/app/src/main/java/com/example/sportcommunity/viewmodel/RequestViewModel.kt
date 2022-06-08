package com.example.sportcommunity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.*
import com.example.sportcommunity.reposotiry.RequestRepository
import com.example.sportcommunity.reposotiry.SportCommunityRepository
import com.example.sportcommunity.reposotiry.TeamRepository

class RequestViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val requestRepository = RequestRepository(appContext)

    //--------

    private var _requests = MutableLiveData<List<Request>>()
    var requests: LiveData<List<Request>> = _requests

    init {
        registerRequestlistener()
    }
    private fun registerRequestlistener() {
        requestRepository.RequestDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _requests.value = snappshot!!.toObjects(Request::class.java)
        }
    }

//    var requests = mutableListOf<Request>()

    suspend fun addRequests(request: Request) {
//        requests.add(request)
        addRequestToFDB(request)
    }
    // delete ,update

    suspend fun addRequestToFDB(request: Request){
        requestRepository.addRequestToFireBase(request)
    }
    suspend fun deleteRequests(request: Request) {
        requestRepository.deleteRequestToFireBase(request)
    }
    suspend fun updateRequests(request: Request) {
        requestRepository.updateRequestToFireBase(request)
    }
//---------



//    var requests = mutableListOf<Request>()
//
//    suspend fun addRequests(request: Request) {
//        requests.add(request)
//        addRequestToFDB(request)
//    }
//    // delete ,update
//    suspend fun deleteRequests(request: Request) {
//        requests.remove(request)
//        requestRepository.deleteRequestToFireBase(request)
//    }
//    suspend fun addRequestToFDB(request: Request){
//        requestRepository.addRequestToFireBase(request)
//    }


}