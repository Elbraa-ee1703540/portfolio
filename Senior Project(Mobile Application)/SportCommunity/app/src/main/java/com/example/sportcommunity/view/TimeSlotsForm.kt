package com.example.sportcommunity.view

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel

@Composable
fun TimeSlotsForm(onAddNewDayClicked: () -> Unit) {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            LazyColumn {
                if (venueOwnerViewModel.venueToBeEdit.timeSlots.keys.toList().isEmpty()) {
                    Log.d("TAG", "TimeSlotsForm: null")
                    item {
                        Text(text = "No Days to display")
                    }
                } else {
                    items(venueOwnerViewModel.venueToBeEdit.timeSlots.keys.toList()) {
                        Log.d("TAG", "TimeSlotsForm: not Null")
                        it
                    }
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onAddNewDayClicked()
            }) {
                Text(text = "Add new day")
            }
        }
    }
}