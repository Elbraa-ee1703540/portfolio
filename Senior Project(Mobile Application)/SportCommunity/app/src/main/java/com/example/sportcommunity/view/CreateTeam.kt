package com.example.sportcommunity.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.components.InputField
import com.example.sportcommunity.components.showToast
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.TeamMember
import com.example.sportcommunity.model.User
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@Composable
fun CreateTeam(onBackArrowClicked:()->Unit={}, onaddTeam:(Team) -> Unit){
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val teamViewModel =
        viewModel<TeamViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val TeamName = remember { mutableStateOf("") }
    val coroutineScope = CoroutineScope(Dispatchers.Main)
    val description = remember { mutableStateOf("") }
    var user = User()
    val context = LocalContext.current
    val T_members = mutableListOf<String>()
    var Team_Member by remember {
        mutableStateOf(
            sportCommunityViewModel.currentUser?.friends?.map {
                Log.e("Friends2", it.toString())
                TeamMember(
                    user = it,
                    isSelected = false
                )
            }
        )
    }
    Log.e("dgdfgdfgd", Team_Member?.isEmpty().toString())
    var memberExpandable by remember {
        mutableStateOf(false)
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar (
            title = {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back",
                    tint =   MaterialTheme.colors.primarySurface,
                    modifier = Modifier.clickable { onBackArrowClicked.invoke() })

                Spacer(modifier = Modifier.width(40.dp) )
                Text(text = "Create Team",
                    color =   MaterialTheme.colors.primarySurface,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                )

            },backgroundColor = Color.Transparent,elevation = 0.dp
        )

        InputField(
            valueState = TeamName,
            labelId = "Team Name",
            enabled = true
        )

        InputField(
            valueState = description,
            labelId = "description",
            enabled = true
        )



        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

//            Row(verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier
//                    .clickable { memberExpandable = !memberExpandable }
//                    .weight(1f)
//            )
//            {
//                OutlinedTextField(
//                    value = user,
//                    onValueChange = {
//                        user = it
//                    },
//                    label = { Text(text = "Choose your Team Members") }, enabled = false, leadingIcon = {
//                        Icon(
//                            imageVector = Icons.Default.ArrowDropDown,
//                            contentDescription = "Member"
//                        )
//                    },
//                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
//                        .fillMaxWidth()
//                )
//
//                DropdownMenu(
//                    expanded = memberExpandable,
//                    onDismissRequest = { memberExpandable = false }) {
//                    sportCommunityViewModel.currentUser?.Friends?.forEach {
//                        DropdownMenuItem(onClick = {
//                            memberExpandable = false
//                            user = it.email.toString()
//                        }) {
//                            Text(text = it.fname.toString(), fontWeight = FontWeight.Bold)
//                        }
//                    }
//                }
//            }

            Text(text = "Choose Your Team Member..",
                color = Color.Black,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Team_Member.let {
                    it?.size?.let { it1 ->
                        items(it1) { i ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        Team_Member = Team_Member?.mapIndexed { j, item ->
                                            if(i == j) {
                                                item.copy(isSelected = !item.isSelected)
                                            } else item
                                        }
                                    }
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                            //                            Text(text = "${items?.get(i)?.user}")
                                Log.e("Eeee222222", Team_Member.toString())
                                Team_Member?.get(i)?.user?.let { it1 -> MemberCard(it1) }
                                if(Team_Member?.get(i)?.isSelected == true) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.Green,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            var capacity = (Team_Member?.filter { it.isSelected }?.size?.plus(1)).toString()

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val valid = remember(TeamName.value,description.value) { TeamName.value.trim().isNotEmpty()
                        &&  description.value.isNotEmpty()
                }
                Button(
                    modifier = Modifier
                        .width(280.dp)
                        .height(50.dp),
                    onClick = {
                        if (valid){
                            val newTeam = Team_Member.let { Team(TeamName = TeamName.value,capacity=capacity,description=description.value,Users = it?.filter { it.isSelected }?.map { it.user } as MutableList<String>, leader = sportCommunityViewModel.currentUser?.email
                            ) }
                            if (newTeam != null) {
//                                teamViewModel.addTeams(newTeam)
                                onaddTeam(newTeam)
                            }
                            showToast(context ,"Team Created Successfully")

                            TeamName.value=""
                            onBackArrowClicked.invoke()
                        }

                        else{
                            showToast(context ,"Fill all info please")

                        }
                    },
                    shape = RoundedCornerShape(50),
                    colors =  ButtonDefaults.buttonColors(
                        backgroundColor = Color(ContextCompat.getColor(context,R.color.darkGreen)),
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


@Composable
fun MemberCard(email: String) {


    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.cartoon)
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var users = sportCommunityViewModel.users.observeAsState(listOf()).value

        var user = users.find { u ->
            u.email == email
        }

    Log.e("Eeee12312", user.toString())

    Card(elevation = 14.dp, modifier = Modifier
        .padding(10.dp)
        .width(250.dp)
        .height(100.dp)
        ) {
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
                    text = "${user?.fname} ${user?.lName}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Blue,
                    fontSize = 18.sp
                )
                Text(
                    text = "${user?.city}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Gray
                )
                Text(
                    text = "${user?.email}",
                    modifier = Modifier.padding(start = 12.dp),
                    Color.Gray
                )
            }
        }

    }
}