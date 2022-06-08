package com.example.sportcommunity.view

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
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
import androidx.navigation.NavController
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.viewmodel.EventViewModel

@SuppressLint("UnrememberedMutableState")

@Composable
fun EventList(navController: NavController,onAddEvent: () -> Unit = {},) {

    val eventViewModel =
        viewModel<EventViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current
    var isCreated by remember {
        mutableStateOf(false)
    }
    var isShowed by remember {
        mutableStateOf(false)
    }
    var events = eventViewModel.events.observeAsState(listOf()).value

    Surface() {

        var showMenu by remember { mutableStateOf(false) }
        val context = LocalContext.current



        Scaffold(
//            topBar = {
//
//            var events = listOf<String>("", "")
//            TopAppBar(
//                title = { Text("Event") },
//                actions = {
//
//
//
////                    DropdownMenu(
////                        expanded = showMenu,
////                        onDismissRequest = { showMenu = false }
////                    ) {
////                        events.forEach {
////
////
////
////                        DropdownMenuItem(onClick = {
////                            showMenu = false
////                            if (it == "Show Event") {
//////                                isShowed= !isShowed
//////                                isCreated= false
////                            }
////                            else{
//////                                isShowed=false
//////                                isCreated = !isCreated
////                            }
////                        }) {
////                            Text(text = it)
////                        }
////                    }
////
////
////                    }
//
//
//                }
//            )
//
//        },
            floatingActionButton = {
                ExtendedFloatingActionButton(modifier = Modifier.height(40.dp), text = {
                    Text(text = "Create Event")
                }, onClick = { onAddEvent() })

            }) {

            Surface(modifier = Modifier.fillMaxSize()) {


                Column() {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(12.dp)
                    ) {


                    }




                    if (events.isNotEmpty()) {
                        var search = remember { mutableStateOf("") }

                        Column() {

                            SearchBox(text = search,"event name")

                            LazyColumn {
                                items(
                                    if (search.value.isEmpty())

                                        events
                                else{
                                        events.filter {
                                            it.eventName!!.contains( search.value)// search by event name
                                        }
                                    }

                                ) {
                                    EventCard(it,navController ){
                                        eventViewModel.event = it

                                    }

                                }
                            }


                        }
                    } else {
                   //     showToast(context, "No event found")

                    }
                }


            }

        }
    }
}




@Composable
fun EventCard(
    event: Event, navController: NavController, onEventClicked: (Event) -> Unit,
) {
    Card(modifier = Modifier
        .clickable {
          onEventClicked(event)
            navController.navigate(Screen.EventDetails.route)
        }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top) {


            Column {


                Text(text = "Event Name: ${event.eventName},", overflow = TextOverflow.Ellipsis,
                    // fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(text = "Event Location: ${event.eventLocation}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                var time = event.eventDateTime?.split("22")?.get(1)
                Text(text = "Event Date:  ${event?.eventDateTime?.take(15)}",
                    //fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
                Text(text = "Event Time $time",   overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )





            }

        }

    }

}
