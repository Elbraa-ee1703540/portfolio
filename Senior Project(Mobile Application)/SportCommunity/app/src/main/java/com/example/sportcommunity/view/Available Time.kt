package com.example.sportcommunity.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sportcommunity.ui.theme.Purple500


//@Composable
//fun AvailableTimes(from: String = "", to: String = "", onAlert: () -> Unit) {
//
//
//    Surface(modifier = Modifier
//        .height(60.dp)
//        .width(150.dp)
//        .clickable {
//            onAlert(
//
//            )
//
//        }
//        .padding(4.dp), shape = RoundedCornerShape(16.dp), elevation = 6.dp, color = Color.White
//    ) {
//        Column(modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center) {
//
//
//            Text(
//                text = "$from:00:PM - ${to}:00PM",
//
//                modifier = Modifier
//                    .padding(5.dp)
//                    .align(Alignment.CenterHorizontally),
//
//                )
//
//
//        }
//    }
//
//}

@Composable
fun ShowPopDialog(isDialogOpen: MutableState<Boolean>) {

    if (isDialogOpen.value) {
        Dialog(onDismissRequest = { isDialogOpen.value = false }) {
            Surface(
                modifier = Modifier
                    .width(870.dp)
                    .height(590.dp)
                    .padding(5.dp),
                shape = RoundedCornerShape(5.dp),
                color = Color.White
            ) {




                Column() {
                    Text(
                        text = "Choose Date and  time ",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 6.dp)
                    )


                    Spacer(modifier = Modifier.height(20.dp))

                    DateWithTimeView()
                   // Spacer(modifier = Modifier.height(20.dp))




                }



            }
        }
    }





}
// not used yet

@Composable
fun AvailableTimeScreen() {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 26.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Choose available time ",
                color = Color(0xFF5C5C5C),
                //fontFamily = ,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
//
        }

        Column() {

            Card(modifier = Modifier.fillMaxSize()) {
                Column() {
                    Row() {
                        // for now dummy data
                        AvailableTimesUI("4:00-5:00 pm")
                        AvailableTimesUI("6:00-7:00 pm")

                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row() {
                        AvailableTimesUI("8:00-9:00 pm")
                        AvailableTimesUI("9:00-10:00 pm")

                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Row() {
                        AvailableTimesUI("10:00-11:00 pm")
                        AvailableTimesUI("11:00-1200 pm")

                    }

                }


            }
        }
    }

}


@Composable
fun AvailableTimesUI(
    text: String,
    enabled: MutableState<Boolean> = remember { mutableStateOf(true) }
) {
    OutlinedButton(
        onClick = {
            // todo once we get days and times from venue owner we will display them to the users and they can choose the suitable time, day for them
            enabled.value = false


        }, elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Color(0xFF9B51E0)
        ), enabled = enabled.value,
        shape = RoundedCornerShape(26.dp),
        border = ButtonDefaults.outlinedBorder,
        modifier = Modifier.padding(end = 6.dp)
    ) {
        Text(text = text)
    }
}

