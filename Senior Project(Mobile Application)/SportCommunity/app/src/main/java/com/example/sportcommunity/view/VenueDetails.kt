package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.sportcommunity.R
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun VenueDetails(
    type: String,
    BookClicked: () -> Unit,
    onEditVenueClicked: (Venue) -> Unit,
    onEditTimeSlotsClicked: (Venue) -> Unit
) {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val venue: Venue = if (type == Screen.OwnerVenueDetails.title) {
        venueOwnerViewModel.venueToBeEdit
    } else {
        sportCommunityViewModel.showedVenueDetails
    }

    var imageList = listOf<String>()
    imageList = venue.images!!

    val context = LocalContext.current

    val imageId = context.resources.getIdentifier(
        venue.images?.get(0),
        "drawable",
        context.packageName
    )
    val scroll = rememberScrollState()

    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .verticalScroll(scroll)
                .height(800.dp)
        ) {

            //row1
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
                    .background(Color.Black)
            ) {
                items(imageList) { image ->
                    ShowImage(image)
                }
            }

            //row2
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = Icons.Outlined.MyLocation,
                    contentDescription = "location",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(0.dp, 0.dp, 2.dp, 0.dp)
                )
                Text(text = "${venue.city}", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }

            //row3
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items(venue.facilities!!) {
                    when (it.lowercase()) {
                        "wc" -> {
                            Icon(
                                imageVector = Icons.Outlined.Wc,

                                contentDescription = "WC",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        "ball" -> {
                            Icon(
                                imageVector = Icons.Outlined.SportsSoccer,
                                contentDescription = "ball",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        "internet" -> {
                            Icon(
                                imageVector = Icons.Outlined.Wifi,
                                contentDescription = "Internet",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        "coffee shop" -> {
                            Icon(
                                imageVector = Icons.Outlined.Coffee,
                                contentDescription = "Coffee shop",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        "water" -> {
                            Icon(
                                imageVector = Icons.Outlined.BrandingWatermark,
                                contentDescription = "Water",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        else -> Log.d("TAG", "VenueDetails: $it ")
                    }

                }
            }

            //row4
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .weight(2f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Size:   ${venue.size}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
                Text(
                    text = "${venue.price} QR/H",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                modifier = Modifier
                    .weight(2f)
                    .padding(15.dp),horizontalArrangement = Arrangement.SpaceBetween
            ) {

                if (type == Screen.OwnerVenueDetails.title) {
                    Button(
                        onClick = {
                            onEditTimeSlotsClicked(venue)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0, 106, 31)),
                        ) {
                        Text(
                            "Edit time slots", fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }

                    Button(
                        onClick = {
                            onEditVenueClicked(venue)
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0, 106, 31)),

                        ) {
                        Text(
                            "Edit venue's info",  fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            BookClicked()
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0, 106, 31)),

                        ) {
                        Text(
                            "BOOK NOW", fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

            }

            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .weight(5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Box(Modifier.fillMaxSize()) {

                    val venueLoc = LatLng(venue.location[0], venue.location[1])
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(venueLoc, 13f)
                    }
                    if (venue.location[0] == 25.0 && venue.location[1] == 52.0) {
                        Image(
                            painter = painterResource(id = R.drawable.image_placeholder),
                            contentDescription = "empty",
                            modifier = Modifier.fillMaxSize(),
                        )
                    } else {
                        GoogleMap(
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ) {
                            Marker(
                                position = venueLoc,
                                title = "${venue.name}",
                                snippet = "Marker in ${venue.name}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowImage(image: String) {
    val painter = rememberImagePainter(image, builder = {
        placeholder(R.drawable.image_placeholder)
    })
    val painterState = painter.state
    Image(
        painter = painter, contentDescription = "venue",
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxSize()
            .width(LocalConfiguration.current.screenWidthDp.dp)

    )
    if (painterState is ImagePainter.State.Loading) {
        CircularProgressIndicator()
    }

}

@Preview(showBackground = true)
@Composable
fun prevVenueDetails() {
    VenueDetails("", {}, {}, {})
}