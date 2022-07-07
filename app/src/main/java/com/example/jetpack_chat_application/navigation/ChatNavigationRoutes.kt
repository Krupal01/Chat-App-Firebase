package com.example.jetpack_chat_application.navigation

sealed class ChatNavigationRoutes(val route : String) {

    object ChatListScreen : ChatNavigationRoutes("ChatList Screen")
    object ChatScreen : ChatNavigationRoutes("Chat Screen")
}