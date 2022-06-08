import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.Event
import com.example.sportcommunity.model.Team
import com.example.sportcommunity.model.User
import com.example.sportcommunity.view.MemberCard
import com.example.sportcommunity.viewmodel.EventViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.TeamViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun TeamsList(onAddTeam: () -> Unit) {
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val teamViewModel =
        viewModel<TeamViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val x = teamViewModel.teams.observeAsState(listOf()).value
//    val teams = teamViewModel.teams.observeAsState(listOf()).value


    val teams = x.filter {
        it.Users.contains(sportcommunityViewModel.currentUser?.email)
                ||
                sportcommunityViewModel.currentUser?.email?.let { it1 -> it.leader?.contains(it1) } == true
    }

    Log.d("TAG11", "${teams}" )
    Log.d("TAG11", "${x.size}" )
    Log.d("TAG11", "${sportcommunityViewModel.currentUser?.email}" )
//    val coroutineScope = CoroutineScope(Dispatchers.Main)
//    var user = User()
//    for( f in sportcommunityViewModel.currentUser?.friends!!) {
//        coroutineScope.launch {
////                if(sportcommunityViewModel.usersfrind.isEmpty()) {
//////                    sportcommunityViewModel.usersfrind.removeAll(sportcommunityViewModel.usersfrind)
////                    user = sportcommunityViewModel.getUser(f)!!
////                    Log.e("Eeee", user.toString())
////                    sportcommunityViewModel.usersfrind.add(user)
////                }
//            for( u in sportcommunityViewModel.usersfrind){
//                if(u?.email != f){
////                    sportcommunityViewModel.usersfrind.removeAll(sportcommunityViewModel.usersfrind)
//                    user = sportcommunityViewModel.getUser(f)!!
//                    Log.e("Eeee", user.toString())
//                    sportcommunityViewModel.usersfrind.add(user)
//                }
//
//            }
//
//        }
//    }
    var users = sportcommunityViewModel.users.observeAsState(listOf()).value
    var Friends: List<User> = listOf()
    Log.e("Eeee", users.toString())
    for (f in sportcommunityViewModel.currentUser?.friends!!) {
        Friends = users.filter { u ->
            u.email == f
        }
    }


    Surface() {

        val context = LocalContext.current


        Scaffold(
//            topBar = {
//            var Teams = listOf<String>("", "")
//            TopAppBar(
//                title = { Text("Team") },
//                actions = {
//                }
//            )
//
//        },
            floatingActionButton = {

                ExtendedFloatingActionButton(modifier = Modifier.height(40.dp), text = {
                    Text(text = "Create Team")
                }, onClick = { onAddTeam() })

            }) {

            Surface(modifier = Modifier.fillMaxSize()) {


                Column() {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(12.dp)
                    ) {

                    }

                    if (teams.isNotEmpty()) {
                        var search = remember { mutableStateOf("") }
                        Column() {
                            SearchBox(text = search, "team")
                            LazyColumn {
                                items(
                                    if (search.value.isEmpty())
                                        teams
                                    else {
                                        teams.filter {
                                            it.TeamName!!.contains(search.value) // search by event name
                                        }
                                    }
                                ) {
                                    Log.d("TAG112132", "${it.Users.size}" )
                                    TeamCard(it)

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
fun TeamCard(team: Team) {
    val s_team = painterResource(R.drawable.successfulteam)
    val leader_team = painterResource(R.drawable.redflag)
    Card(
        shape = RoundedCornerShape(19.dp),
        backgroundColor = Color.White,
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {

        Row {

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .width(60.dp)
                    .height(80.dp)
            ) {
                Image(
                    painter = s_team, contentDescription = "user",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(width = 0.5.dp, color = Color.Red, shape = CircleShape)
                )
            }

            Column(
                modifier = Modifier
                    .width(300.dp)

            ) {
                Text(
                    text = "${team.TeamName}",
                    modifier = Modifier.padding(start = 0.dp),
                    Color.Blue,
                    fontSize = 18.sp
                )
//                Text(text = "Team Capacity: ${team.capacity}", fontSize = 16.sp)
                Column() {
                    Text(
                        text = "${team.description}",
                        modifier = Modifier.padding(start = 0.dp),
                        Color.Gray,
                        fontSize = 16.sp
                    )

                }

                Row() {
                    team.leader?.let { it.let { it1 -> MemberCard2(it1) } }

                    Image(
                        painter = leader_team, contentDescription = "user",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
                team.Users.map { MemberCard2(it) }
                Log.d("TAG112132", "${team.Users.size}" )
            }

            Column(

            ) {
                Text(
                    text = "${team.capacity}", fontSize = 20.sp,
                    color = Color.Green
                )
            }

        }

    }
}

@Composable
fun MemberCard2(email: String) {
    val context = LocalContext.current
    val cartoonImage = painterResource(R.drawable.cartoon)
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    var users = sportCommunityViewModel.users.observeAsState(listOf()).value

    var user = users.find { u ->
        u.email == email
    }



    Card(
        modifier = Modifier
            .padding(5.dp)
            .width(250.dp)
            .height(30.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .width(60.dp)
                    .height(80.dp)
            ) {

                Image(
                    painter = cartoonImage, contentDescription = "user",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(width = 0.5.dp, color = Color.Blue, shape = CircleShape)

                )
            }
            Column(
                modifier = Modifier
                    .width(170.dp)
                    .height(150.dp)
            ) {
                Text(
                    text = "${user?.fname} ${user?.lName}",
                    modifier = Modifier.padding(start = 0.dp),
                    Color.Blue,
                    fontSize = 13.sp
                )
//                Text(
//                    text = "${user.email}",
//                    modifier = Modifier.padding(start = 0.dp),
//                    Color.Gray,
//                    fontSize = 10.sp
//                )
            }
        }

    }
}
