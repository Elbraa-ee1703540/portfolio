package com.example.sportcommunity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.sportcommunity.ui.theme.SportCommunityTheme
import com.example.sportcommunity.navigation.AppNavHost
import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportCommunityTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp(this)
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun MyApp(mainActivity: MainActivity) {
    val scaffoldState =
        rememberScaffoldState(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    val coroutineScope = rememberCoroutineScope()
    val navHostController = rememberNavController()
    val currentRoute = navHostController
        .currentBackStackEntryAsState()?.value?.destination?.route
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentRoute != Screen.Splash.route && currentRoute != Screen.SignIn.route
                && currentRoute != Screen.SignUp.route && currentRoute != Screen.CreateEvent.route && currentRoute != Screen.CreateTeam.route
                && currentRoute != Screen.EventDetails.route && currentRoute != Screen.ProfileScreen.route
            )
                TopBar(
                    scope, scaffoldState, title =
                    when (currentRoute) {
                        Screen.Home.route -> "Home"
                        Screen.EventList.route -> "Events"
                        Screen.VenuesList.route -> "Venues"
                        Screen.AddVenueScreen.route -> "Add Venue"
                        Screen.EditVenueScreen.route -> "Edit Venue"
                        Screen.ImagePickerScreen.route -> "Venue Images"
                        Screen.OwnerVenues.route -> "Owner Venues"
                        Screen.OwnerVenueDetails.route -> "Owner Venues Details"
                        Screen.Teams.route -> "Teams"
                        Screen.MyFriend.route -> "My Friends"
                        Screen.ProfileScreen.route -> "Profile"
                        Screen.Request.route -> "Requests"
                        Screen.EventDetails.route -> "Event Details"
                        Screen.VenueDetails.route -> "Venue Details"
                        Screen.SelectTime.route -> "Available Times"
                        Screen.AddFriend.route -> "Add Friends"
                        else -> ""
                    }

                )
        },


        drawerElevation = 12.dp,
        drawerScrimColor = Color.Blue.copy(alpha = 0.2f),
        drawerShape = CutCornerShape(topEnd = 40.dp),
        drawerContent = {
            if (currentRoute != Screen.SignIn.route && currentRoute != Screen.SignUp.route) {
                Drawer(navHostController, coroutineScope, scaffoldState)
            }
        },
        bottomBar = { BottomBar(navHostController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            AppNavHost(navHostController, scaffoldState, mainActivity)
        }
    }
}


//@Composable
//fun TopBar(navHostController: NavHostController, currentRoute: String?) {
//    if (currentRoute != Screen.SignIn.route && currentRoute != Screen.SignUp.route && currentRoute != Screen.Splash.route
//    ) {
//
//        TopAppBar(
//            title = {
//                when (currentRoute) {
//                    Screen.SignIn.route -> Text(text = "Login")
//                    Screen.Home.route -> Text(text = "Home")
//                    Screen.VenueDetails.route -> Text(text = "Venue Details")
//
//                }
//            },
//            backgroundColor = MaterialTheme.colors.primary,
//            elevation = 0.dp
//        )
//    }
//}

@Composable
fun Drawer(
    navController: NavController,
    coroutineScope: CoroutineScope, scaffoldState: ScaffoldState
) {

    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val drawerItems = mutableListOf<Screen>(
        Screen.ProfileScreen, Screen.MyFriend, Screen.Request, Screen.MyBooking
    )

    sportcommunityViewModel.currentUser?.let {
        //if (it.userType != "Normal" )
        // drawerItems.removeAt()
    }
    Column(
        modifier = Modifier.background(Color.White)
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        Image(
            painter = rememberImagePainter(
                R.drawable.cartoon
            ), contentDescription = "venue",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(width = 1.dp, color = Color.Blue, shape = CircleShape)
        )
//        Text(
//            text = "Name:${     sportcommunityViewModel.currentUser?.fname+sportcommunityViewModel.currentUser?.fname
//            }",
//            textAlign = TextAlign.Start,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(12.dp)
//                .align(Alignment.Start)
//        )


        sportcommunityViewModel.currentUser?.let {
            drawerItems.add(0, Screen.SignOut)
            Text(
                text = "${it.fname} ${it.lName}",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.Start)
            )


            if (it.userType != "Normal")
                drawerItems.removeAt(2)

        } ?: drawerItems.add(0, Screen.SignOut)

    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    drawerItems.forEach { item ->
        Item(item = item, selected = currentRoute == item.route,
            onItemClick = {
                var routeTo = it
                if (it == Screen.SignOut) {
                    sportcommunityViewModel.signOut()
                    routeTo = Screen.SignIn
                }
                navController.navigate(routeTo.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route)
                    }
                    launchSingleTop = true
                }
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            })
    }
}

@Composable
fun Item(item: Screen, selected: Boolean, onItemClick: (Screen) -> Unit) {


    val background = if (selected)
        MaterialTheme.colors.primaryVariant
    else
        MaterialTheme.colors.primarySurface

    if (item.title == "Divider") {
        Divider()
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(item) })
                .height(45.dp)
                .background(background)
                .padding(start = 10.dp)
        ) {
            Image(
                imageVector = item.icon,
                contentDescription = item.title,
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.title,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }

}


@ExperimentalAnimationApi
@Composable
fun CustomBottomNavigationItem(screen: Screen, isSelected: Boolean, onClick: () -> Unit) {

    val background =
        if (isSelected) MaterialTheme.colors.primary.copy(alpha = 0.1f) else Color.Transparent
    val contentColor =
        if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Icon(
                imageVector = screen.icon,
                contentDescription = null,
                tint = contentColor
            )

            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }

        }
    }


}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState, title: String
) {

    TopAppBar(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .clip(RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp))
            .height(80.dp),

        title = {
            Row() {
                Text("$title")
                Spacer(modifier = Modifier.width(160.dp))
                IconButton(onClick = {

                }) {
                    Icon(imageVector = Icons.Filled.Notifications, contentDescription = "Menu Icon")
                }

            }


        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }

            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu Icon")
            }

        },

        )

}


@ExperimentalAnimationApi
@Composable
fun BottomBar(navHostController: NavHostController) {

    var bottomNavItems = mutableListOf<Screen>(
        Screen.Home,
        Screen.VenuesList,
        Screen.OwnerVenues,
//        Screen.VenueDetails,
        Screen.AddVenueScreen,
        Screen.EventList,
        Screen.Teams
    )
    val sportcommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    sportcommunityViewModel.currentUser?.let {
        if (it.userType == "Normal") {
            bottomNavItems.removeAt(3)
            bottomNavItems.removeAt(2)
        }

        if (it.userType != "Normal") {
            bottomNavItems.removeAt(1)
            bottomNavItems.removeLast()
            bottomNavItems.removeLast()
        }
//        Text(
//            text = "${it.fname} ${it.lName}",
//            textAlign = TextAlign.Start,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .padding(12.dp).
//                .align(Alignment.Start)
//        )
    }
    val currentRoute = navHostController
        .currentBackStackEntryAsState()?.value?.destination?.route



    if (currentRoute != Screen.SignIn.route && currentRoute != Screen.SignUp.route && currentRoute != Screen.Splash.route) {
        /// BottomNavigation {

        Row(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomNavItems.forEach { screen ->


                CustomBottomNavigationItem(
                    screen = screen,
                    isSelected = screen.route == currentRoute
                ) {
                    navHostController.navigate(screen.route)


                }
            }
//                BottomNavigationItem(
//                    selected = currentRoute == screen.route,
//                    onClick = { navHostController.navigate(screen.route) },
//                    icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
//                    label = { Text(text = screen.title) },
//                    alwaysShowLabel = true
//                )
        }
    }
}

//}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SportCommunityTheme {
        // MyApp()
    }
}


//    var imageUriState = mutableStateOf<Uri?>(null)
//    private val selectImageLauncher = registerForActivityResult(
//        ActivityResultContracts
//            .GetContent()
//    ) { uri ->
//        imageUriState.value = uri
//    }
//
//    @Composable
//    fun imagePicker() {
//        Box() {
//            Column() {
//
//                if (imageUriState.value != null) {
//                    Log.d("Tag", imageUriState.value.toString())
////                   GlideImage(data = imageUriState.value!!) {
////
////                   }
//                    Image(
//                        painter = rememberImagePainter(
//                            imageUriState.value,
//                        ),
//                        contentDescription = null,
//                        contentScale = ContentScale.FillWidth,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                    )
//                    val storage = Firebase.storage
//
//                    val storageRef = storage.reference
//
//// Create a reference to "mountains.jpg"
//                    val mountainsRef = storageRef.child("${imageUriState.value}.jpg")
//                    val uploadTask = mountainsRef.putFile(imageUriState.value!!)
//
//                    val urlTask = uploadTask.continueWithTask { task ->
//                        if (!task.isSuccessful) {
//                            task.exception?.let {
//                                throw it
//                            }
//                        }
//                        mountainsRef.downloadUrl
//                    }.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val downloadUri = task.result
//                            Log.d("Tag", imageUriState.value.toString())
//                            imageUriState.value = downloadUri
//                        } else {
//                            Log.d("FAILED", imageUriState.value.toString())
//
//                        }
//                    }
//
//                }
//
//                Button(onClick = { selectImageLauncher.launch("image/*") }) {
//                    Text(text = "Open Gallery")
//                }
//            }
//        }
//    }