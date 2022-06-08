package com.example.sportcommunity.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.ui.theme.Purple500
import com.example.sportcommunity.viewmodel.DateWithTimeViewModel

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun DateWithTimeView() {


    val context = LocalContext.current
    val viewModel: DateWithTimeViewModel = viewModel()
  var dateTime = viewModel.time.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TextButton(
                    onClick = {
                        viewModel.selectDateTime(context)
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Purple500)
                        .padding(5.dp)
                ) {
                    Text(text = "Select Date & Time", color = Color.White)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = dateTime.value ?: "No Time Set")

            }
        }
    }

