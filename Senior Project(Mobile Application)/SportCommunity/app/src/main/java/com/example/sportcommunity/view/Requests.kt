package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.viewmodel.RequestViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Requests(onReload:() -> Unit) {
    Log.d("TAG", "Requests: Helllllllllo")
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val requestViewModel =
        viewModel<RequestViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

//    var re = Request("add", "AddFrind",false)
//    sportcommunityViewModel.currentUser?.requests?.add(re)
//    sportcommunityViewModel.currentUser?.let { c ->

//     =  requestViewModel.requests
//    Log.d("TAG", "Requests: ${myRequests?.value?.size} ")

    var myRequests = requestViewModel.requests.value?.filter { !it.isApproved }?.filter { req ->
        req.receiver == sportcommunityViewModel.currentUser?.email
    }
//            as MutableList<Request>

//        if (myRequests != null) {
//            myRequests.forEach {
//
//                Log.d("TAG", "Requests: $myRequests")
//            }
//        }

    Column {
//                Friends.add(x)
        if (myRequests.isNullOrEmpty()) {
            Log.d("TAG", "Requests: Helllllllllo11")

            Text(
                text = "No Requests Found",
                modifier = Modifier.padding(end=90.dp, top= 40.dp),
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace
            )
        } else {
            Log.d("TAG", "Requests: Helllllllllo22")

            LazyColumn {
                items(myRequests) { r ->
                    RequestCard(r,{onReload()})
                }
            }
//            }
        }
    }
}

@Composable
fun RequestCard(request: Request,onReload:() -> Unit) {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.noti)

    val requestViewModel =
        viewModel<RequestViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var AllUsers = sportCommunityViewModel.users.observeAsState(listOf()).value
    var senderUser= AllUsers.find { it.email == request.sender }

    Card(
        elevation = 14.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
//        .clickable { onUserProfile(user) }
    )


    {
        Row {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .width(80.dp)
                    .height(80.dp)
            ) {

                Image(
                    painter = cartoonImage, contentDescription = "notification",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.White, shape = CircleShape)
                )
            }


            Column(
                modifier = Modifier
                    .width(170.dp)
                    .height(150.dp)
            ) {
                Text(
                    text = "${request.reqType}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Blue,
                    fontSize = 18.sp
                )
                Text(
                    text = "${request.description}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Gray
                )
            }
            Column(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight(), Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
//                        sportCommunityViewModel.currentUser?.friends?.remove(user)
//                        sportCommunityViewModel.UpdateUser()
//                        onReload()
                        request.isApproved = true
                        coroutineScope.launch {
                            requestViewModel.addRequests(request)
                        }
                        sportCommunityViewModel.currentUser?.friends?.add(request.sender)
                        sportCommunityViewModel.UpdateUser()
                        senderUser?.friends?.add(request.receiver)
                        Log.e("Eeee456456456", sportCommunityViewModel.currentUser?.friends!!.toString())
                        if (senderUser != null) {
                            sportCommunityViewModel.UpdateotherUser(senderUser)
                        }
                        coroutineScope.launch {
                            requestViewModel.deleteRequests(request)
                        }
                        onReload()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .size(50.dp),
                    border = BorderStroke(1.dp, Color(0xFFA500)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Approve ")
                }

                Button(
                    onClick = {
//                        sportCommunityViewModel.currentUser?.friends?.remove(user)
//                        sportCommunityViewModel.UpdateUser()
//                        onReload()
                        coroutineScope.launch {
                            requestViewModel.deleteRequests(request)
                        }
                        onReload()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .size(50.dp),
                    border = BorderStroke(1.dp, Color(0xFFA500)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "Reject")
                }

            }
        }

    }
}