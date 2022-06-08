package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sportcommunity.R
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.ui.theme.darkGreen
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun Home(
    onViewAll: () -> Unit,
    onClick: (Venue) -> Unit,
    onCardClicked: (Venue) -> Unit,
    onNavigateToTeams: () -> Unit,
    onNavigateToEvents: () -> Unit,
    onNavigateToFriends: () -> Unit
) {
//    val navController = rememberNavController()
//    val scaffoldState = rememberScaffoldState(
//        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    )

    Column() {

        Column(Modifier.padding(horizontal = 16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                Text(
                    text = "Venues nearby you", style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                TextButton(onClick = {
                    onViewAll()
                }) {
                    Text(
                        text = "View all >",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp
                    )
                }
            }
        }

        val sportCommunityViewModel =
            viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
        var venues = sportCommunityViewModel.venues.observeAsState(listOf()).value


        LazyColumn(


            Modifier.height(200.dp),

            ) {
            var filteredVenues = venues.filter {
                it.city == sportCommunityViewModel.currentUser?.city
                        || sportCommunityViewModel.currentUser?.favSports?.contains(it.type) == true
            }
            if (!filteredVenues.isNullOrEmpty()) {
                venues = filteredVenues
            }
            items(venues) {
                venueCard(it, onClick, onCardClicked)
            }

        }
        Spacer(modifier = Modifier.height(29.dp))


        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Discover events, teams, friends",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
//            TextButton(onClick = {}) {
//                Text(text = "More", color = MaterialTheme.colors.primary, style = MaterialTheme.typography.h6)
//            }
        }
        Spacer(modifier = Modifier.height(19.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HomeItemCard(
                    imagePainter = painterResource(id = R.drawable.teams),
                    title = "Create Team",
                    onCardClicked = onNavigateToTeams
                )
            }
            item {
                HomeItemCard(
                    imagePainter = painterResource(id = R.drawable.events),
                    title = "Show Events",
                    onCardClicked = onNavigateToEvents
                )
            }
            item {
                HomeItemCard(
                    imagePainter = painterResource(id = R.drawable.fr),
                    title = "Make friends",
                    onCardClicked = onNavigateToFriends
                )
            }
        }
    }

}

@Composable
fun HomeItemCard(

    title: String = "",

    imagePainter: Painter,
    onCardClicked: () -> Unit

) {
    Card(
        Modifier
            .width(160.dp)
            .height(190.dp)
            .clickable {
                onCardClicked()
            }, backgroundColor = White, elevation = 10.dp
    ) {
        Column(
            Modifier
                .padding(bottom = 32.dp)
        ) {
            Image(
                painter = imagePainter, contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                //.height(190.dp)
                ,

                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, color = darkGreen)

            }
        }
    }
}
