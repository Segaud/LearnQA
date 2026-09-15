package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.SampleContent

@Composable
fun LearnScreen(
    onStartLesson: () -> Unit
) {
    

    val subject = SampleContent.qaFundamentals
    val firstUnit = subject.units.first()
    val firstLesson = firstUnit.lessons.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Learn",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Build your QA skills one lesson at a time.",
            style = MaterialTheme.typography.bodyLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = subject.title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = subject.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = firstUnit.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = firstUnit.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Text(
                    text = firstLesson.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = firstLesson.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = onStartLesson,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Start lesson")
                }
            }
        }
    }
}