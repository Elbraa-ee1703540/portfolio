package com.example.sportcommunity.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.User
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.reposotiry.SportCommunityRepository
import com.example.sportcommunity.reposotiry.TeamRepository

class TeamViewModel(appContext: Application) : AndroidViewModel(appContext) {
    private val teamRepository = TeamRepository(appContext)

    private var _teams = MutableLiveData<List<Team>>()
    var teams: LiveData<List<Team>> = _teams

    init {
        registerTeamlistener()
    }

    private fun registerTeamlistener() {
        teamRepository.TeamDocumentRef.addSnapshotListener { snappshot, e ->
            if (e != null) return@addSnapshotListener
            _teams.value = snappshot!!.toObjects(Team::class.java)
        }
    }

//    var teams = mutableListOf<Team>()

    suspend fun addTeams(team: Team) {
//        teams.add(team)
        addTeamToFDB(team)
    }
    // delete ,update

    suspend fun addTeamToFDB(team: Team){
        teamRepository.addTeamToFireBase(team)
    }
}