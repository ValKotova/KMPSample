package com.magni.game2048.feature.game.presentation.GameBoard

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.magni.game2048.core.presentation.darken
import com.magni.game2048.core.presentation.lighten

@Composable
fun VolumetricCircle(
    baseColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .drawWithCache {
                onDrawBehind {
                    val circleRadius = size.minDimension / 2f
                    val center = Offset(circleRadius, circleRadius)

                    // shadow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.2f),
                                Color.Transparent
                            ),
                            center = Offset(circleRadius * 1.4f, circleRadius * 1.4f),
                            radius = circleRadius * 0.8f
                        ),
                        radius = circleRadius * 1.08f,
                        center = Offset(circleRadius * 1.04f, circleRadius * 1.04f)
                    )

                    // 2. Enhanced sphere with stronger gradient contrast
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                baseColor.lighten(0.6f), // Much brighter center
                                baseColor.lighten(0.3f),
                                baseColor,               // Main color
                                baseColor.darken(0.4f)   // Much darker edges
                            ),
                            center = Offset(circleRadius * 0.6f, circleRadius * 0.6f),
                            radius = circleRadius * 1.4f
                        ),
                        radius = circleRadius,
                        center = center
                    )

                    // 3. Strong highlight at 45° (pure white)
                    val highlightDistance = circleRadius * 0.6f
                    val highlightOffset = highlightDistance * 0.707f
                    val highlightCenter = Offset(
                        center.x - highlightOffset,
                        center.y - highlightOffset
                    )

                    // Main highlight
                    drawCircle(
                        color = Color.White,
                        radius = circleRadius * 0.18f,
                        center = highlightCenter
                    )

                    // 4. Enhanced glow around highlight
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.9f),
                                Color.White.copy(alpha = 0.5f),
                                Color.White.copy(alpha = 0.2f),
                                Color.Transparent
                            ),
                            center = highlightCenter,
                            radius = circleRadius * 0.3f
                        ),
                        radius = circleRadius * 0.25f,
                        center = highlightCenter
                    )

                    // Secondary reflection
                    val reflectionCenter = Offset(
                        highlightCenter.x - circleRadius * 0.05f,
                        highlightCenter.y - circleRadius * 0.05f
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.9f),
                        radius = circleRadius * 0.06f,
                        center = reflectionCenter
                    )
                }
            }
    ) {
        content()
    }
}