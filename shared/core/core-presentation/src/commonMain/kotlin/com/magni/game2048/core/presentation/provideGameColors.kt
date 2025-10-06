package com.magni.game2048.core.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun provideGameColors(): GameColors {
    val colorScheme = MaterialTheme.colorScheme
    val isDarkTheme = isSystemInDarkTheme()

    // Enhanced tile colors with better contrast and spectrum
    val tileColors = if (isDarkTheme) {
        // Dark theme - brighter tiles for better contrast
        mapOf(
            1 to Color(0xFF3A3A3A),
            2 to Color(0xFF404040),
            4 to Color(0xFF4A3726),
            8 to Color(0xFF4D2E1A),
            16 to Color(0xFF52321A),
            32 to Color(0xFF5A2C15),
            64 to Color(0xFF5D3A1F),
            128 to Color(0xFF5D4520),
            256 to Color(0xFF5D4D21),
            512 to Color(0xFF5D5522),
            1024 to Color(0xFF5D5D23),
            2048 to Color(0xFF4A5D23),
            4096 to Color(0xFF375D23),
            8192 to Color(0xFF235D4A),
            16384 to Color(0xFF235D5D),
            32768 to Color(0xFF234A5D),
            65536 to Color(0xFF23375D)
        )
    } else {
        // Light theme - vibrant but not too bright
        mapOf(
            1 to Color(0xFFEEE4DA),
            2 to Color(0xFFEDE0C8),
            4 to Color(0xFFF2B179),
            8 to Color(0xFFF59563),
            16 to Color(0xFFF67C5F),
            32 to Color(0xFFF65E3B),
            64 to Color(0xFFEDCF72),
            128 to Color(0xFFEDCC61),
            256 to Color(0xFFEDC850),
            512 to Color(0xFFEDC53F),
            1024 to Color(0xFFEDC22E),
            2048 to Color(0xFFA7E635),
            4096 to Color(0xFF63E63B),
            8192 to Color(0xFF3BE6A7),
            16384 to Color(0xFF3BC3E6),
            32768 to Color(0xFF3B7BE6),
            65536 to Color(0xFF633BE6)
        )
    }

    val textColors = tileColors.mapValues { (value, tileColor) ->
        if (isDarkTheme) {
            if (value <= 64) Color.White else Color.Black
        } else {
            if (value <= 4) Color(0xFF776E65) else Color.White
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