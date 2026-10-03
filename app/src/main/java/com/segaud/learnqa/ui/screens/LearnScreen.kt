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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.segaud.learnqa.data.progress.ProgressRepository

@Composable
fun LearnScreen(
    progressRepository: ProgressRepository,
    onStartLesson: () -> Unit
) {
    

    val subject = SampleContent.qaFundamentals
    val firstUnit = subject.units.first()
    val firstLesson = firstUnit.lessons.first()
    
    val lessonCompleted by
        progressRepository
            .isLessonCompleted(firstLesson.id)
            .collectAsState(initial = false)

    val bestXp by
        progressRepository
            .lessonBestXp(firstLesson.id)
            .collectAsState(initial = 0)

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
                
                if (lessonCompleted) {

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Text(
                        text = "✓ Completed",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        text = "Best score: $bestXp / 60 XP",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Button(
                    onClick = onStartLesson,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (lessonCompleted) {
                            "Review lesson"
                        } else {
                            "Start lesson"
                        }
                    )
                }
            }
        }
    }
}