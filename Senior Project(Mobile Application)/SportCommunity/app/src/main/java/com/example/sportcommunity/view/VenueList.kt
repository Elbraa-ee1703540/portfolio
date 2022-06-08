package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.sportcommunity.components.SearchBox
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.ui.theme.*

import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun VenuesList(onClick: (Venue) -> Unit, onCardClicked: (Venue) -> Unit) {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val venues = sportCommunityViewModel.venues.observeAsState(listOf()).value
Column() {

    val selectedCategory: MutableState<VenueCategory?> = mutableStateOf(null)


    var type = remember { mutableStateOf("") }

    var search = remember { mutableStateOf("") }

    SearchBox(text = search,"venue name")

    Column() {
        var type = remember { mutableStateOf("") }





        LazyRow {



            item {
                for (category in getAllFVenueCategories()) {
                    VenueCategoryChip(
                        category = category.type,
                        isSelected = selectedCategory.value == category,

                        onSelectedCategoryChanged = {

                            val newCategory = getVenueCategory(it)

                            selectedCategory.value = newCategory


                            type.value = it



                        },

                        )


                }

//

            }
        }

    }
    Spacer(modifier = Modifier.height(20.dp))
    LazyColumn(modifier = Modifier.background(color = veryLightGray)) {













//                item {
//
//
////
//
//                }
//


        items(


            if (search.value.isEmpty())
                venues
//        else if(type.value== "Football")
//                venues.filter {
//                    it.type == type.value
//                }
//            else if(type.value== "Tennis")
//                venues.filter {
//                    it.type== "Tennis"
//                }

        else{
                venues.filter {
                    it.name!!.contains( search.value)   // search by event name
                }

            }


        ) {
            if (venues.isEmpty()) {
                Text(text = "No venues to display")
            } else {
                venueCard(it, onClick, onCardClicked)
            }
        }
    }
}
}


@Composable
fun venueCard(venue: Venue, onClick: (Venue) -> Unit, onCardClicked: (Venue) -> Unit) {
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
                    onClick = { onClick(venue) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = darkBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 0.dp),
                ) {
                    Text("Book Now", color = Color.White, fontSize = 10.sp)
                }
            }
        }
    }
}


enum class VenueCategory(val type: String){
    ALlSports("All Sports"),
    FootBall("Football"),
    BASKETBALL("Basketball"),
    HANDBALL("Handball"),
    TENNIS("Tennis"),

}

fun getAllFVenueCategories(): List<VenueCategory>{
    return listOf(VenueCategory.ALlSports,
        VenueCategory.FootBall, VenueCategory.BASKETBALL,
        VenueCategory.HANDBALL, VenueCategory.TENNIS)
}

fun getVenueCategory(type: String): VenueCategory? {
    val map = VenueCategory.values().associateBy(VenueCategory::type)
    return map[type]
}


@Composable
fun VenueCategoryChip(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit,

    ){
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 0.dp,
        shape = MaterialTheme.shapes.medium,
        color = if(isSelected) MaterialTheme.colors.primary.copy(alpha= 0.9f) else Color.Transparent,
        contentColor =   Color.White 
    ) {



        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category)

                }
            )
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.body2,
                color =if(isSelected)    Color.White else Color.Black ,
                modifier = Modifier.padding(8.dp)
            )

        }
    }

}