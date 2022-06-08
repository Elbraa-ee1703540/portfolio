package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.model.User
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun UserProfile(){
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var user = User()
    var users = sportCommunityViewModel.users.observeAsState(listOf()).value
    val cartoonImage = painterResource(R.drawable.cartoon)
    Column(
        modifier = Modifier.fillMaxSize()
            .border(width = 40.dp, color = Color.White)
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = cartoonImage, contentDescription = "user",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
        )

        Text(
            text = "${sportCommunityViewModel.otherUser?.fname} ${sportCommunityViewModel.otherUser?.lName}",
            modifier = Modifier.padding(start = 12.dp),
            Color.Blue,
            fontSize = 18.sp
        )
        Text(
            text = "${sportCommunityViewModel.otherUser?.city}",
            modifier = Modifier.padding(start = 12.dp),
            Color.Gray
        )
        Text(
            text = "${sportCommunityViewModel.otherUser?.favSports}",
            modifier = Modifier.padding(start = 12.dp),
            Color.Gray
        )
        Text(
            text = "${sportCommunityViewModel.otherUser?.friends?.map {
                for( u in users){
                    if(u.email == it)
                        user = u
                }
                user.fname + " "+ user.lName}}",
            modifier = Modifier.padding(start = 12.dp),
            Color.Gray
        )
    }

}


@Composable
fun FOFCard(user: User
//            ,onUserProfile: (User) -> Unit
) {
    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.cartoon)


    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)


    Card(elevation = 14.dp, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
//        .clickable { onUserProfile(user) }
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

        }

    }
}