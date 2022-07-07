package com.example.jetpack_chat_application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.jetpack_chat_application.navigation.MainChatNavHost
import com.example.jetpack_chat_application.ui.theme.Jetpack_chat_applicationTheme
import com.example.jetpack_chat_application.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainChatActivity : ComponentActivity() {
    val viewModel : ChatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Jetpack_chat_applicationTheme {
                val navController = rememberNavController()
                MainChatNavHost(
                    navHostController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
