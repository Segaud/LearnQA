package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
import com.segaud.learnqa.data.progress.ProgressRepository
import kotlinx.coroutines.launch

@Composable
fun LessonScreen(
    lesson: Lesson,
    progressRepository: ProgressRepository,
    onBack: () -> Unit
) {

    var currentExerciseIndex by rememberSaveable {
        mutableStateOf(0)
    }

    var selectedOptionId by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var correctAnswers by rememberSaveable {
        mutableStateOf(0)
    }

    var lessonComplete by rememberSaveable {
        mutableStateOf(false)
    }

    val xpEarned = correctAnswers * 20

    if (lessonComplete) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Lesson complete!",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "${lesson.exercises.size} exercises completed"
            )

            Text(
                text = "$correctAnswers of ${lesson.exercises.size} correct"
            )

            Text(
                text = "+$xpEarned XP",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }

        return
    }
    
    val coroutineScope = rememberCoroutineScope()

    val exercise = lesson.exercises[currentExerciseIndex]

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

        LinearProgressIndicator(
            progress = {
                (currentExerciseIndex + 1).toFloat() /
                    lesson.exercises.size
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Exercise ${currentExerciseIndex + 1} of ${lesson.exercises.size}",
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

                            // Only the first answer counts
                            if (selectedOptionId == null) {

                                selectedOptionId = option.id

                                if (option.id == exercise.correctOptionId) {
                                    correctAnswers++
                                }
                            }
                        },
                        enabled = selectedOptionId == null,
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
                                text = exercise.explanation
                            )

                            Button(
                                onClick = {

                                    if (
                                        currentExerciseIndex <
                                        lesson.exercises.lastIndex
                                    ) {

                                        currentExerciseIndex++

                                        // Reset for the next question
                                        selectedOptionId = null

                                    } else {

                                        coroutineScope.launch {
                                        progressRepository.addXp(xpEarned)
                                            lessonComplete = true
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {

                                Text(
                                    if (
                                        currentExerciseIndex <
                                        lesson.exercises.lastIndex
                                    ) {
                                        "Continue"
                                    } else {
                                        "Finish lesson"
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}