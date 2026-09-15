package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.model.Exercise
import com.segaud.learnqa.model.Lesson

@Composable
fun LessonScreen(
    lesson: Lesson,
    onBack: () -> Unit
) {

    var selectedOptionId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val exercise = lesson.exercises.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Text(
            text = lesson.title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Exercise 1 of ${lesson.exercises.size}",
            style = MaterialTheme.typography.labelLarge
        )

        when (exercise) {

            is Exercise.MultipleChoice -> {

                Text(
                    text = exercise.prompt,
                    style = MaterialTheme.typography.titleMedium
                )

                exercise.options.forEach { option ->

                    OutlinedButton(
                        onClick = {
                            selectedOptionId = option.id
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(option.text)
                    }
                }

                if (selectedOptionId != null) {

                    val isCorrect =
                        selectedOptionId == exercise.correctOptionId

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Text(
                                text = if (isCorrect) {
                                    "Correct!"
                                } else {
                                    "Not quite."
                                },
                                style = MaterialTheme.typography.titleLarge
                            )

                            Text(
                                text = if (isCorrect) {
                                    exercise.explanation
                                } else {
                                    "Try comparing the actual behaviour with the requirement."
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}