package com.magni.game2048.feature.game.common

sealed class Screen {
    object Game : Screen()
    object Settings : Screen()
    object Records : Screen()
}
