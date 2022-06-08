package com.example.sportcommunity.view

import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.HandlerCompat.postDelayed
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.viewmodel.DateWithTimeViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

@Composable
fun AddDaysAndHours(OnAddClicked: (Venue) -> Unit, OnCancelClicked: () -> Unit) {
    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    val dateWithTimeViewModel: DateWithTimeViewModel = viewModel()
    var date = dateWithTimeViewModel.date.observeAsState()

    val venue = venueOwnerViewModel.venueToBeEdit
    var isComplete by remember { mutableStateOf(false) }
    val defaultArr =
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    var addedDayArr by remember { mutableStateOf(defaultArr) }
    var visible by remember { mutableStateOf(false) }

    var times = listOf(
        "00:00 - 01:00",
        "01:00 - 02:00",
        "02:00 - 03:00",
        "03:00 - 04:00",
        "04:00 - 05:00",
        "05:00 - 06:00",
        "06:00 - 07:00",
        "07:00 - 08:00",
        "08:00 - 09:00",
        "09:00 - 10:00",
        "10:00 - 11:00",
        "11:00 - 12:00",
        "12:00 - 13:00",
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00",
        "18:00 - 19:00",
        "19:00 - 20:00",
        "20:00 - 21:00",
        "21:00 - 22:00",
        "22:00 - 23:00",
        "23:00 - 24:00",
    )

    var selectedDayArr = remember {
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
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .weight(4f)
        ) {
            Button(
                onClick = {
                    visible = true
                    dateWithTimeViewModel.selectDate(context)
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(5.dp), colors = ButtonDefaults.textButtonColors(
                    MaterialTheme.colors.primarySurface
                )
            ) {
                Text(text = "Select Date ${date.value}", color = Color.White)
                visible = if (venue.timeSlots.keys.contains(date.value)) {
                    Log.d(
                        "TAG",
                        "AddDaysAndHours: Inside above "
                    )
                    Toast.makeText(
                        context,
                        "${date.value} has been added previously, You can't add it again",
                        Toast.LENGTH_LONG
                    ).show()
                    false
                } else {
                    true
                }

            }
        }
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxWidth()
        ) {

            Text(text = "Choose hour:")
            LazyRow(
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp)
            ) {
                items(times) {
                    TimeSlotToBeAdded(
                        it,
                        visible,
                        selectedDayArr[times.indexOf(it)],
                        onChooseTime = { clock ->
                            var index = times.indexOf(clock)
                            if (addedDayArr[index] == 1 && selectedDayArr[index] == 1) {
                                addedDayArr[index] = 0
                                selectedDayArr[index] = 0
                            } else {
                                addedDayArr[index] = 1
                                selectedDayArr[index] = 1
                            }
                            isComplete = true
                        })
                }
            }
        }
        Row() {
            Button(
                onClick = {
                    if (!date.value.isNullOrEmpty()) {
                        venue.timeSlots[date.value!!] = addedDayArr
                        OnAddClicked(venue)
                    }
                }, enabled = isComplete, modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Add")
            }

            Button(
                onClick = {
                    OnCancelClicked()
                }, modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Cancel")
            }
        }
    }
}


@Composable
fun TimeSlotToBeAdded(
    clock: String,
    isVisible: Boolean,
    availability: Int,
    onChooseTime: (String) -> Unit
) {
    if (isVisible) {
        Card(
            elevation = 10.dp,
            modifier = Modifier
                .padding(10.dp),
        ) {
            Text(
                text = clock, style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                ), modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        when (availability) {
                            -1 -> Color.Red
                            1 -> Color.Green
                            else -> Color.Gray
                            // 1 available, -1 Booked, else unavailable
                        }
                    )
                    .clip(RoundedCornerShape(15.dp))
                    .padding(10.dp)
                    .clickable {
                        onChooseTime(clock)
                    }
            )
        }
    }
}