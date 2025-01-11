package com.ramiro.castrejon.data.room.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomListScreenRoot(
    viewModel: RoomListViewModel = koinViewModel(),
    navController: NavController
){
    val state by viewModel.rooms.collectAsStateWithLifecycle()
    RoomListScreen(state = state, navController = navController, viewModel = viewModel)
}

@Composable
fun RoomListScreen(state: RoomListState, navController : NavController, viewModel: RoomListViewModel
){
    Column(
        modifier = Modifier
            .background(Color.White)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(state.roomResults){results ->
                Row (modifier = Modifier.fillMaxWidth()){
                    Text(text = results.id)
                }

            }

        }
    }
}