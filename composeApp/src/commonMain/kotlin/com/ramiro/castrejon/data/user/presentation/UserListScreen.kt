package com.ramiro.castrejon.data.user.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
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
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.ramiro.castrejon.app.Route
import com.ramiro.castrejon.core.presentation.PulseAnimation
import com.ramiro.castrejon.data.dto.UserDto
import com.ramiro.castrejon.domain.User
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import virginmoneyapp.composeapp.generated.resources.Res
import virginmoneyapp.composeapp.generated.resources.compose_multiplatform

@Composable
fun UserListScreenRoot(
    viewModel: UserListViewModel = koinViewModel(),
    navController: NavController
){

    val state by viewModel.users.collectAsStateWithLifecycle()
    val user by viewModel.selectedUser.collectAsStateWithLifecycle()
    UserListScreen(state = state, navController = navController, viewModel = viewModel)

}

@Composable
fun UserListScreen(
    state: UserListState, navController: NavController, viewModel: UserListViewModel){
    var detailUser by remember { mutableStateOf<User?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.searchResults) { results ->
                Row(modifier = Modifier.fillMaxWidth()) { //Text(text = results.email)
                UserCard(results.firstName,results.lastName,results.avatar, onClick = {
                     viewModel.selectedUser.value= results
                    navController.navigate(Route.UserDetail)
                })
                }


            }
        }
//        detailUser?.let {user ->
//            Box(modifier = Modifier.fillMaxSize()) {
//                DetailUserCard(user.firstName, user.lastName, user.avatar, user.jobtitle, "Yellow",
//                    onClick = { detailUser = null })
//            }
//        }
    }
}

@Composable
fun UserCard(
    firstName: String,
    lastName: String,
    avatar: String,
    onClick: () -> Unit

){
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
    Card(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable{onClick()},
        shape = RoundedCornerShape(8.dp)
    ) {
        Row {
            val painterState by painter.state.collectAsStateWithLifecycle()
            val transition by animateFloatAsState(
                targetValue = if(painterState is AsyncImagePainter.State.Success) {
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
                        modifier = Modifier.size(200.dp)
                            .aspectRatio(
                                ratio = 0.65f,
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
                Text(text = firstName, style = MaterialTheme.typography.body1)
                Text(text = lastName, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Composable
fun DetailUserCard(
    user: User, onBackClick: () -> Unit
){
    println("${user.firstName} ${user.lastName} ${user.jobtitle}")
    Column(modifier = Modifier.fillMaxSize().fillMaxWidth()
        .background(Color.Black.copy(alpha = 0.5f)).clickable { onBackClick() },
        verticalArrangement = Arrangement.SpaceBetween

    ){
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
        Card(modifier = Modifier.fillMaxSize().fillMaxWidth().clickable{onBackClick()},
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                val painterState by painter.state.collectAsStateWithLifecycle()
                val transition by animateFloatAsState(
                    targetValue = if(painterState is AsyncImagePainter.State.Success) {
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
                            modifier = Modifier.fillMaxWidth().padding(20.dp)
                                .aspectRatio(
                                    ratio = 0.65f,
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
                Column (modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                    Row(modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
                        Text(text = "First Name: ${user.firstName}", style = MaterialTheme.typography.body1, textAlign = TextAlign.Center)
                    }
                    Row (){
                        Text(text = "Last Name: ${user.lastName}", style = MaterialTheme.typography.body1, textAlign = TextAlign.Center)
                    }
                    Row (){
                        Text(text = "Title: ${user.jobtitle}", style = MaterialTheme.typography.body1, textAlign = TextAlign.Center)
                    }


                }
            }
        }
    }

}