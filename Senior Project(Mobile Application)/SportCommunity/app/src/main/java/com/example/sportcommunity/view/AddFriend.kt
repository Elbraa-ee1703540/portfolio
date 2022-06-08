package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.User
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.viewmodel.RequestViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AddFriend(onReload: () -> Unit
//              ,OnaddFriend: (User) -> Unit
){
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
//    var users : List<User> = listOf()
    var AllUsers = sportCommunityViewModel.users.observeAsState(listOf()).value
        .filter {  it != sportCommunityViewModel.currentUser }.filter { it.userType == "Normal" }
    Log.e("FFFFFFFFFFFF", sportCommunityViewModel.currentUser?.friends!!.toString())
    var users= AllUsers
    var emptyusers= AllUsers
    emptyusers = emptyList()
    for( c in sportCommunityViewModel.currentUser?.friends!!){
        users = AllUsers
            .filter { it.email != c }

    }


    var search = remember { mutableStateOf("") }




    Column {
        SearchBox(text = search,"user")
        LazyColumn {
            items(
                if (search.value.isEmpty())
                    emptyusers
                else {

                    users.filter {
                        var x = it.fname +" "+ it.lName
                        x.contains(search.value)
                    }
                }
            ) {
                userCard(it, { onReload() }
//                    ,{OnaddFriend(it)}
                )
            }
        }
    }
}

@Composable
fun userCard(user: User, onReload: () -> Unit
//             , OnaddFriend: (User) -> Unit
) {
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.cartoon)
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val requestViewModel =
        viewModel<RequestViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var buttonText = remember { mutableStateOf("Add") }
    var buttoncolor = remember { mutableStateOf(Color(ContextCompat.getColor(context,R.color.darkGreen))) }
    var buttonenabled =  remember { mutableStateOf(true) }

    Card(
        elevation = 14.dp, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .width(80.dp)
                    .height(80.dp)
            ) {

                Image(
                    painter = cartoonImage, contentDescription = "user",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
                )
            }


            Column(
                modifier = Modifier
                    .width(210.dp)
                    .height(150.dp)
            ) {
                Text(
                    text = "${user.fname} ${user.lName}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Blue,
                    fontSize = 18.sp
                )
                Text(
                    text = "${user.city}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Gray
                )
                Text(
                    text = "${user.favSports}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Gray
                )
            }
            Column(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
            ) {

                Button(

                    onClick = {
                        buttonenabled.value =false
                        buttonText.value = "Added"
                        buttoncolor.value = Color.Red
                        val req = sportCommunityViewModel.currentUser?.let {
                            it.email?.let { it1 ->
                                user.email?.let { it2 ->
                                    Request(
                                        sender = it1,
                                        receiver = it2,
                                        reqType = "addFriend",
                                        description = "${it.fname + " " + it.lName} need to add you as a friend",
                                        isApproved = false
                                    )
                                }
                            }
                        }

                        sportCommunityViewModel.request = req

                        sportCommunityViewModel.request?.let {
                            coroutineScope.launch {
                                requestViewModel.addRequests(it)
                            }
//                            it.receiver.requests.add(it)

//                            sportCommunityViewModel.UpdateotherUser(it.receiver)
                        }


//                        if (req != null) {
//                            if (req.isApproved == true) {
//                                user.email?.let {
//                                    sportCommunityViewModel.currentUser?.friends?.add(
//                                        it
//                                    )
//                                }
//                                sportCommunityViewModel.UpdateUser()
//                            }
//                        }

//                        onReload()
                    },
                    modifier = Modifier
                        .fillMaxWidth().padding(5.dp)
                        .width(100.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = buttoncolor.value),
                    border = BorderStroke(1.dp, Color(0xFFA500)),
                    shape = RoundedCornerShape(50)
                    , enabled = buttonenabled.value
                ) {

                    Text(text = "${buttonText.value}")
                }


            }
        }

    }
}

