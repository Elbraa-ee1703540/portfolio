package com.example.sportcommunity.view

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.viewmodel.DateWithTimeViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import java.lang.Exception
import java.util.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun SelectTime(onClickCompletePayment: (String, String, Venue, Int) -> Unit) {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
    val dateWithTimeViewModel: DateWithTimeViewModel = viewModel()
    var date = dateWithTimeViewModel.date.observeAsState()
    val venue = sportCommunityViewModel.showedVenueDetails

    var visible by remember { mutableStateOf(false) }
    var isComplete by remember { mutableStateOf(false) }
    var time by remember { mutableStateOf("") }
    var lastClickedIndex by remember { mutableStateOf(0) }

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
    val defaultDayList = remember {
        mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    }

    var index by remember { mutableStateOf(0) }

    val defaultArr =
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    var addedDayArr by remember { mutableStateOf(defaultArr) }

//    var selectedDayArr = sportCommunityViewModel.day.observeAsState(mutableListOf()).value

    var selectedDayArr = remember {
        mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
    }
//    sportCommunityViewModel.day.value = venue.timeSlots[date.value]
//    if (selectedDayArr.isNullOrEmpty() || selectedDayArr.size == 0) {
//        selectedDayArr = defaultDayList
//    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            modifier = Modifier
                .weight(4f)
        ) {

            TextButton(
                onClick = {
                    dateWithTimeViewModel.selectDate(context)
                    visible = true
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .padding(5.dp), colors = ButtonDefaults.textButtonColors(
                    MaterialTheme.colors.primarySurface
                )
            ) {
                Text(text = "Select Date ${date.value}", color = Color.White)
                selectedDayArr.swapList(venue.timeSlots[date.value] ?: defaultArr)
                addedDayArr = venue.timeSlots[date.value] ?: defaultArr
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
                    TimeSlot(
                        it,
                        visible,
                        selectedDayArr[times.indexOf(it)],
                        onChooseTime = { availability, clock ->
                            index = times.indexOf(clock)
                            when (availability) {
                                -1 -> Toast.makeText(context, "Booked slot!", Toast.LENGTH_SHORT)
                                    .show()
                                1 -> {
                                    if (addedDayArr[index] == 1 && selectedDayArr[index] == 1) {
                                        selectedDayArr[index] = 2
                                        addedDayArr[index] = 2
                                        if (lastClickedIndex != index) {
                                            selectedDayArr[lastClickedIndex] = 1
                                            addedDayArr[lastClickedIndex] = 1
                                        }
                                        lastClickedIndex = index
                                    }
                                    time = it
                                    isComplete = true
                                }
                                2 -> {
                                    selectedDayArr[index] = 1
                                    addedDayArr[index] = 1
                                    isComplete = false
                                }
                                else -> Toast.makeText(
                                    context,
                                    "unavailable slot!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // 1 available, -1 Booked, else unavailable
                            }
                            Log.d(
                                "TAG",
                                "TimeSlot: in if 2222 $addedDayArr"
                            )
                        })
                }
            }
        }

        Button(
            onClick = {
                if (!date.value.isNullOrEmpty() && time != "") {
                    try {
                        addedDayArr[index] = -1
                        venue.timeSlots[date.value!!] = addedDayArr
                        onClickCompletePayment(time, date.value!!, venue, index)

                    } catch (e: Exception) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }, enabled = isComplete, modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Complete Booking")
        }
    }
}

@Composable
fun TimeSlot(
    clock: String,
    isVisible: Boolean,
    availability: Int,
    onChooseTime: (Int, String) -> Unit
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
                            2 -> Color.Yellow
                            else -> Color.Gray
                            // 1 available, -1 Booked, 2 Chosen, else unavailable
                        }
                    )
                    .clip(RoundedCornerShape(15.dp))
                    .padding(10.dp)
                    .clickable {
                        onChooseTime(availability, clock)
                    }
            )
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}