package com.magni.game2048

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.magni.game2048.feature.game.presentation.AndroidGameViewModel
import com.magni.game2048.feature.game.presentation.GameBoard
import org.koin.compose.viewmodel.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val gameViewModel: AndroidGameViewModel = koinViewModel()
            My2048Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gameState by gameViewModel.gameState.collectAsState()
                    val animations by gameViewModel.animations.collectAsState()
                    val gameColors = provideGameColors()
                    val textStyle = MaterialTheme.typography.titleLarge

                    if (gameState != null) {
                        GameBoard(
                            grid = gameState!!.grid,
                            animations = animations,
                            onSwipe = { direction ->
                                gameViewModel.makeMove(direction)
                            },
                            gameColors = gameColors,
                            textStyle = textStyle,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}