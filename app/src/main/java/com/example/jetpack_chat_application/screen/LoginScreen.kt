package com.example.jetpack_chat_application.screen

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_chat_application.MainChatActivity
import com.example.jetpack_chat_application.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun LoginScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModel>()
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val passwordVisibility = remember {
        mutableStateOf(false)
    }
    var emailError by remember {
        mutableStateOf(false)
    }
    var passwordError by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value =email.value ,
            onValueChange ={
                email.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp),
            placeholder = { Text(text = "User Email")},
            label = { Text(text = "User Email")},
            isError = emailError
        )
        TextField(
            value =password.value ,
            onValueChange ={
                password.value = it
            },
            placeholder = { Text(text = "Password")},
            label = { Text(text = "Password")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisibility.value) "Hide password" else "Show password"
                IconButton(onClick = {passwordVisibility.value = !passwordVisibility.value}){
                    Icon(imageVector  = image, description)
                }
            },
            isError = passwordError
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 5.dp),
            onClick = {
                if (email.value.isNotEmpty() && password.value.isNotEmpty()){
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        email.value,
                        password.value
                    ).addOnCompleteListener {
                        viewModel.saveLoggedUser(email.value , password.value)
                    }.addOnCanceledListener {
                        Log.i("SIGN_IN","Sign in cancel" )
                    }.addOnFailureListener {
                        Log.i("SIGN IN","signing fail $it")
                    }

                }else if(email.value.isEmpty()){
                    emailError = true
                }else if (password.value.isEmpty()){
                    passwordError = true
                }
            }
        ) {
            Text(text = "Sign In")
        }
    }

}