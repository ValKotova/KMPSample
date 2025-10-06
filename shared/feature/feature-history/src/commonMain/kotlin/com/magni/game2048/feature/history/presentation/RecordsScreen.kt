package com.magni.game2048.feature.history.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.presentation.LocalNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsScreen(
    recordsStateHolder: RecordsStateHolder,
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val records by recordsStateHolder.records.collectAsState()
    val pagerState = rememberPagerState(pageCount = { Difficulty.entries.size })

    Scaffold(
        topBar = {
            RecordsTopBar(
                navController
            )
        }
    ) { innerPadding ->
        RecordsContent(
            records = records,
            pagerState = pagerState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}