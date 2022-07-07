package com.example.jetpack_chat_application

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.example.jetpack_chat_application.model.MainUserModel
import com.example.jetpack_chat_application.screen.LoginScreen
import com.example.jetpack_chat_application.ui.theme.Jetpack_chat_applicationTheme
import com.example.jetpack_chat_application.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewmodel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.user.observe(
            this
        ) { user ->
            if (!user.email.isNullOrEmpty() && !user.password.isNullOrEmpty()) {
                startActivity(Intent(this, MainChatActivity::class.java))
                finish()
            }
        }
        setContent {
            Jetpack_chat_applicationTheme {
                LoginScreen(viewModel = viewmodel)
            }
        }
    }
}

