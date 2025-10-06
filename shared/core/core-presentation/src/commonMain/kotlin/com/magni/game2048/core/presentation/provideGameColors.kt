package com.magni.game2048.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun provideGameColors(): GameColors {
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()

    // Define tile colors for both light and dark themes
    val tileColors = mapOf(
        1 to if (isDarkTheme) Color(0xFF454545) else Color(0xFFEEE4DA),
        2 to if (isDarkTheme) Color(0xFF454545) else Color(0xFFEEE4DA),
        4 to if (isDarkTheme) Color(0xFF404040) else Color(0xFFEDE0C8),
        8 to if (isDarkTheme) Color(0xFF4A3C2A) else Color(0xFFF2B179),
        16 to if (isDarkTheme) Color(0xFF4A3420) else Color(0xFFF59563),
        32 to if (isDarkTheme) Color(0xFF4A2C1F) else Color(0xFFF67C5F),
        64 to if (isDarkTheme) Color(0xFF4A241B) else Color(0xFFF65E3B),
        128 to if (isDarkTheme) Color(0xFF454025) else Color(0xFFEDCF72),
        256 to if (isDarkTheme) Color(0xFF45401F) else Color(0xFFEDCC61),
        512 to if (isDarkTheme) Color(0xFF45401A) else Color(0xFFEDC850),
        1024 to if (isDarkTheme) Color(0xFF454015) else Color(0xFFEDC53F),
        2048 to if (isDarkTheme) Color(0xFF454010) else Color(0xFFEDC22E)
    )

    // Define text colors for both light and dark themes
    val textColors = mapOf(
        2 to if (isDarkTheme) Color.White else Color(0xFF776E65),
        2 to if (isDarkTheme) Color.White else Color(0xFF776E65),
        4 to if (isDarkTheme) Color.White else Color(0xFF776E65),
        8 to Color.White,
        16 to Color.White,
        32 to Color.White,
        64 to Color.White,
        128 to Color.White,
        256 to Color.White,
        512 to Color.White,
        1024 to Color.White,
        2048 to Color.White
    )

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
        gridLine = colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        tileColors = tileColors,
        textColors = textColors
    )
}