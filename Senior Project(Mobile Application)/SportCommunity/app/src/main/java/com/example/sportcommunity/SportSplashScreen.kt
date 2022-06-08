package com.example.sportcommunity

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sportcommunity.model.User

import com.example.sportcommunity.navigation.Screen
import com.example.sportcommunity.reposotiry.SportCommunityRepository
import com.example.sportcommunity.viewmodel.SportCommunityViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SportSplashScreen(navController: NavController) {
    val context = LocalContext.current
    val sportCommunityRepo = SportCommunityRepository(context = context)

    val sportCommunityViewModel =
        viewModel<SportCommunityViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val coroutineScope = CoroutineScope(Dispatchers.Main)
    var user = User()


    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true ){
        scale.animateTo(targetValue = 0.9f,
                       animationSpec = tween(durationMillis = 800,
                                            easing = {
                                                OvershootInterpolator(8f)
                                                    .getInterpolation(it)
                                            }))
        delay(2000L)
//         after animation
        //    if there is  user logged in  if so take him to home screen else take him to login screen
//        if (sportCommunityViewModel.currentUser?.email.isNullOrEmpty()){
        if (FirebaseAuth.getInstance().currentUser == null){
            navController.navigate(Screen.SignIn.route){
                popUpTo("Splash"){inclusive=true}

            }
        }else {
            sportCommunityViewModel.currentUser = FirebaseAuth.getInstance().currentUser?.email?.let {
                sportCommunityRepo.getUser(
                    it
                )
            }
            navController.navigate(Screen.Home.route)
        }




    }

    Surface(modifier = Modifier
        .fillMaxSize()
//        .padding(20.dp)
        .size(330.dp)
        .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp,
            color = Color.LightGray)
    ) {

        Column(
            modifier = Modifier.padding(1.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
              ) {
            Text(text = "Sport Community",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                color =
                MaterialTheme.colors.primarySurface
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "\"Book. Play. Enjoy \"",
                style = MaterialTheme.typography.h5,
                color = Color.Gray)
        }


    }

}


