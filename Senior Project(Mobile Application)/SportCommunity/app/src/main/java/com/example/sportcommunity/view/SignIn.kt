package com.example.sportcommunity.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sportcommunity.R

@ExperimentalComposeUiApi
@Composable
fun SignIn(
    onSignInClicked: (String, String) -> Unit, onSignUpTextClicked: () -> Unit
) {

    var email by remember { mutableStateOf("") } 
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(12.dp, 12.dp)
            .fillMaxHeight()
    ) {
        LoginLogo()
        LoginText(
            email,
            password,
            { email = it },
            { password = it }
        )
        LoginButton(
            email, password, onSignUpTextClicked, onSignInClicked, context
        )

    }
}


@Composable
fun LoginLogo() {
    Column {

        Image(
            painter = painterResource(R.drawable.picture), contentDescription = "Logo",
            modifier = Modifier.size(144.dp)
        )
//        Text(
//            text = "Sport Community App",
//            fontStyle = FontStyle.Italic,
//            style = MaterialTheme.typography.body1
//        )

    }
}


@ExperimentalComposeUiApi
@Composable
fun LoginText(
    email: String, password: String,
    onEmailChanged: (String) -> Unit, onPasswordChanged: (String) -> Unit
) {

    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordVisibility by remember { mutableStateOf(false) }
    Column {

        Row {

            OutlinedTextField(
                value = email,
                onValueChange = { onEmailChanged(it) },
                label = { Text("email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.focusRequester(focusRequester),
                keyboardActions = KeyboardActions(
                    onNext = { focusRequester.requestFocus() }
                )

            )
        }
        Row {
            OutlinedTextField(
                value = password,
                onValueChange = { onPasswordChanged(it) },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ), keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()

                    }
                ),
                trailingIcon = {
                    val icon = if (passwordVisibility)
                        Icons.Default.Visibility
                    else Icons.Default.VisibilityOff

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(imageVector = icon, "")
                    }
                }
            )
        }
    }
}


@Composable
fun LoginButton(
    username: String,
    password: String,
    onSignUpTextClicked: () -> Unit,
    onSignInClicked: (String, String) -> Unit, context: Context
) {
    Column() {
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    try {
                        onSignInClicked(username, password)
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
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            border = BorderStroke(1.dp, Color(0xFFA500)),
            shape = RoundedCornerShape(50)

        ) {
            Text(text = "Log in ")
        }
    }

    Text(text = "Sign Up", modifier = Modifier
        .padding(16.dp, 0.dp)
        .clickable {
            onSignUpTextClicked()
        })

}
