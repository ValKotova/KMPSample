package com.magni.game2048.feature.game.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.magni.game2048.core.domain.entity.Animation
import com.magni.game2048.core.domain.entity.Grid

@Composable
fun DebugPanel(
    grid: Grid,
    animations: List<Animation>,
    animatedOffsets: Map<Long, Offset>,
    animatedScales: Map<Long, Float>
) {
    Column(modifier = Modifier.background(Color.LightGray).padding(8.dp)) {
        Text("Debug Info", fontWeight = FontWeight.Bold)
        Text("Grid cells: ${grid.cells.flatten().size}")
        Text("Animations: ${animations.size}")
        Text("Animated offsets: ${animatedOffsets.size}")
        Text("Animated scales: ${animatedScales.size}")

        animations.forEachIndexed { index, animation ->
            Text("Animation $index: $animation")
        }

        animatedOffsets.forEach { (id, offset) ->
            Text("Offset $id: $offset")
        }

        animatedScales.forEach { (id, scale) ->
            Text("Scale $id: $scale")
        }
    }
}