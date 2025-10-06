package com.magni.game2048.feature.history.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.Difficulty
import com.magni.game2048.core.domain.entity.GameRecord
import kotlinx.coroutines.launch

@Composable
fun RecordsContent(
    records: List<GameRecord>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val difficulties = Difficulty.entries.toTypedArray()
    val scope = rememberCoroutineScope()

    if (records.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No records yet. Play some games!",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        val recordsByDifficulty = records.groupBy { it.difficulty }

        Column(modifier = modifier.fillMaxSize()) {
            // Tab indicator
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                difficulties.forEachIndexed { index, difficulty ->
                    Tab(
                        text = {
                            Text(
                                when (difficulty) {
                                    Difficulty.EASY -> "Easy"
                                    Difficulty.MEDIUM -> "Medium"
                                    Difficulty.HARD -> "Hard"
                                }
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            // Animate to the selected page
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page ->
                val difficulty = difficulties[page]
                val difficultyRecords = recordsByDifficulty[difficulty] ?: emptyList()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    if (difficultyRecords.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No records for this difficulty")
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(difficultyRecords.sortedByDescending { it.score }) { record ->
                                RecordItem(
                                    record = record,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
