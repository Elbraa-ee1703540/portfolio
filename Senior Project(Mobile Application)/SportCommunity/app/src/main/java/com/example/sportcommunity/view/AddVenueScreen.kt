package com.example.sportcommunity.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.R
import com.example.sportcommunity.components.DefaultSnackbar
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.view.permission.LocationPermissionUI
import com.example.sportcommunity.viewmodel.PermissionViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//lateinit var context: Context

@Composable
fun AddVenueScreen(
    formType: String,
    scaffoldState: ScaffoldState,
    activity: Activity,
    onNextClicked: (
        String, String, String, String,
        MutableList<String>, String, String, String, Double, Double
    ) -> Unit
) {
    val permissionTestViewModel = PermissionViewModel()
    val context = LocalContext.current

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)


    var type by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var facilitie1 by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var lat by remember { mutableStateOf(25.0) }
    var long by remember { mutableStateOf(52.0) }
    var chosenFacilities = mutableListOf<String>()

    if (formType == Screen.EditVenueScreen.title) {
        type = venueOwnerViewModel.venueToBeEdit.type ?: ""
        name = venueOwnerViewModel.venueToBeEdit.name ?: ""
        street = venueOwnerViewModel.venueToBeEdit.street ?: ""
        city = venueOwnerViewModel.venueToBeEdit.city ?: ""
        contactNumber = venueOwnerViewModel.venueToBeEdit.contactNumber ?: ""
        price = "${venueOwnerViewModel.venueToBeEdit.price}" ?: ""
        chosenFacilities = venueOwnerViewModel.venueToBeEdit.facilities ?: mutableListOf<String>()
        size = venueOwnerViewModel.venueToBeEdit.size ?: ""
        lat = venueOwnerViewModel.venueToBeEdit.location[0]
        long = venueOwnerViewModel.venueToBeEdit.location[1]
        chosenFacilities = venueOwnerViewModel.venueToBeEdit.facilities ?: mutableListOf<String>()
        facilitie1 = ""
        chosenFacilities.forEach { facility ->
            facilitie1 += "$facility, "
        }
    }
    var typeExpandable by remember {
        mutableStateOf(false)
    }
    var facilitiesExpandable by remember {
        mutableStateOf(false)
    }

    var sizeExpandable by remember {
        mutableStateOf(false)
    }

    var cityExpandable by remember {
        mutableStateOf(false)
    }

    val typesList = listOf(
        "Football",
        "Basketball",
        "Handball",
        "Tennis",
    )

    val facilitiesList = listOf(
        "Ball",
        "WC",
        "Coffee shop",
        "Internet",
        "Water"
    )

    val sizeList = listOf(
        "4x4",
        "5x5",
        "6x6",
        "7x7",
        "8x8",
        "9x9",
        "10x10",
        "11x11",
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

    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp), elevation = 10.dp
    ) {
        val scroll = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .height((LocalConfiguration.current.screenHeightDp + 200).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = formType, fontSize = 30.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        typeExpandable = !typeExpandable
                    }
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = type,
                    onValueChange = {
                        type = it
                    },
                    enabled = false, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Type"
                        )
                    },
                    label = { Text(text = "select venue type") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(20.dp, 0.dp)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = typeExpandable,
                    onDismissRequest = { typeExpandable = false }) {
                    typesList.forEach {
                        DropdownMenuItem(onClick = {
                            typeExpandable = false
                            type = it
                        }) {
                            Text(text = "$it", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Row(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Venue Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)

                )
            }

            Row(modifier = Modifier.weight(1f)) {

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
                    label = { Text(text = "Contact Number") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text(text = "price per hour") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Row(
                modifier = Modifier
                    .clickable {
                        facilitiesExpandable = !facilitiesExpandable
                    }
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = facilitie1,
                    onValueChange = {
                        facilitie1 = it
                    },
                    enabled = false, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Facilities"
                        )
                    },
                    label = { Text(text = "select the facilities in your venue") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(20.dp, 0.dp)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = facilitiesExpandable,
                    onDismissRequest = { facilitiesExpandable = false }) {
                    facilitiesList.forEach {
                        DropdownMenuItem(onClick = {
                            facilitiesExpandable = false
                            if (chosenFacilities.contains(it)) {
                                chosenFacilities.remove(it)
                            } else {
                                chosenFacilities.add(it)
                            }
                            facilitie1 = ""
                            chosenFacilities.forEach { facilitie ->
                                facilitie1 += "$facilitie, "
                            }
                        }) {
                            Text(text = "$it", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }


            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        sizeExpandable = !sizeExpandable
                    }
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = size,
                    onValueChange = {
                        size = it
                    },
                    enabled = false, leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Size"
                        )
                    },
                    label = { Text(text = "select the Size of your venue") },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(20.dp, 0.dp)
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = sizeExpandable,
                    onDismissRequest = { sizeExpandable = false }) {
                    sizeList.forEach {
                        DropdownMenuItem(onClick = {
                            sizeExpandable = false
                            size = it
                        }) {
                            Text(text = "$it", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
//                    .padding(15.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = street,
                    onValueChange = { street = it },
                    label = { Text(text = "Street") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(20.dp, 0.dp, 0.dp, 0.dp)

                )

                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable { cityExpandable = !cityExpandable }
                        .weight(1f)
                        .padding(0.dp, 0.dp, 20.dp, 0.dp)

                )
                {
                    OutlinedTextField(
                        value = city,
                        onValueChange = {
                            city = it
                        },
                        label = { Text(text = "choose city") },
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
            }
            val scaffoldState = rememberScaffoldState()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(5f)
            ) {


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Button(
                        onClick = {
                            val task = fusedLocationProviderClient.lastLocation
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                ActivityCompat.requestPermissions(
                                    activity,
                                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                                    101
                                )
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                            }
                            task.addOnSuccessListener {
                                if (it != null) {
                                    Toast.makeText(
                                        activity,
                                        "${it.latitude}, ${it.longitude}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    Log.d(
                                        "TAG",
                                        "Location has been granted, ${it.latitude}, ${it.longitude}"
                                    )
                                    lat = it.latitude
                                    long = it.longitude
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Turn on Location in your mobile to use this feature",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp)
                    ) {
                        Text(text = "Get the Location")
                    }
//                LocationPermissionUI(scaffoldState, permissionTestViewModel, activity)
                }

                Row(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Box(Modifier.fillMaxSize()) {

                        val venueLoc = LatLng(lat, long)
                        val cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(venueLoc, 13f)
                        }
                        if (lat == 25.0 && long == 52.0) {
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
                                    title = "${lat}, $long",
                                    snippet = "Marker in ${lat}, $long"
                                )
                            }
                        }
                    }
                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {

                Button(
                    onClick = {
                        if (type != "" && name != "" && contactNumber != "" && price.toDoubleOrNull() != null &&
                            chosenFacilities.size != 0 && size != "" && street != "" && city != ""
                        ) {
                            //TODO: Make sure every entry is valid, such as the phone number,
                            //TODO: Also, ensure that user not mixing between arabic and english language
                            onNextClicked(
                                type, name, contactNumber, price,
                                chosenFacilities, size, street, city, lat, long
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill all fields correctly",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp)
                ) {
                    Text(text = "Next")
                }
            }


            DefaultSnackbar(
                snackbarHostState = scaffoldState.snackbarHostState,
                modifier = Modifier
                    .align(Alignment.End)
                    .weight(1f),
                onAction = {
                    Log.d("TAG", "Snackbar action performed")
                    scaffoldState.snackbarHostState.currentSnackbarData?.performAction()
                },
            )
        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun prev() {
//    AddVenueScreen({ _, _, _, _, _, _, _, _ -> })
//}