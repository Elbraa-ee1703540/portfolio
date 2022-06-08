package com.example.sportcommunity.view

import  androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.Booking
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.viewmodel.EventViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import android.util.Log
import androidx.test.internal.util.LogUtil
import kotlin.math.log

@Composable
fun MyBooking() {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    var bookings = sportCommunityViewModel.bookings.observeAsState(listOf()).value

    Column() {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(12.dp)
        ) {


        }




        if (bookings.isNotEmpty()) {
            Column() {

                LazyColumn {

                    items(
                        if (sportCommunityViewModel.currentUser?.userType ?: "" == "Normal") {
                            Log.d(
                                "TAG",
                                "TimeSlot: in if 1111 ${bookings.filter { it.userId == sportCommunityViewModel.currentUser?.email }}"
                            )
                            Log.d("TAG", "TimeSlot: in if 1111 ${bookings}")
                            Log.d("TAG", "TimeSlot: in if 1111 ${sportCommunityViewModel.currentUser?.email}")
                            bookings.filter { it.userId == sportCommunityViewModel.currentUser?.email }
                        } else {
                            Log.d("TAG", "TimeSlot: in if 2222 $bookings")
                            var venuesIds =
                                sportCommunityViewModel.venues.value?.filter { it.ownerUser == sportCommunityViewModel.currentUser?.email }
                                    ?.map { it.venueId }
                            bookings = bookings.filter { venuesIds?.contains(it.venueId) ?: false }
                            bookings
                        }) {
                        BookingCard(it) {

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookingCard(
    booking: Booking, onEventClicked: (Booking) -> Unit,
) {
    Card(modifier = Modifier
        .clickable {
            onEventClicked(booking)
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {


            Column {


                Text(
                    text = "booking Id: ${booking.bookingId},", overflow = TextOverflow.Ellipsis,
                    // fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Venue ID: ${booking.venueId}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Booking Date:  ${booking.date}",
                    //fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Booking Time ${booking.hour}", overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

        }

    }
}