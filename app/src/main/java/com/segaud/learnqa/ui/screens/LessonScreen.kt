package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.CardDefaults

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

    var selectedOptionIds by rememberSaveable {
        mutableStateOf<List<String>>(emptyList())
    }

    var answerSubmitted by rememberSaveable {
        mutableStateOf(false)
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

    fun resetAnswerState() {
        selectedOptionId = null
        selectedOptionIds = emptyList()
        answerSubmitted = false
    }

    fun advanceLesson() {

        if (
            currentExerciseIndex <
            lesson.exercises.lastIndex
        ) {

            currentExerciseIndex++
            resetAnswerState()

        } else {

            coroutineScope.launch {

                xpAwarded =
                    progressRepository.recordLessonResult(
                        lessonId = lesson.id,
                        xpEarned = xpEarned
                    )

                lessonComplete = true
            }
        }
    }

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

    val exercise =
        lesson.exercises[currentExerciseIndex]

    val continueButtonText =
        if (
            currentExerciseIndex <
            lesson.exercises.lastIndex
        ) {
            "Continue"
        } else {
            "Finish lesson"
        }

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

                            if (!answerSubmitted) {

                                selectedOptionId =
                                    option.id

                                answerSubmitted = true

                                if (
                                    option.id ==
                                    exercise.correctOptionId
                                ) {
                                    correctAnswers++
                                }
                            }
                        },
                        enabled = !answerSubmitted,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(option.text)
                    }
                }

                if (answerSubmitted) {

                    val isCorrect =
                        selectedOptionId ==
                            exercise.correctOptionId

                    ExerciseFeedback(
                        isCorrect = isCorrect,
                        explanation = exercise.explanation,
                        buttonText = continueButtonText,
                        onContinue = {
                            advanceLesson()
                        }
                    )
                }
            }

            is Exercise.MultiSelect -> {

                Text(
                    text = exercise.prompt,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Select all that apply.",
                    style = MaterialTheme.typography.bodyMedium
                )

                exercise.options.forEach { option ->

                    val isSelected =
                        option.id in selectedOptionIds

                    OutlinedButton(
                        onClick = {

                            if (!answerSubmitted) {

                                selectedOptionIds =
                                    if (isSelected) {

                                        selectedOptionIds -
                                            option.id

                                    } else {

                                        selectedOptionIds +
                                            option.id
                                    }
                            }
                        },
                        enabled = !answerSubmitted,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = if (isSelected) {
                                "✓ ${option.text}"
                            } else {
                                option.text
                            }
                        )
                    }
                }

                if (!answerSubmitted) {

                    Button(
                        onClick = {

                            answerSubmitted = true

                            val isCorrect =
                                selectedOptionIds.toSet() ==
                                    exercise.correctOptionIds

                            if (isCorrect) {
                                correctAnswers++
                            }
                        },
                        enabled =
                            selectedOptionIds.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check answer")
                    }
                }

                if (answerSubmitted) {

                    val isCorrect =
                        selectedOptionIds.toSet() ==
                            exercise.correctOptionIds

                    ExerciseFeedback(
                        isCorrect = isCorrect,
                        explanation = exercise.explanation,
                        buttonText = continueButtonText,
                        onContinue = {
                            advanceLesson()
                        }
                    )
                }
            }
            
            is Exercise.BugReportReview -> {

                Text(
                    text = exercise.prompt,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Select the most useful bug report.",
                    style = MaterialTheme.typography.bodyMedium
                )

                exercise.reports.forEach { report ->

                    val isSelected =
                        selectedOptionId == report.id

                    OutlinedCard(
                        onClick = {

                            if (!answerSubmitted) {
                                selectedOptionId = report.id
                            }
                        },
                        enabled = !answerSubmitted,
                        modifier = Modifier.fillMaxWidth(),
                        border = BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.outlineVariant
                            }
                        ),
                        colors = CardDefaults.outlinedCardColors(
                            containerColor = if (isSelected) {
                                MaterialTheme.colorScheme.primaryContainer
                            } else {
                                MaterialTheme.colorScheme.surface
                            }
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Text(
                                text = report.title,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {

                                Text(
                                    text = "Steps",
                                    style = MaterialTheme.typography.labelLarge
                                )

                                Text(
                                    text = report.steps,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {

                                Text(
                                    text = "Expected",
                                    style = MaterialTheme.typography.labelLarge
                                )

                                Text(
                                    text = report.expected,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            Column(
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {

                                Text(
                                    text = "Actual",
                                    style = MaterialTheme.typography.labelLarge
                                )

                                Text(
                                    text = report.actual,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                if (!answerSubmitted) {

                    Button(
                        onClick = {

                            answerSubmitted = true

                            if (
                                selectedOptionId ==
                                exercise.correctReportId
                            ) {
                                correctAnswers++
                            }
                        },
                        enabled = selectedOptionId != null,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Check answer")
                    }
                }

                if (answerSubmitted) {

                    val isCorrect =
                        selectedOptionId ==
                            exercise.correctReportId

                    ExerciseFeedback(
                        isCorrect = isCorrect,
                        explanation = exercise.explanation,
                        buttonText = continueButtonText,
                        onContinue = {
                            advanceLesson()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ExerciseFeedback(
    isCorrect: Boolean,
    explanation: String,
    buttonText: String,
    onContinue: () -> Unit
) {

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
                text = explanation
            )

            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(buttonText)
            }
        }
    }
}