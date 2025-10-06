package com.magni.game2048.feature.history.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun AndroidRecordsScreen(modifier: Modifier = Modifier) {
    val recordsViewModel: RecordsViewModel = koinViewModel()
    RecordsScreen(
        recordsStateHolder = recordsViewModel.recordsStateHolder,
        modifier = modifier
    )
}