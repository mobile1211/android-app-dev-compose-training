package com.antonioleiva.composetraining.ui.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalMaterialApi
@Composable
fun HomeList(viewModel: HomeViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(viewModel.state) { index, item ->
            HomeListItem(
                item = item,
                onAction = { viewModel.onAction(it, index) }
            )
            if (index < viewModel.state.size - 1) {
                Divider()
            }
        }
    }
}