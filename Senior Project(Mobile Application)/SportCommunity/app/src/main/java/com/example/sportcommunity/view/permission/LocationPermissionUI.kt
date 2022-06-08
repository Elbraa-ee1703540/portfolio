package com.example.sportcommunity.view.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch
import com.example.sportcommunity.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.sportcommunity.viewmodel.PermissionViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


private const val TAG = "PermissionTestUI"

@Composable
fun LocationPermissionUI(scaffoldState: ScaffoldState, permissionViewModel: PermissionViewModel, activity: Activity) {


    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val performLocationAction by permissionViewModel.performLocationAction.collectAsState()

    var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)



    if (performLocationAction) {
        Log.d(TAG, "Invoking Permission UI")

        PermissionUI(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION,
            stringResource(id = R.string.permission_location_rationale),
            scaffoldState
        ) { permissionAction ->
            when (permissionAction) {
                is PermissionAction.OnPermissionGranted -> {
                    permissionViewModel.setPerformLocationAction(false)
                    //Todo: do something now as we have location permission
                    val task = fusedLocationProviderClient.lastLocation

                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                    }
                    task.addOnSuccessListener {
                        if (it != null){
                            Toast.makeText(activity, "${it.longitude}, ${it.longitude}", Toast.LENGTH_LONG).show()
                            Log.d(TAG, "Location has been granted, ${it.longitude}, ${it.longitude}")

                        }
                    }
                    Log.d(TAG, "Location has been granted")
//                    scope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar("Location permission granted!")
//                    }
                }
                is PermissionAction.OnPermissionDenied -> {
                    permissionViewModel.setPerformLocationAction(false)
                }
            }
        }
    }


    Button(
        onClick = { permissionViewModel.setPerformLocationAction(true) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp)
    ) {
        Text("Capture Location")

    }


}