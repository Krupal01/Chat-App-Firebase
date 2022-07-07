package com.example.jetpack_chat_application.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.jetpack_chat_application.navigation.ChatNavigationRoutes
import com.example.jetpack_chat_application.utils.TAG
import com.example.jetpack_chat_application.viewmodel.ChatViewModel
import com.google.firebase.database.*

@Composable
fun ChatListScreen(
    navController: NavController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar {
                Text(text ="Firebase Chat")
            }
        }
    ) {
        LazyColumn(){
            items(viewModel.chatContact){item: String ->
                Log.i(TAG,item)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 5.dp)
                        .padding(all = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(5.dp))
                        .clickable {
                            navController.navigate("${ChatNavigationRoutes.ChatScreen.route}/$item")
                        }
                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(5.dp))
                ){
                    Text(
                        text = item,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 7.dp)
                    )
                }

            }
        }

    }

}