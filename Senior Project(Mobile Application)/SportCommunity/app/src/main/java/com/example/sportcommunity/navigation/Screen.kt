package com.example.sportcommunity.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen(val route : String, val title : String, val icon: ImageVector){
    object  Splash : Screen(route = "Splash", title = "Splash Screen", icon = Icons.Outlined.Home)
    object Home : Screen(route = "home", title = "Home", icon = Icons.Outlined.Home)
    object SignUp : Screen(route = "signUp", title = "sign Up", icon = Icons.Outlined.Person)
    object SignIn : Screen(route = "logIn", title = "Log In", icon = Icons.Outlined.Login)
    object VenuesList : Screen(route = "venuesList", title = "Venues List", icon = Icons.Outlined.Business)
    object VenueDetails : Screen(route = "venueDetails", title = "Venue Details", icon = Icons.Outlined.Business)
    object EventList : Screen(route = "EventList", title = "Event list", icon = Icons.Outlined.Event)
    object CreateEvent : Screen(route = "CreateEvent", title = "Create Event", icon = Icons.Outlined.Event)
    object ProfileScreen : Screen(route = "profile", title = "Profile", icon = Icons.Outlined.Person)
    object ChallengeScreen : Screen(route = "challengeScreen", title = "Challenge", icon = Icons.Outlined.PersonAdd)
    object AddVenueScreen : Screen(route = "addVenueScreen", title = "Add Venue", icon = Icons.Outlined.Add)
    object EditVenueScreen : Screen(route = "EditVenueScreen", title = "Edit Venue", icon = Icons.Outlined.Edit)
    object ImagePickerScreen : Screen(route = "ImagePickerScreen", title = "Choosing Image", icon = Icons.Outlined.Image)
    object SignOut : Screen(route = "SignOut", title = "Sign out", icon = Icons.Outlined.Logout)
    object UserProfile : Screen(route = "UserProfile", title = "User Profile", icon = Icons.Outlined.Info)
    object EditProfile : Screen(route = "EditProfile", title = "EditProfile", icon = Icons.Outlined.Info)
    object SelectTime : Screen(route = "SelectTime", title = "Select Time", icon = Icons.Outlined.Timer)
    object Teams : Screen(route = "Team", title = "Team", icon = Icons.Outlined.People)
    object CreateTeam : Screen(route = "CreateTeam", title = "Create Team", icon = Icons.Outlined.People)
    object AddFriend : Screen(route = "AddFriend", title = "Add Friend", icon = Icons.Outlined.People)
    object MyFriend : Screen(route = "MyFriend", title = "My Friends", icon = Icons.Outlined.People)
    object Request : Screen(route = "Request", title = "Request", icon = Icons.Outlined.Info)
    object EventDetails : Screen(route = " EventDetails", title = "EventDetails", icon = Icons.Outlined.Info)
    object Payment : Screen(route = " Payment", title = "Payment", icon = Icons.Outlined.Payment)
    object OwnerVenues : Screen(route = " OwnerVenues", title = "Owner Venues", icon = Icons.Outlined.Business)
    object OwnerVenueDetails : Screen(route = " OwnerVenueDetails", title = "Owner Venue Details", icon = Icons.Outlined.Business)
    object ImagesEdit : Screen(route = " ImagesEdit", title = "Images Edit", icon = Icons.Outlined.Image)
    object TimeSlotsForm : Screen(route = " TimeSlotsForm", title = "Time Slots Form", icon = Icons.Outlined.Schedule)
    object AddDaysAndHours : Screen(route = " AddDaysAndHours", title = "Add Days And Hours", icon = Icons.Outlined.Schedule)
    object OwnerHome : Screen(route = " OwnerHome", title = "Home", icon = Icons.Outlined.Home)
    object MyBooking : Screen(route = "MyBooking", title = "My Booking", icon = Icons.Outlined.Bookmark)
}
