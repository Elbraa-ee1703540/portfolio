package com.example.sportcommunity.reposotiry

import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.security.SecureRandom
import android.widget.Toast
import androidx.core.net.toUri
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread


class SportCommunityRepositoryTest {

    lateinit var sportCommunityRepository: SportCommunityRepository

    @Before
    fun setup() {
        sportCommunityRepository =
            SportCommunityRepository(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun return_null_if_no_user_signedIn() = runBlocking {
        assertEquals(sportCommunityRepository.getCurrentUser(), null)
    }

    @Test
    fun return_false_if_emailUser_bad_formatted_in_signup() = runBlocking {
        val user = User(
            email = "",
            password = "asdasdasdasd"
        )

        sportCommunityRepository.registerNewUser(user)
        assertEquals(
            sportCommunityRepository.isSignedUp.value, false
        )
    }

    @Test
    fun return_false_if_password_tooShort_in_sign_up() = runBlocking {
        val user = User(
            email = "q@gmail.com",
            password = ""
        )
        sportCommunityRepository.registerNewUser(user)
        assertEquals(
            sportCommunityRepository.isSignedUp.value, false
        )
    }

    @Test
    fun return_false_if_emailUser_bad_formatted_in_signIn() = runBlocking {
        val user = User(
            email = "",
            password = "asdasdasdasd"
        )

        sportCommunityRepository.signIn(user.email ?: "", user.password)
        assertEquals(
            sportCommunityRepository.isSignedIn.value, false
        )
    }

    @Test
    fun return_false_if_password_tooShort_in_signIn() = runBlocking {
        val user = User(
            email = "q@gmail.com",
            password = ""
        )
        sportCommunityRepository.signIn(user.email ?: "", user.password)
        assertEquals(
            sportCommunityRepository.isSignedIn.value, false
        )
    }

    @Test
    fun return_url_of_image() = runBlocking {
        val uri = "https://img.etimg.com/thumb/msid-67468478,width-1200,height-900,imgsize-181877,overlay-etpanache/photo.jpg".toUri()
        val result = sportCommunityRepository.addVenueImage(uri)
        assertEquals(
           result!=null , true
        )
    }
}