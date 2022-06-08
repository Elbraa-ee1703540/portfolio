package com.example.sportcommunity.reposotiry

import androidx.test.platform.app.InstrumentationRegistry
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TeamRepositoryTest {
    lateinit var teamRepository: TeamRepository

    @Before
    fun setup() {
        teamRepository =
            TeamRepository(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun return_true_when_team_Added_successfully() = runBlocking {
        teamRepository.addTeamToFireBase(
            Team(
                TeamName = "Test",
                Users = mutableListOf(),
                description = "test",
                capacity = "5",
                leader = User("m.gazalh@gmail.com")
            )
        )
        assertEquals(teamRepository.isTeamAdded.value, true)
    }
}