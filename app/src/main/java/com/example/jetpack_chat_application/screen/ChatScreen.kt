package com.example.jetpack_chat_application.screen

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetpack_chat_application.launcher.rememberGetContentActivityResult
import com.example.jetpack_chat_application.model.MessageModel
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

    val getContent = rememberGetContentActivityResult()

    val imageUri = viewModel.photoAttachmentUri.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        LazyColumn(
            modifier = Modifier.weight(1F)
        ){
            items(viewModel.chatMessage){ item: MessageModel ->
                MessageCompose(item = item)
            }
        }

        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.background(color = Color.LightGray)
        ) {
            getContent.uri?.let {
                viewModel._photoAttachmentUri.value = it
                getContent.uri = null
            }
            Image(
                modifier = Modifier
                    .size(if (imageUri.value!=null) 100.dp else 0.dp)
                    .padding(all = 5.dp),
                contentDescription = "UserImage",
                contentScale = ContentScale.FillBounds,
                painter = rememberAsyncImagePainter(
                    model =  ImageRequest.Builder(LocalContext.current)
                        .data(imageUri.value)
                        .size(Size.ORIGINAL)
                        .build()
                )
            )
            Row(
                modifier = Modifier.padding(all = 5.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value =msg.value ,
                    onValueChange ={msg.value = it},
                    trailingIcon = {
                        IconButton(onClick = {
                            getContent.launch("image/*")
                        }) {
                            Icon(imageVector = Icons.Filled.Image, contentDescription ="Image" , tint = Color.Black )
                        }
                    }
                )
                IconButton(
                    onClick = {
                        if (!key.isNullOrEmpty()){
                            with(viewModel){
                                when{
                                    _photoAttachmentUri.value != null ->{
                                        uploadImage(key!!)
                                    }
                                    else ->{
                                        sendMessage(key!!,msg.value)
                                    }
                                }
                            }

                            msg.value = ""
                        }else{
                            Log.i(TAG,"key is null or empty")
                        }
                    }) {
                    Icon(Icons.Filled.Send, contentDescription = "Send" , tint = Color.Black)
                }
            }
        }

    }
}

@Composable
fun MessageCompose(item : MessageModel) {
    if (!item.imageUrl.isNullOrEmpty()){
        Image(
            modifier = Modifier
                .size(100.dp)
                .padding(all = 5.dp),
            contentDescription = "UserImage",
            contentScale = ContentScale.FillBounds,
            painter = rememberAsyncImagePainter(
                model =  ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .size(Size.ORIGINAL)
                    .build()
            )
        )
    }else if(!item.msg.isNullOrEmpty()){
        Text(
            text = item.msg,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(all = 5.dp)
                .background(color = Color.Gray)
                .padding(all = 5.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(5.dp))
                .clipToBounds()
        )
    }
}