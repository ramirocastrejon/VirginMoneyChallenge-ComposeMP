package com.ramiro.castrejon.data.room.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ramiro.castrejon.app.Route
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomListScreenRoot(
    viewModel: RoomListViewModel = koinViewModel(),
    navController: NavController
) {
    val state by viewModel.rooms.collectAsStateWithLifecycle()
    RoomListScreen(state = state, navController = navController, viewModel = viewModel)
}

@Composable
fun RoomListScreen(
    state: RoomListState, navController: NavController, viewModel: RoomListViewModel
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(Route.UserList) }) {
            Text(text = "Users")
        }
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp),
            content = {
                items(state.roomResults) { index ->
                    Card(
                        backgroundColor = Color.White,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth().height(175.dp).align(Alignment.Start),
                        elevation = 8.dp,
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "Number: ${index.id}",
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                textAlign = TextAlign.Left,

                                )
                            Text(
                                text = "Occupied: ${index.isOccupied}",
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                textAlign = TextAlign.Left,

                                )
                            Text(
                                text = "Max Occupancy: ${index.maxOccupancy}",
                                style = MaterialTheme.typography.body1,
                                color = Color.Black,
                                textAlign = TextAlign.Left,

                                )
                        }


                    }
                }
            }
        )

    }

}