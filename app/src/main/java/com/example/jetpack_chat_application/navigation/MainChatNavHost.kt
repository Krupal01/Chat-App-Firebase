package com.example.jetpack_chat_application.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jetpack_chat_application.screen.ChatListScreen
import com.example.jetpack_chat_application.screen.ChatScreen
import com.example.jetpack_chat_application.viewmodel.ChatViewModel

@Composable
fun MainChatNavHost(
    navHostController: NavHostController,
    viewModel: ChatViewModel
) {

    NavHost(
        navController = navHostController,
        startDestination = ChatNavigationRoutes.ChatListScreen.route
    ){
        composable(
            route = ChatNavigationRoutes.ChatListScreen.route
        ){
            ChatListScreen(navController = navHostController, viewModel = viewModel)
        }

        composable(
            route = "${ChatNavigationRoutes.ChatScreen.route}/{user2}",
            arguments = listOf(
                navArgument("user2") { type = NavType.StringType }
            )
        ){
            ChatScreen(
                navController = navHostController,
                chatWith = it.arguments?.getString("user2") ?: "",
                viewModel = viewModel
            )
        }

    }

}