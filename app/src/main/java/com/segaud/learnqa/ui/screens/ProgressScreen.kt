package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.progress.ProgressRepository

@Composable
fun ProgressScreen(
    progressRepository: ProgressRepository
) {

    val totalXp by progressRepository.totalXp.collectAsState(
        initial = 0
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Progress",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "$totalXp XP",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Total XP earned"
        )
    }
}