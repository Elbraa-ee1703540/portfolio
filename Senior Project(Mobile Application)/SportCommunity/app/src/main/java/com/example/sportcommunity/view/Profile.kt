package com.example.sportcommunity.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun Profile(){
    val sportcommunityViewModel = viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

   val currentUSer =  sportcommunityViewModel.currentUser

    var name by rememberSaveable { mutableStateOf(currentUSer?.fname + " " + currentUSer?.lName  ?: "") }
    var city by rememberSaveable { mutableStateOf(currentUSer?.city?: "") }
   // var favSports by rememberSaveable { mutableStateOf(currentUSer?.favSports ?: "") }
    ProfileContent(
        sportcommunityViewModel = sportcommunityViewModel,
        name = name,
        city = city,
  //favSports = emptyList() ,
        onNameChange = {name= it},
        onUCityChange ={ city= it} ,
      //  onFavSportsChange = {favSports= it},
        onSave = {


        },
        onBack = { /*TODO*/ },onLogout = {}
    )

}


@Composable
fun ProfileContent(

    sportcommunityViewModel:SportCommunityViewModel,
    name: String,
    city: String,
    onNameChange: (String) -> Unit,
    onUCityChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,


    ) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier
                .padding(8.dp)
                .clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier
                .padding(8.dp)
                .clickable { onSave.invoke() })

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name, onValueChange = onNameChange, colors =
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "City", modifier = Modifier.width(100.dp))
            TextField(
                value = city, onValueChange = onUCityChange, colors =
                TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )

        }

//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 4.dp, end = 5.dp),
//            verticalAlignment = Alignment.Top
//        ) {
//            Text(text = "FavSport", modifier = Modifier.width(100.dp))
//            TextField(
//                value = favSports.toString(), onValueChange = onFavSportsChange, colors =
//                TextFieldDefaults.textFieldColors(
//                    backgroundColor = Color.Transparent,
//                    textColor = Color.Black,
//                ), singleLine = false, modifier = Modifier.height(150.dp)
//            )

//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp, bottom = 16.dp), horizontalArrangement = Arrangement.Center
//        ) {
//            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() })
//
//        }

    }

}