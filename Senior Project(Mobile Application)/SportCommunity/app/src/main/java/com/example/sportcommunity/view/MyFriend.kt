package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.User
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

//@ExperimentalMaterialApi
@Composable
fun MyFriend(onAdd: () -> Unit,onReload: () -> Unit,onUserProfile: (User) -> Unit
){


    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var users = sportCommunityViewModel.users.observeAsState(listOf()).value
    var Friends: MutableList<User> = mutableListOf()
    Log.e("FFFFFFFFFFFF", sportCommunityViewModel.currentUser?.friends!!.toString())
//    for( u in users){
//        var x = sportCommunityViewModel.currentUser?.friends!!.find { f ->
//            u.email == f
//        }
//        Friends.add(x)
//
//    }
    for( f in sportCommunityViewModel.currentUser?.friends!!){
        var x = users.find { u ->
            u.email == f
        }
        Log.e("Eeee456456456", x.toString())
        if (x != null) {
            Friends.add(x)
        }

    }
    Log.e("Eeee456456456", Friends.toString())
//     = sportCommunityViewModel.currentUser?.friends

    var search = remember { mutableStateOf("") }
    val x = User("email", "password", "fName", "lName", "city")


    Scaffold(
        topBar = {
            SearchBox(text = search,"friend")

        },
        floatingActionButton = {
            FloatingActionButton(

                onClick = { onAdd() },
                backgroundColor = Color.LightGray,
                contentColor = Color.White,
                modifier = Modifier
                    .size(width = 100.dp, height = 100.dp)
                    .padding(20.dp)
                ,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center

    ){



        Column(
        ) {
            Column {
//                Friends.add(x)
                if (Friends != null) {
                    if (Friends.isEmpty()) {
                        Text(text = "You Do not have Friends yet",
                            modifier = Modifier.padding(end=40.dp, top= 40.dp),
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    } else{
                        LazyColumn {
                            items(
                                if (search.value.isEmpty())
                                    Friends
                                else {

                                    Friends.filter {
                                        var x = it.fname + it.lName
                                        x.contains(search.value)
                                    }
                                }
                            ) {
                                FriendCard(it,  { onReload() },{onUserProfile(it)}
                                )
                            }
                        }
                    }
                }

            }

        }


    }





}

//@ExperimentalMaterialApi
@Composable
fun FriendCard(user: User,onReload: () -> Unit,onUserProfile: (User) -> Unit
) {
    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.cartoon)


    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)


    Card(elevation = 14.dp, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .clickable { onUserProfile(user) }
    )


    {
        Row {
            Column(
                modifier = Modifier.background(Color.White)
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


            Column (
                modifier = Modifier
                    .width(170.dp)
                    .height(150.dp)
            ){
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
            Column (
                modifier = Modifier
                    .width(140.dp)
                    .height(70.dp)
            ){

                Button(
                    onClick = {
                        sportCommunityViewModel.currentUser?.friends?.remove(user.email)
                        user.friends.remove(sportCommunityViewModel.currentUser?.email)
                        sportCommunityViewModel.UpdateUser()
                        sportCommunityViewModel.UpdateotherUser(user)
                        onReload()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .size(500.dp)

                    ,
                    border = BorderStroke(1.dp, Color(0xFFA500)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(text = "UnFriend")
                }


            }
        }

    }
}

//@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun FriensdCard() {
    FriendCard(
        User("email", "password", "fName", "lName", "city")
        , {}
        ,{}
    )
}