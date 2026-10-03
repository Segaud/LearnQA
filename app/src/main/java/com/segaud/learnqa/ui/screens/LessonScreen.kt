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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.progress.ProgressRepository
import com.segaud.learnqa.model.Exercise
import com.segaud.learnqa.model.Lesson
import kotlinx.coroutines.launch
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

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

    var xpAwarded by rememberSaveable {
        mutableStateOf<Int?>(null)
    }

    var lessonComplete by rememberSaveable {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    val xpEarned = correctAnswers * 20

    // Lesson completion screen
    if (lessonComplete) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                text = "Score: $xpEarned XP",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = if ((xpAwarded ?: 0) > 0) {
                    "+${xpAwarded ?: 0} XP added"
                } else {
                    "No additional XP earned"
                }
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

    val exercise = lesson.exercises[currentExerciseIndex]

    // Main lesson screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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

                            // Only the first answer counts towards the score
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

                // Show feedback once an answer has been selected
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

                                    // Move to the next exercise
                                    if (
                                        currentExerciseIndex <
                                        lesson.exercises.lastIndex
                                    ) {

                                        currentExerciseIndex++
                                        selectedOptionId = null

                                    } else {

                                        // Final exercise:
                                        // save the result before completing
                                        coroutineScope.launch {

                                            xpAwarded =
                                                progressRepository.recordLessonResult(
                                                    lessonId = lesson.id,
                                                    xpEarned = xpEarned
                                                )

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