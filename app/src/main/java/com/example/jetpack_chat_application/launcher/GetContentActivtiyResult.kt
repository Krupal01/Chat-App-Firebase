package com.example.jetpack_chat_application.launcher

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable

class GetContentActivityResult (
    private val launcher: ManagedActivityResultLauncher<String, Uri>,
    var uri: Uri?
) {
    fun launch(mimeType: String) {
        launcher.launch(mimeType)
    }
}

@Composable
fun rememberGetContentActivityResult(): GetContentActivityResult {
    var uri by rememberSaveable {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent(), onResult = {
        uri = it
    })
    return remember(launcher, uri) {
        GetContentActivityResult(launcher, uri)
    }
}


