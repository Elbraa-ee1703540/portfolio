package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.ui.theme.*
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel

@Composable
fun OwnerVenues(onEditClick: (Venue) -> Unit, onCardClicked: (Venue) -> Unit) {
    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var venues = sportCommunityViewModel.venues.observeAsState(listOf()).value
    LazyColumn(modifier = Modifier.background(color = veryLightGray)) {
        if (venues.isEmpty()) {
            item {
                Text(text = "No venues to display")
            }
        } else {
            venues = venues.filter { it.ownerUser == sportCommunityViewModel.currentUser?.email }
            items(venues) {
                OwnerVenueCard(it, onEditClick, onCardClicked)
            }
        }
    }
}


@Composable
fun OwnerVenueCard(venue: Venue, onEditClick: (Venue) -> Unit, onCardClicked: (Venue) -> Unit) {
    val context = LocalContext.current

    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(15.dp)
            .clickable { onCardClicked(venue) }
    ) {
        val painter = rememberImagePainter(
            venue.images?.get(0),
        )
        val painterState = painter.state
        Image(
            painter = painter,
            contentDescription = "venue",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
//                .clip(RoundedCornerShape(15.dp))
        )
        if (painterState is ImagePainter.State.Loading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .weight(5f)
                    .background(Color(3, 13, 102, 150)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${venue.name}",
                    modifier = Modifier.padding(18.dp, 5.dp),
                    fontSize = 15.sp, fontWeight = FontWeight.Bold,
                    color = phsfory
                )
                Row(
                    modifier = Modifier.padding(15.dp, 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = "location",
                        modifier = Modifier.size(13.dp)
                    )
                    Text(text = "${venue.city}", fontSize = 10.sp, color = Color.White)
                }
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(2f)
                    .background(Color.White.copy(alpha = 0.4f))
                    .padding(5.dp),

                ) {

                Text(
                    text = "${venue.size}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 10.dp)
                )
                Text(text = "${venue.price} QR", fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Button(
                    onClick = { onEditClick(venue) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = darkBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 0.dp),
                ) {
                    Text("Edit", color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}
