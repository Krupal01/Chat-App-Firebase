package com.example.jetpack_chat_application.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        LazyColumn(
            modifier = Modifier.weight(1F)
        ){
            items(viewModel.chatMessage){ item: String ->
                Text(
                    text = item,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .background(color = Color.Gray)
                        .padding(all= 5.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .clipToBounds()
                )
            }
        }
        Row(
            modifier = Modifier.padding(all = 5.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value =msg.value ,
                onValueChange ={msg.value = it}
            )
            IconButton(
                onClick = {
                if (!key.isNullOrEmpty()){
                    viewModel.sendMessage(key!!,msg.value)
                    msg.value = ""
                }else{
                    Log.i(TAG,"key is null or empty")
                }
            }) {
                Icon(Icons.Filled.Send, contentDescription = "Send" , tint = Color.Gray)
            }
        }
    }
}