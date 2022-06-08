package com.example.sportcommunity.reposotiry

import androidx.test.platform.app.InstrumentationRegistry
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.User
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.security.SecureRandom


class RequestRepositoryTest {

    lateinit var requestRepository: RequestRepository

    @Before
    fun setup() {
        requestRepository = RequestRepository(InstrumentationRegistry.getInstrumentation().context)
    }

    @Test
    fun return_false_if_sender_or_receiver_empty() = runBlocking {
        requestRepository.addRequestToFireBase(
            Request(
                id = SecureRandom().nextInt(1000).toString(),
                sender = "",
                receiver = "",
                reqType = "",
                description = "",
                isApproved = false
            )
        )

        assertEquals(requestRepository.isRequestAdded.value, false)
    }

    @Test
    fun return_true_when_Deleting_request() = runBlocking {

        val request = Request(
            id = "987",
            sender = "a@gmail.com",
            receiver = "ma@gmail.com",
            reqType = "add friend",
            description = "a sent you a friend request",
            isApproved = false
        )

        requestRepository.addRequestToFireBase(
            request
        )
        assertEquals(requestRepository.deleteRequestToFireBase(request), true)
    }

    @Test
    fun return_true_when_updating_status_of_request() = runBlocking {
        var request = Request(
            id = "asasasd10000",
            sender = "a@gmail.com",
            receiver = "ma@gmail.com",
            reqType = "add friend",
            description = "a sent you a friend request",
            isApproved = false
        )
        requestRepository.addRequestToFireBase(
            request
        )
        request.isApproved = true
        requestRepository.updateRequestToFireBase(
            request
        )
        var doc = requestRepository.RequestDocumentRef.document(request.id).get().await()
        doc.toObject(Request::class.java)!!

        var isApproved = request.isApproved
        assertEquals(isApproved, true)
    }


}