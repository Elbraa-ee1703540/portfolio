package com.example.sportcommunity.view

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel

@Composable
fun ImagePicker(type: String, onImageLoaded: (MutableList<Uri>) -> Unit) {
    val context = LocalContext.current
    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    var counter by remember {
        mutableStateOf(0)
    }

    var imageUriState1 by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageUriState2 by remember {
        mutableStateOf<Uri?>(null)
    }
    var imageUriState3 by remember {
        mutableStateOf<Uri?>(null)
    }

    var doneBtnEnable by remember {
        mutableStateOf(false)
    }

    var chooseBtnEnable by remember {
        mutableStateOf(true)
    }

    var imagesList by remember {
        mutableStateOf(mutableListOf<Uri>())
    }

    if (type == Screen.ImagesEdit.title) {
        imagesList =
            venueOwnerViewModel.venueToBeEdit.images?.map { it.toUri() } as MutableList<Uri>
        imagesList.forEach { uri ->
            when {
                imageUriState1 == null -> {
                    imageUriState1 = uri
                    imageUriState1?.let {
                        imagesList.add(0, it)
                        counter++
                    }
                }
                imageUriState2 == null -> {
                    imageUriState2 = uri
                    imageUriState2?.let {
                        imagesList.add(it)
                        counter++
                    }
                }
                imageUriState3 == null -> {
                    imageUriState3 = uri
                    imageUriState3?.let {
                        imagesList.add(it)
                        counter++
                    }
                }
            }
        }
    }
    Log.d("TAG", "ImagePicker1: ${imagesList.size}")
    val selectImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts
            .GetContent()
    ) { uri ->
        when {
            imageUriState1 == null -> {
                imageUriState1 = uri
                imageUriState1?.let {
                    imagesList.add(0, it)
                    counter++
                }
            }
            imageUriState2 == null -> {
                imageUriState2 = uri
                imageUriState2?.let {
                    imagesList.add(it)
                    counter++
                }
            }
            imageUriState3 == null -> {
                imageUriState3 = uri
                imageUriState3?.let {
                    imagesList.add(it)
                    counter++
                }
            }
        }
        Log.d("TAG", "ImagePicker2: ${imagesList.size}")

    }

    Column(
        modifier = Modifier
            .fillMaxSize(6.0F)
            .padding(10.dp, 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .weight(5f)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageUriState1 != null) {
                    Text(
                        text = "X",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            imagesList.removeIf { it == imageUriState1 }
                            Log.d("TAG", "ImagePicker3: ${imagesList.size}")

                            imageUriState1 = null
                            imageUriState1 = imageUriState2
                            imageUriState2 = imageUriState3
                            imageUriState3 = null
                            counter--
                        })
                } else {
                    Text(text = "X", color = Color.Gray)
                }
                Image(
                    painter = rememberImagePainter(
                        imageUriState1,
                    ),
                    contentDescription = "null",
                    contentScale = ContentScale.FillWidth,
                )
            }
            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                if (imageUriState2 != null) {
                    Text(
                        text = "X",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            imagesList.removeIf { it == imageUriState2 }
                            Log.d("TAG", "ImagePicker4: ${imagesList.size}")

                            imageUriState2 = null
                            imageUriState2 = imageUriState3
                            imageUriState3 = null
                            counter--
                        })
                } else {
                    Text(text = "X", color = Color.Gray)
                }
                Image(
                    painter = rememberImagePainter(
                        imageUriState2,
                    ),
                    contentDescription = "null",
                    contentScale = ContentScale.FillWidth,
                )
            }

            Column(
                modifier = Modifier
                    .weight(5f)
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (imageUriState3 != null) {
                    Text(
                        text = "X",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            imagesList.removeIf { it == imageUriState3 }
                            Log.d("TAG", "ImagePicker5: ${imagesList.size}")
                            imageUriState3 = null
                            counter--

                        })
                } else {
                    Text(text = "X", color = Color.Gray)
                }
                Image(
                    painter = rememberImagePainter(
                        imageUriState3,
                    ),
                    contentDescription = "null",
                    contentScale = ContentScale.FillWidth,
//                        modifier = Modifier
//                            .weight(5f)
//                            .padding(5.dp),
                )
            }
        }

        chooseBtnEnable = counter < 3

        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    selectImageLauncher.launch("image/*")
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(70.dp), enabled = chooseBtnEnable
            ) {
                when (counter) {
                    0 -> Text(text = "choose 1st image")
                    1 -> Text(text = "choose 2nd image")
                    2 -> Text(text = "choose 3rd image")
                }
            }
        }


        if (imageUriState1 != null) {
            doneBtnEnable = true
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    Log.d("TAG", "ImagePicker7: ${imagesList.size}")

                    if (imageUriState1 == null) {
                        Toast.makeText(
                            context,
                            "Please add at least one image for your venue",
                            Toast.LENGTH_LONG
                        ).show()

                    } else {
                        //TODO: display uploading circle until the image uploaded successfully
                        onImageLoaded(imagesList)
                    }
                }, modifier = Modifier
                    .width(200.dp)
                    .height(70.dp), enabled = doneBtnEnable
            ) {
                Text(text = "Done")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    ImagePicker("",{})
}