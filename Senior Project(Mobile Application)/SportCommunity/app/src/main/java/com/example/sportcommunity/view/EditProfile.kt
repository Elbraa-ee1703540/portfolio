package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.model.FSport
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.User
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.google.firebase.firestore.Exclude


@Composable
fun onEdit(onBackClicked:()->Unit={}, onSaveClicked:(User)->Unit) {
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    sportcommunityViewModel.currentUser?.let {

        var fName by remember {
            mutableStateOf(it.fname ?: "")
        }
        var lName by remember {
            mutableStateOf(it.lName ?: "")
        }
        var city by remember {
            mutableStateOf(it.city ?: "")
        }

        var cityExpandable by remember {
            mutableStateOf(false)
        }

        val SPORTS = listOf(
            "Futsal" ,
            "Foot ball" ,
            "Basket ball" ,
            "Hand ball" ,
            "GYM" ,
            "Table Tennis" ,
            "Tennis" ,
            "Cricket" ,
            "Volleyball" ,
            "Baseball" ,
            "Golf" ,
            "Bicycling" ,
            "Running" ,
        )
        val CITIES = listOf(
            "Doha",
            "Al Ghanim",
            "Al Ghuwariyah",
            "Al Khalifat",
            "Al Khor",
            "Al Wakrah",
            "Al Rayyan",
            "Ar Ru'ays",
            "As Salatah al Jadidah",
            "Dukhan",
            "Ras Laffan",
            "Industrial City",
            "Umm Bab",
            "Umm Salal Ali",
            "Umm Salal Mohammed"
        )
        var MyfavSports = SPORTS.map {
            FSport(
                sport = it,
                isSelected = false
            )
        }
     for (z in it.favSports){
         for(c in MyfavSports){
             if (z == c.sport){
                 c.isSelected = true
             }
         }
     }

        var favSports by remember {
            mutableStateOf(
               MyfavSports
            )
        }

        val verticalScroll = rememberScrollState()
        Column(
            modifier = Modifier .verticalScroll( verticalScroll).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,


        ) {

            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Back", modifier = Modifier
                            .padding(8.dp)
                            .clickable { onBackClicked()}
                        , color =   MaterialTheme.colors.primarySurface,


                        )
                        Text(text = "Save", modifier = Modifier
                            .padding(8.dp)
                            .clickable {

                                var EditUser = User(fname=fName,lName=lName,city=city,favSports= favSports.filter { it.isSelected }.map { it.sport } as MutableList<String>)

                                onSaveClicked(EditUser)

                            }, color =   MaterialTheme.colors.primarySurface,

                            )

                    }


                },backgroundColor = Color.Transparent,elevation = 0.dp
            )
            val cartoonImage = painterResource(R.drawable.cartoon)
            Image(
                painter = cartoonImage, contentDescription = "user",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
            )
            OutlinedTextField(
                value = fName,
                onValueChange = { fName = it },
                label = { Text(text = "First Name") }, modifier = Modifier
                    .fillMaxWidth()
            )


            OutlinedTextField(
                value = lName,
                onValueChange = { lName = it },
                label = { Text(text = "Last Name") }, modifier = Modifier
                    .fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { cityExpandable = !cityExpandable }
            )
            {
                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                    },
                    label = { Text(text = "choose your city") },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "city"
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = cityExpandable,
                    onDismissRequest = { cityExpandable = false }) {
                    CITIES.forEach {
                        DropdownMenuItem(onClick = {
                            cityExpandable = false
                            city = it
                        }) {
                            Text(text = it, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Text(text = "favourite Sports List: ", fontWeight = FontWeight.Bold)

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .width(400.dp)
                    .padding(8.dp)
                    .border(width = 1.dp, color = Color.Black)
            ) {
                favSports?.let {
                    items(it.size) { i ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    favSports = favSports!!.mapIndexed { j, item ->
                                        if (i == j) {
                                            item.copy(isSelected = !item.isSelected)
                                        } else item
                                    }
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            favSports?.get(i)?.sport?.let { it1 ->
                                Text(text = "${it1}")
                            }
                            if(favSports?.get(i)?.isSelected == true) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.Green,
                                    modifier = Modifier.size(20.dp).border(width = 0.5.dp, color = Color.Red)
                                )
                            }
                        }
                    }
                }
            }

//
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(text = "Back", modifier = Modifier
//                    .padding(8.dp)
//                    .clickable { onBackClicked()})
//                Text(text = "Save", modifier = Modifier
//                    .padding(8.dp)
//                    .clickable {
//
//                        var EditUser = User(fname=fName,lName=lName,city=city,favSports= favSports.filter { it.isSelected }.map { it.sport } as MutableList<String>)
//
//                        onSaveClicked(EditUser)
//
//                    })
//
//            }

        }
    }

}
