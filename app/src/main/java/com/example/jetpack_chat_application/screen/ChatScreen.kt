package com.example.jetpack_chat_application.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jetpack_chat_application.utils.TAG
import com.example.jetpack_chat_application.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    navController: NavController,
    chatWith : String,
    viewModel: ChatViewModel
) {
    viewModel.getKey(chatWith)
    val key by viewModel.key.observeAsState()
    val msg = remember {
        mutableStateOf("")
    }
    key?.let {
        viewModel.getMessage(it)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(){
            items(viewModel.chatMessage){ item: String ->
                Text(text = item)
            }
        }
        TextField(value =msg.value , onValueChange ={msg.value = it} )
        Button(onClick = {
            if (!key.isNullOrEmpty()){
                viewModel.sendMessage(key!!,msg.value)
            }else{
                Log.i(TAG,"key is null or empty")
            }
        }) {
            Text(text = "Send")
        }

    }


}