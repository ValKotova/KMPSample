package com.magni.game2048.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun provideGameColors(): GameColors {
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()

    val tileColors = if (isDarkTheme) {
        mapOf(
            2 to Color(0xFFF44336),     // Bright Red
            4 to Color(0xFFE91E63),     // Pink
            8 to Color(0xFF9C27B0),     // Purple
            16 to Color(0xFF673AB7),    // Deep Purple
            32 to Color(0xFF3F51B5),    // Indigo
            64 to Color(0xFF2196F3),    // Blue
            128 to Color(0xFF03A9F4),   // Light Blue
            256 to Color(0xFF00BCD4),   // Cyan
            512 to Color(0xFF009688),   // Teal
            1024 to Color(0xFF4CAF50),  // Green
            2048 to Color(0xFF8BC34A),  // Light Green
            4096 to Color(0xFFCDDC39),  // Lime
            8192 to Color(0xFFFFEB3B),  // Yellow
            16384 to Color(0xFFFFC107), // Amber
            32768 to Color(0xFFFF9800), // Orange
            65536 to Color(0xFFFF5722)  // Deep Orange
        )
    } else {
        mapOf(
            2 to Color(0xFFD32F2F),     // Vibrant Red
            4 to Color(0xFFC2185B),     // Vibrant Pink
            8 to Color(0xFF7B1FA2),     // Vibrant Purple
            16 to Color(0xFF512DA8),    // Vibrant Deep Purple
            32 to Color(0xFF303F9F),    // Vibrant Indigo
            64 to Color(0xFF1976D2),    // Vibrant Blue
            128 to Color(0xFF0288D1),   // Vibrant Light Blue
            256 to Color(0xFF0097A7),   // Vibrant Cyan
            512 to Color(0xFF00796B),   // Vibrant Teal
            1024 to Color(0xFF388E3C),  // Vibrant Green
            2048 to Color(0xFF689F38),  // Vibrant Light Green
            4096 to Color(0xFFAFB42B),  // Vibrant Lime
            8192 to Color(0xFFFBC02D),  // Vibrant Yellow
            16384 to Color(0xFFFFA000), // Vibrant Amber
            32768 to Color(0xFFF57C00), // Vibrant Orange
            65536 to Color(0xFFE64A19)  // Vibrant Deep Orange
        )
    }

    val textColors = tileColors.mapValues { (value, tileColor) ->
        if (isDarkTheme) {
            when (value) {
                in 2..128 -> Color.White
                in 256..1024 -> Color.White
                else -> Color.Black
            }
        } else {
            when (value) {
                in 2..512 -> Color.White
                in 1024..4096 -> Color.White
                else -> Color.Black
            }
        }
    }

    return GameColors(
        background = colorScheme.background,
        surface = colorScheme.surface,
        onSurface = colorScheme.onSurface,
        primary = colorScheme.primary,
        onPrimary = colorScheme.onPrimary,
        secondary = colorScheme.secondary,
        onSecondary = colorScheme.onSecondary,
        surfaceVariant = colorScheme.surfaceVariant,
        onSurfaceVariant = colorScheme.onSurfaceVariant,
        gridLine = colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
        tileColors = tileColors,
        textColors = textColors
    )
}

fun Color.lighten(factor: Float): Color {
    return Color(
        red = (red + (1f - red) * factor).coerceIn(0f, 1f),
        green = (green + (1f - green) * factor).coerceIn(0f, 1f),
        blue = (blue + (1f - blue) * factor).coerceIn(0f, 1f),
        alpha = alpha
    )
}

fun Color.darken(factor: Float): Color {
    return Color(
        red = (red * (1f - factor)).coerceIn(0f, 1f),
        green = (green * (1f - factor)).coerceIn(0f, 1f),
        blue = (blue * (1f - factor)).coerceIn(0f, 1f),
        alpha = alpha
    )
}