package com.ramiro.castrejon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.ramiro.castrejon.app.Route
import com.ramiro.castrejon.data.room.presentation.RoomListScreenRoot
import com.ramiro.castrejon.data.room.presentation.RoomListViewModel
import com.ramiro.castrejon.data.user.presentation.DetailUserCard
import com.ramiro.castrejon.data.user.presentation.UserListScreenRoot
import com.ramiro.castrejon.data.user.presentation.UserListViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

import virginmoneyapp.composeapp.generated.resources.Res
import virginmoneyapp.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        var showContent by remember { mutableStateOf(false) }
        val viewModel = koinViewModel<UserListViewModel>()
        val roomViewModel = koinViewModel<RoomListViewModel>()

        NavHost(
            navController = navController,
            startDestination = Route.AppGraph
        ) {
            navigation<Route.AppGraph>(startDestination = Route.UserList){
                composable<Route.UserList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    Box{
                        Column {
//
                            UserListScreenRoot(viewModel, navController = navController)
                        }

                    }

                }
                composable<Route.UserDetail>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val selectedUser = viewModel.selectedUser.value

                    DetailUserCard(
                        user = selectedUser,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Route.RoomList>(
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val roomList = roomViewModel.rooms.value
                    println(roomList)
                    RoomListScreenRoot(roomViewModel,navController)
                }

            }

        }



    }
}