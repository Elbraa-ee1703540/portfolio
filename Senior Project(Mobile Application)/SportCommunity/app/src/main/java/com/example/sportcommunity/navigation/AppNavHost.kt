package com.example.sportcommunity.navigation

import TeamsList
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sportcommunity.SportSplashScreen
import com.example.sportcommunity.model.Booking
import com.example.sportcommunity.model.Venue
import com.example.sportcommunity.view.*
import com.example.sportcommunity.viewmodel.EventViewModel
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.example.sportcommunity.viewmodel.TeamViewModel
import com.example.sportcommunity.viewmodel.VenueOwnerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun AppNavHost(
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    activity: Activity
) {
    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val venueOwnerViewModel =
        viewModel<VenueOwnerViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val teamViewModel =
        viewModel<TeamViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val eventViewModel =
        viewModel<EventViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val context = LocalContext.current

    NavHost(navController = navHostController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SportSplashScreen(navController = navHostController)

        }

        composable(route = Screen.SignUp.route) {
            SignUp { email, password, fName, lName, city, favSports, requests, friends, userType ->
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    sportCommunityViewModel.registerNewUser(
                        email,
                        password,
                        fName,
                        lName,
                        city,
                        favSports as MutableList<String>,
                        requests,
                        friends,
                        userType
                    )

                    if (sportCommunityViewModel.isSignedUp.value == true) {
                        if (userType == "Owner") {
                            navHostController.navigate(Screen.OwnerVenues.route)
                        } else {
                            navHostController.navigate(Screen.Home.route)
                        }

                    }
                }
            }
        }
        composable(route = Screen.SignIn.route) {
            SignIn(
                onSignInClicked = { email, password ->
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    coroutineScope.launch {
                        sportCommunityViewModel.signIn(email, password)

                        if (sportCommunityViewModel.isSignedIn.value == true) {
                            if (sportCommunityViewModel.currentUser?.userType ?: "" == "Normal")
                                navHostController.navigate(Screen.VenuesList.route)
                            else
                                navHostController.navigate(Screen.OwnerVenues.route)

                        } else {
                            Toast.makeText(
                                context,
                                "Invalid email or password, please correct and renter",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                onSignUpTextClicked = {
                    navHostController.navigate(Screen.SignUp.route)
                })
        }
        composable(route = Screen.Home.route) {
            Home(onViewAll = { navHostController.navigate(Screen.VenuesList.route) }, onClick = {
                sportCommunityViewModel.showedVenueDetails = it
                navHostController.navigate(Screen.SelectTime.route)
            }, onCardClicked = {
                sportCommunityViewModel.showedVenueDetails = it
                navHostController.navigate(Screen.VenueDetails.route)
            }, onNavigateToEvents = {
                navHostController.navigate(Screen.EventList.route)
            }, onNavigateToFriends = {
                navHostController.navigate(Screen.MyFriend.route)

            }, onNavigateToTeams = {
                navHostController.navigate(Screen.Teams.route)

            }
            )
//            Home {
//                navHostController.navigate(Screen.VenuesList.route)
//            }
        }

        composable(route = Screen.OwnerHome.route) {
            OwnerHome()

        }

        composable(route = Screen.VenuesList.route) {
            VenuesList(
                {
                    sportCommunityViewModel.showedVenueDetails = it
                    navHostController.navigate(Screen.SelectTime.route)
                }, {
                    sportCommunityViewModel.showedVenueDetails = it
                    navHostController.navigate(Screen.VenueDetails.route)
                }
            )
        }

        composable(route = Screen.VenueDetails.route) {
            VenueDetails(Screen.VenueDetails.title, {
                navHostController.navigate(Screen.SelectTime.route)
            }, onEditVenueClicked = {
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.EditVenueScreen.route)
            }, onEditTimeSlotsClicked = {
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.TimeSlotsForm.route)
//                navHostController.navigate(Screen.EditVenueScreen.route)
            })
        }

        composable(route = Screen.OwnerVenueDetails.route) {
            VenueDetails(Screen.OwnerVenueDetails.title, {
                navHostController.navigate(Screen.SelectTime.route)
            }, onEditVenueClicked = {
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.EditVenueScreen.route)
            }, onEditTimeSlotsClicked = {
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.TimeSlotsForm.route)
//                navHostController.navigate(Screen.EditVenueScreen.route)
            })
        }

        composable(route = Screen.TimeSlotsForm.route) {
            TimeSlotsForm {
                navHostController.navigate(Screen.AddDaysAndHours.route)
            }
        }

        composable(route = Screen.AddDaysAndHours.route) {
            AddDaysAndHours(OnAddClicked = {
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    venueOwnerViewModel.editedVenueToFDB(it)
                    if (venueOwnerViewModel.isVenueEdited.value == true) {
                        Toast.makeText(
                            context,
                            "Venue has been edited",
                            Toast.LENGTH_LONG
                        ).show()
                        navHostController.navigate(Screen.OwnerVenues.route)
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to update the venue",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }, OnCancelClicked = {
                navHostController.navigateUp()
            })
        }

        composable(route = Screen.OwnerVenues.route) {
            OwnerVenues({
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.EditVenueScreen.route)
            }, {
                venueOwnerViewModel.venueToBeEdit = it
                navHostController.navigate(Screen.OwnerVenueDetails.route)
            })
        }

        composable(route = Screen.AddVenueScreen.route) {
            AddVenueScreen(
                Screen.AddVenueScreen.title,
                scaffoldState,
                activity
            ) { type, name, contactNumber, price,
                chosenFacilities, size, street, city, lat, long ->
                if (sportCommunityViewModel.currentUser?.email != ""
                    && sportCommunityViewModel.currentUser?.email != null
                ) {
                    val venue = Venue(
                        type = type,
                        name = name,
                        city = "$city",
                        street = "$street",
                        location = listOf(lat, long),
                        contactNumber = contactNumber,
                        price = price.toDouble(),
                        facilities = chosenFacilities,
                        size = size,
                        images = mutableListOf<String>(),
                        ownerUser = sportCommunityViewModel.currentUser?.email ?: ""
                    )
                    sportCommunityViewModel.venueToBeAdded = venue
                    navHostController.navigate(Screen.ImagePickerScreen.route)
                } else {
                    Toast.makeText(
                        context,
                        "Error in Adding new Venue, please re-login and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        composable(route = Screen.EditVenueScreen.route) {
            AddVenueScreen(
                Screen.EditVenueScreen.title,
                scaffoldState, activity
            ) { type, name, contactNumber, price,
                chosenFacilities, size, street, city, lat, long ->
                if (venueOwnerViewModel.ownerEmail.value != ""
                    && venueOwnerViewModel.ownerEmail.value != null
                ) {
                    val venue = Venue(
                        type = type,
                        name = name,
                        city = "$city",
                        street = "$street",
                        location = listOf(lat, long),
                        contactNumber = contactNumber,
                        price = price.toDouble(),
                        facilities = chosenFacilities,
                        size = size,
                        images = mutableListOf<String>(),
                        ownerUser = sportCommunityViewModel.currentUser?.email ?: ""
                    )
                    venueOwnerViewModel.venueToBeEdit = venue
                    navHostController.navigate(Screen.ImagesEdit.route)
                } else {
                    Toast.makeText(
                        context,
                        "Error in Adding new Venue, please re-login and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        composable(route = Screen.ImagePickerScreen.route) {
            ImagePicker(Screen.ImagePickerScreen.title) { imagesUris ->
                Log.d("TAG", "size: ${imagesUris.size} ")
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    Log.d("TAG", "size: ${imagesUris.size} ")

                    imagesUris.forEach {
                        Log.d("TAG", "uri: $it, outside ")
                        if (it != null) {
                            sportCommunityViewModel.addImageToFDB(it)
                            Log.d("TAG", "uri: $it, inside")
                        }
                    }

                    sportCommunityViewModel.venueToBeAdded.images?.forEach { image ->
                        Log.d("TAG", "beforeDB: $image ")
                    }
                    sportCommunityViewModel.addVenueToFDB(sportCommunityViewModel.venueToBeAdded)

                    Toast.makeText(
                        context,
                        "Venue has been added successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    navHostController.navigate(Screen.OwnerVenues.route)
                }
            }
        }

        composable(route = Screen.ImagesEdit.route) {
            ImagePicker(Screen.ImagesEdit.title) { imagesUris ->
                Log.d("TAG", "size: ${imagesUris.size} ")
                val coroutineScope = CoroutineScope(Dispatchers.Main)
                coroutineScope.launch {
                    Log.d("TAG", "size: ${imagesUris.size} ")

                    imagesUris.forEach {
                        Log.d("TAG", "uri: $it, outside ")
                        if (it != null) {
                            venueOwnerViewModel.addImageToFDB(it)
                            Log.d("TAG", "uri: $it, inside")
                        }
                    }

                    venueOwnerViewModel.venueToBeEdit.images?.forEach { image ->
                        Log.d("TAG", "beforeDB: $image ")
                    }
                    venueOwnerViewModel.editedVenueToFDB(venueOwnerViewModel.venueToBeEdit)

                    Toast.makeText(
                        context,
                        "Venue has been edited successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    navHostController.navigate(Screen.OwnerVenues.route)
                }
            }
        }

        composable(route = Screen.CreateEvent.route) {
            CreateEvent(onBackArrowClicked =
            {
                navHostController.popBackStack() // same as navigateup
            },
                onaddEvent = {
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    coroutineScope.launch {
                        eventViewModel.addEvent(it)
                    }
                }
            )
        }

        composable(route = Screen.EventList.route) {
            EventList(navController = navHostController) {
                navHostController.navigate(Screen.CreateEvent.route)
            }

        }

        composable(route = Screen.EventDetails.route) {
            EventDetails(navController = navHostController)
        }

        composable(route = Screen.ProfileScreen.route) {
            onEdit(
                onBackClicked =
                {
                    navHostController.navigate(Screen.Home.route)

                },
                onSaveClicked = {
                    sportCommunityViewModel.editProfile(it)
                    navHostController.navigate(Screen.Home.route)

                }
            )
        }

        composable(route = Screen.CreateTeam.route) {
            CreateTeam(onBackArrowClicked =
            {
                navHostController.popBackStack() // same as navigateup
            },
                onaddTeam = {
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    coroutineScope.launch {
                        teamViewModel.addTeams(it)
                    }
                }
            )
        }

        composable(route = Screen.Teams.route) {
            TeamsList(
                onAddTeam = {
                    navHostController.navigate(Screen.CreateTeam.route)
                }
            )
        }

        composable(route = Screen.SelectTime.route) {
            SelectTime { time, date, venue, index ->
                Log.d("TAG", "AppNavHost: appNav")

                var booking = sportCommunityViewModel.currentUser?.email?.let { email ->
                    Booking(
                        venueId = venue.venueId,
                        userId = email,
                        date = date,
                        hour = time
                    )
                }

                if (booking != null) {
                    val coroutineScope = CoroutineScope(Dispatchers.Main)
                    val coroutineScope2 = CoroutineScope(Dispatchers.Main)

                    coroutineScope2.launch {
                        venueOwnerViewModel.editedVenueToFDB(venue)
                    }
                    coroutineScope.launch {
                        sportCommunityViewModel.addBookingToFDB(booking)

                        if (sportCommunityViewModel.isBookingAdded.value == true) {
                            Toast.makeText(
                                context,
                                "Booked the venue successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            navHostController.navigate(Screen.VenuesList.route)
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to book the venue",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

//        composable(route = Screen.EditProfile.route) {
//            onEdit(
//                onback = {
//                    navHostController.navigate(Screen.ProfileScreen.route)
//                }
//            )
//        }

        composable(route = Screen.MyFriend.route) {
            MyFriend(
                onAdd = {
                    navHostController.navigate(Screen.AddFriend.route)
                },
                onReload = {
                    navHostController.navigate(Screen.MyFriend.route)
                },
                onUserProfile = {
                    sportCommunityViewModel.otherUser = it
                    navHostController.navigate(Screen.UserProfile.route)
                }

            )
        }
        composable(route = Screen.UserProfile.route) {
            UserProfile()
        }
        composable(route = Screen.AddFriend.route) {
            AddFriend(
                onReload = {
                    navHostController.navigate(Screen.AddFriend.route)
                }
//                ,
//                OnaddFriend = {
//                    sportCommunityViewModel.addFriend(it)
//                }
            )
        }
        composable(route = Screen.Request.route) {
            Requests(
                onReload = {
                    navHostController.navigate(Screen.Request.route)
                }
            )
        }

        composable(route = Screen.Payment.route) {
            Payment()
        }


        composable(route = Screen.MyBooking.route) {
            MyBooking()
        }
    }
}

