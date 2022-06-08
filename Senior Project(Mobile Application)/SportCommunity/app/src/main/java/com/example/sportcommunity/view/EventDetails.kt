package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportcommunity.viewmodel.EventViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun EventDetails(
    navController: NavController,
) {
    val eventViewModel = viewModel<EventViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val context = LocalContext.current
 val event = eventViewModel.event


    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = "arrow back",  tint =   MaterialTheme.colors.primarySurface,

                            modifier = Modifier.clickable { navController.popBackStack() })

                        Spacer(modifier = Modifier.width(40.dp))

                        Text("Event Details",
                                color =   MaterialTheme.colors.primarySurface,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp
                        ))
                    }
                        },backgroundColor = Color.Transparent,elevation = 0.dp
            )

        }) {

        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.padding(34.dp),
                    shape = CircleShape, elevation = 4.dp
                ) {



                }

                Text(
                    text = event.eventName.toString(),fontSize = 29.sp,
                    style = MaterialTheme.typography.h6,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 19
                )

                Text(
                    text = "Event Location: ${event.eventLocation}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,
                    fontSize = 20.sp
                )
                var time = event.eventDateTime?.split("22")?.get(1)
                Text(
                    text = "Event Date:  ${event?.eventDateTime?.take(15)}",fontSize = 20.sp,
                    //fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "Event Time $time", overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,fontSize = 20.sp
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(15.dp))

                if(event.owner == sportcommunityViewModel.currentUser?.email!!){
                    var x = ""
                    if(event.members.isEmpty()){x = "No members yet" }else{x = "${event.members}"}
                    Text(

                        text = "Event Member: ${x}", overflow = TextOverflow.Clip,
                        fontStyle = FontStyle.Italic,
                        style = MaterialTheme.typography.caption,fontSize = 20.sp
                        // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(15.dp))
                }


                Text(
                    text = "Description: ", overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.caption,fontSize = 20.sp
                    // fontSize = 16.sp,modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Surface(
                    modifier = Modifier
                        .height(150.dp).fillMaxWidth()
                        .padding(4.dp),
                    shape = RectangleShape,
                    border = BorderStroke(1.dp, Color.DarkGray)
                ) {

                    LazyColumn(modifier = Modifier.padding(3.dp)) {
                        item {

                           Text(text = event.description.toString(), style = TextStyle(fontSize = 18.sp))
                        }

                    }
                }
                Spacer(modifier = Modifier.height(35.dp))


                Row(
                    modifier = Modifier.padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if(event.owner == sportcommunityViewModel.currentUser?.email!! || event.members.contains(sportcommunityViewModel.currentUser?.email!!)){
                        if(event.owner == sportcommunityViewModel.currentUser?.email!!){
                            Text(
                                text = "My Event",fontSize = 29.sp,
                                style = MaterialTheme.typography.h6,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 19
                            )
                        } else {
                            Text(
                                text = "Already join",fontSize = 29.sp,
                                style = MaterialTheme.typography.h6,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 19
                            )
                        }

                    }else {
                        RoundedButton(label = "Join") {
                            event.members.add(sportcommunityViewModel.currentUser?.email!!)
                            showToast(context, "You Joined Successfully")
                            navController.popBackStack()
                        }
                        Spacer(modifier = Modifier.width(85.dp))
                        RoundedButton(label = "Cancel") {
                            navController.popBackStack()
                        }
                    }



                }

            }
        }
    }
}
@Preview
@Composable
fun RoundedButton(
    label: String = "",
    radius: Int = 29,
    onPress: () -> Unit = {}) {
    Surface(modifier = Modifier.size(80.dp).clip(
        RoundedCornerShape(
        bottomEndPercent = radius,
        topStartPercent = radius)
    ),
        color =   MaterialTheme.colors.primarySurface
    ) {

        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = TextStyle(color = Color.White,
                fontSize = 22.sp),)

        }

    }


}
