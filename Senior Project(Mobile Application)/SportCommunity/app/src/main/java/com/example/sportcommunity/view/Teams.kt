package com.example.sportcommunity.view

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel

@Composable
fun Teams() {
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

//    val lisOfTeams= mutableStateListOf<Team>()
    val context = LocalContext.current
    var isCreated by remember {
        mutableStateOf(false)
    }
    var isShowed by remember {
        mutableStateOf(false)
    }

    Surface() {
        var showMenu by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Scaffold(
            topBar = {
            var teams = listOf<String>("Create Team","Show Available Team")
            TopAppBar(
                title = { Text("Teams") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, "")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        teams.forEach {
                            DropdownMenuItem(onClick = {
                                showMenu = false
                                if (it == "Show Available Team") {
                                    isShowed= !isShowed
                                    isCreated= false
                                }
                                else{
                                    isShowed=false
                                    isCreated = !isCreated
                                }
                            }) {
                                Text(text = it)
                            }
                        }
                    }
                }
            )
        },
            floatingActionButton = {
            }) {
            Surface(modifier = Modifier.fillMaxSize()) {

                Column() {
                    Text(
                        text = "There is no such teams you added",
                        fontWeight = FontWeight.Bold,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(12.dp)
                    ) {

                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (isCreated) {
                        }

                        if (isShowed) {
                            showToast(context ,"No Team found")
                        }
                    }
                }
            }

        }

    }
}
fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}


