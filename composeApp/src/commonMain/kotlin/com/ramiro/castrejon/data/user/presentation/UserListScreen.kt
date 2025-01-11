package com.ramiro.castrejon.data.user.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ramiro.castrejon.app.Route
import com.ramiro.castrejon.core.presentation.PulseAnimation

import com.ramiro.castrejon.domain.User
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import virginmoneyapp.composeapp.generated.resources.Res
import virginmoneyapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun UserListScreenRoot(
    viewModel: UserListViewModel = koinViewModel(),
    navController: NavController
) {

    val state by viewModel.users.collectAsStateWithLifecycle()

    UserListScreen(state = state, navController = navController, viewModel = viewModel)

}

@Composable
fun UserListScreen(
    state: UserListState, navController: NavController, viewModel: UserListViewModel
) {

    Column(
        modifier = Modifier
            .background(Color.Black)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
            items(state.searchResults) { results ->

                UserCard(results.firstName, results.lastName, results.avatar, onClick = {
                    viewModel.selectedUser.value = results
                    navController.navigate(Route.UserDetail)
                })


            }
        }

    }
}

@Composable
fun UserCard(
    firstName: String,
    lastName: String,
    avatar: String,
    onClick: () -> Unit

) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }
    val painter = rememberAsyncImagePainter(
        model = avatar,
        onSuccess = {
            imageLoadResult =
                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                    Result.success(it.painter)
                } else {
                    Result.failure(Exception("Invalid image size"))
                }
        },
        onError = {
            it.result.throwable.printStackTrace()
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Card(
        modifier = Modifier.fillMaxWidth().padding(16.dp).clickable { onClick() },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if (painterState is AsyncImagePainter.State.Success) {
                    1f
                } else {
                    0f
                },
                animationSpec = tween(durationMillis = 800)
            )

            when (val result = imageLoadResult) {
                null -> PulseAnimation(
                    modifier = Modifier.size(60.dp)
                )

                else -> {
                    Image(
                        painter = if (result.isSuccess) painter else {
                            painterResource(Res.drawable.compose_multiplatform)
                        },
                        contentDescription = "$firstName $lastName",
                        contentScale = if (result.isSuccess) {
                            ContentScale.Crop
                        } else {
                            ContentScale.Fit
                        },
                        modifier = Modifier.size(150.dp)
                            .aspectRatio(
                                ratio = 0.85f,
                                matchHeightConstraintsFirst = true
                            )
                            .graphicsLayer {
                                rotationX = (1f - transition) * 30f
                                val scale = 0.8f + (0.2f * transition)
                                scaleX = scale
                                scaleY = scale
                            }
                    )
                }
            }
            Column {
                Text(text = firstName, style = MaterialTheme.typography.h6)
                Text(text = lastName, style = MaterialTheme.typography.h6)
            }
        }
    }
}

@Composable
fun DetailUserCard(
    user: User, onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp).clickable { onBackClick() },


        ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }
        val painter = rememberAsyncImagePainter(
            model = user.avatar,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
            },
            onError = {
                it.result.throwable.printStackTrace()
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if (painterState is AsyncImagePainter.State.Success) {
                    1f
                } else {
                    0f
                },
                animationSpec = tween(durationMillis = 800)
            )

            when (val result = imageLoadResult) {
                null -> PulseAnimation(
                    modifier = Modifier.size(60.dp)
                )

                else -> {
                    Image(
                        painter = if (result.isSuccess) painter else {
                            painterResource(Res.drawable.compose_multiplatform)
                        },
                        contentDescription = "$user.firstName $user.lastName",
                        contentScale = if (result.isSuccess) {
                            ContentScale.Crop
                        } else {
                            ContentScale.Fit
                        },
                        modifier = Modifier.size(350.dp).padding(20.dp)
                            .aspectRatio(
                                ratio = 0.85f,
                                matchHeightConstraintsFirst = true
                            )
                            .graphicsLayer {
                                rotationX = (1f - transition) * 30f
                                val scale = 0.8f + (0.2f * transition)
                                scaleX = scale
                                scaleY = scale
                            }
                    )
                }
            }

            Text(
                text = "First Name: ${user.firstName}",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Left
            )

            Text(
                text = "Last Name: ${user.lastName}",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Left
            )

            Text(
                text = "Title: ${user.jobtitle}",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Left
            )

        }
    }

}