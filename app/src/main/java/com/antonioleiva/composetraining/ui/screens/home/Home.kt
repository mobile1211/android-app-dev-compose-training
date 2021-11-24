package com.antonioleiva.composetraining.ui.screens.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.antonioleiva.composetraining.R
import com.antonioleiva.composetraining.model.Item
import com.antonioleiva.composetraining.model.itemList
import com.antonioleiva.composetraining.ui.screens.Screen

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Home(onItemClick: (Item) -> Unit, viewModel: HomeViewModel = viewModel()) {
    Home(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onMessageRemoved = viewModel::onMessageRemoved,
        onItemClick = onItemClick
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Home(
    state: HomeViewModel.UiState,
    onAction: (Action, Int) -> Unit,
    onMessageRemoved: () -> Unit,
    onItemClick: (Item) -> Unit
) {
    val homeState = rememberHomeState()

    if (state.message != null) {
        LaunchedEffect(state.message) {
            homeState.scaffoldState
                .snackbarHostState
                .showSnackbar(state.message)
            onMessageRemoved()
        }
    }

    Scaffold(
        modifier = Modifier.semantics { contentDescription = "Home" },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = { homeState.gridMode.value = !homeState.gridMode.value }) {
                        Crossfade(targetState = homeState.gridMode.value) { state ->
                            if (state) {
                                Icon(
                                    imageVector = Icons.Default.ViewList,
                                    contentDescription = stringResource(R.string.change_view)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.GridView,
                                    contentDescription = stringResource(R.string.change_view)
                                )
                            }
                        }
                    }
                }
            )
        },
        scaffoldState = homeState.scaffoldState
    ) { padding ->
        Crossfade(targetState = homeState.gridMode.value) { gridMode ->
            if (gridMode) {
                HomeGrid(
                    items = state.items,
                    onItemClick = onItemClick,
                    onAction = onAction,
                    modifier = Modifier.padding(padding)
                )
            } else {
                HomeList(
                    items = state.items,
                    onItemClick = onItemClick,
                    onAction = onAction,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun HomePreview() {
    Screen {
        Home(
            state = HomeViewModel.UiState(itemList),
            onAction = { _, _ -> },
            onMessageRemoved = {},
            onItemClick = {}
        )
    }
}