package com.magni.game2048.feature.history.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.magni.game2048.core.presentation.NavControllerImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordsTopBar(navController: NavControllerImpl) {
    TopAppBar(
        title = { Text("Records") },
        navigationIcon = {
            IconButton(onClick = { navController.navigateBack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}