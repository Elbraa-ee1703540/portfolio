package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.components.InputField
import com.example.sportcommunity.components.showToast
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.viewmodel.DateWithTimeViewModel
import com.example.sportcommunity.viewmodel.EventViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CreateEvent(onBackArrowClicked: () -> Unit = {}, onaddEvent:(Event) -> Unit) {

    val eventName = remember { mutableStateOf("") }
    var eventLocation by remember { mutableStateOf("") }
    val eventDescription  = remember { mutableStateOf("") }
    var eventLocationExpandable by remember {
        mutableStateOf(false)
    }

    val eventViewModel =
        viewModel<EventViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val CITIES = listOf(
        "Doha",
        "Al Ghanim",
        "Al Ghuwariyah",
        "Al Khalifat",
        "Al Khor",
        "Al Wakrah",
        "Al Rayyan",
        "Ar Ru'ays",
        "As Salatah al Jadidah",
        "Dukhan",
        "Ras Laffan",
        "Industrial City",
        "Umm Bab",
        "Umm Salal Ali",
        "Umm Salal Mohammed"
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {

                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back",
                    tint =   MaterialTheme.colors.primarySurface,
                    modifier = Modifier.clickable { onBackArrowClicked.invoke() })

                Spacer(modifier = Modifier.width(40.dp))
                Text(
                    text = "Create Event",
                    color =   MaterialTheme.colors.primarySurface,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )

            },backgroundColor = Color.Transparent,elevation = 0.dp
        )


        Column(
            modifier = Modifier.height(350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InputField(
                valueState = eventName,
                labelId = "Event Name",
                enabled = true
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end=10.dp)
                    .clickable { eventLocationExpandable = !eventLocationExpandable }
                    .weight(1f)
            )
            {
                OutlinedTextField(
                    value = eventLocation,
                    onValueChange = {
                        eventLocation = it
                    },
                    label = { Text(text = "choose your eventLocation") },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "eventLocation"
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = eventLocationExpandable,
                    onDismissRequest = { eventLocationExpandable = false }) {
                    CITIES.forEach {
                        DropdownMenuItem(onClick = {
                            eventLocationExpandable = false
                            eventLocation = it
                        }) {
                            Text(text = it, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            OutlinedTextField(
                value = eventDescription.value,
                onValueChange = { eventDescription.value = it },
                label = { Text(text = "Event Description") },

                textStyle = TextStyle(
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground
                ),
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                    .height(200.dp)
                    .fillMaxWidth(),

                )
//
        }


        // date and time
        Spacer(modifier = Modifier.height(40.dp))

        val context = LocalContext.current
        val viewModel: DateWithTimeViewModel = viewModel()
        var dateTime = viewModel.time.observeAsState()

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextButton(
                    onClick = {
                        viewModel.selectDateTime(context)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))

                        .padding(5.dp),colors = ButtonDefaults.textButtonColors(
                        MaterialTheme.colors.primarySurface
                        )
                ) {
                    Text(text = "Select Date & Time", color = Color.White)
                }

                Spacer(modifier = Modifier.height(30.dp))



                val valid = remember(eventName.value, eventLocation, dateTime.value) {
                    eventName.value.trim().isNotEmpty()
                            && eventLocation.trim()
                        .isNotEmpty() && eventDescription.value.trim()
                        .isNotEmpty() && !dateTime.value.isNullOrEmpty()
                }
                Button(
                    modifier = Modifier
                        .width(180.dp)
                        .height(50.dp),
                    onClick = {
                        if (valid) {
                            val newEvent = Event(
                                eventName = eventName.value,
                                eventLocation = eventLocation,
                                eventDateTime = dateTime.value,description= eventDescription.value,
                                owner = sportcommunityViewModel.currentUser?.email!!
                            )
                            if (newEvent != null) {
//                                eventViewModel.addEvent(newEvent)
                                onaddEvent(newEvent)
                            }


                            showToast(context, "Event Created Successfully")

                            eventName.value = ""

                            eventLocation = ""
                            eventDescription.value=""
                            onBackArrowClicked.invoke()


                        } else {
                            showToast(context, "Fill all info please")

                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primarySurface,
                        //LightGray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Create",
                        style = MaterialTheme.typography.h6.copy(

                        )
                    )
                }

            }
        }
    }

}