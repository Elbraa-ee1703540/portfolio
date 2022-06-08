package com.example.sportcommunity.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportcommunity.R
import com.example.sportcommunity.model.Request
import com.example.sportcommunity.model.User

@Composable
fun SignUp(onSignUpClicked: (String, String, String, String, String, List<String>, MutableList<Request>, MutableList<String>, String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fName by remember { mutableStateOf("") }
    var lName by remember { mutableStateOf("") }
    var favSport by remember { mutableStateOf("") }
    var userType by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    val context = LocalContext.current
    var favSportsList: List<String> = mutableListOf()
    var requests: MutableList<Request> = mutableListOf()
    var friends: MutableList<String> = mutableListOf<String>()
    var favSportExpandable by remember {
        mutableStateOf(false)
    }

    var userTypeExpandable by remember {
        mutableStateOf(false)
    }
    var cityExpandable by remember {
        mutableStateOf(false)
    }
    val SPORTS = mutableMapOf(
        "Futsal" to false,
        "Foot ball" to false,
        "Basket ball" to false,
        "Hand ball" to false,
        "GYM" to false,
        "Table Tennis" to false,
        "Tennis" to false,
        "Cricket" to false,
        "Volleyball" to false,
        "Baseball" to false,
        "Golf" to false,
        "Bicycling" to false,
        "Running" to false,
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
    val userTypeList = listOf(
        "Normal",
        "Owner"
    )

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Image(
                painter = painterResource(R.drawable.picture),
                contentDescription = "",
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            )

            Text(
                text = "Sign Up", fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )


            OutlinedTextField(
                value = fName,
                onValueChange = { fName = it },
                label = { Text(text = "First Name") }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )


            OutlinedTextField(
                value = lName,
                onValueChange = { lName = it },
                label = { Text(text = "Last Name") }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )



            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )




            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { cityExpandable = !cityExpandable }
                    .weight(1f)
            )
            {
                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                    },
                    label = { Text(text = "choose your city") }, enabled = false, leadingIcon = {
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

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { favSportExpandable = !favSportExpandable }
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = favSport,
                    onValueChange = {
                        favSport = it
                    },
                    label = { Text(text = "choose your favorite sports") },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "department"
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = favSportExpandable,
                    onDismissRequest = { favSportExpandable = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    SPORTS.forEach { pair ->
                        DropdownMenuItem(onClick = {
//                            favSportExpandable = false
                            favSport = pair.key
                            SPORTS[pair.key] = !pair.value
                        }) {
                            Text(text = pair.key, fontWeight = FontWeight.Bold)
                            if (pair.value) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = Color.Green,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { userTypeExpandable = !userTypeExpandable }
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = userType,
                    onValueChange = {
                        userType = it
                    },
                    label = { Text(text = "Select your user type") },
                    enabled = false,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "user type"
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = userTypeExpandable,
                    onDismissRequest = { userTypeExpandable = false },
                    modifier = Modifier.width(200.dp)
                ) {
                    userTypeList.forEach {
                        DropdownMenuItem(onClick = {
                            userTypeExpandable = false
                            userType = it
                        }) {
                            Text(text = it, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (fName.isNotEmpty() && lName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        favSportsList = SPORTS.filter { it.value }.map { it.key }
                        try {
                            onSignUpClicked(
                                email, password, fName, lName, city, favSportsList,
                                requests, friends, userType
                            )
                        } catch (e: Exception) {
                            Log.e("TAG", "LoginButton Error: $e ")
                            Toast.makeText(
                                context, "Error: ${e.message} ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            context, "Please provide all the information",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                border = BorderStroke(1.dp, Color(0xFFA500)),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Sign Up ")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUp { userName, Password, fName, lName, _, _, _, _, _ ->

    }
}
