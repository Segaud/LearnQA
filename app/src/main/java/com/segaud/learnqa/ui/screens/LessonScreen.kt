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
import com.segaud.learnqa.ui.components.exercises.ExerciseFeedback
import com.segaud.learnqa.ui.components.exercises.MultipleChoiceExercise
import com.segaud.learnqa.ui.components.exercises.MultiSelectExercise
import com.segaud.learnqa.ui.components.exercises.BugReportReviewExercise

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

                MultipleChoiceExercise(
                    exercise = exercise,
                    selectedOptionId = selectedOptionId,
                    answerSubmitted = answerSubmitted,
                    buttonText = continueButtonText,
                    onOptionSelected = { optionId ->

                        if (!answerSubmitted) {

                            selectedOptionId = optionId
                            answerSubmitted = true

                            if (
                                optionId ==
                                exercise.correctOptionId
                            ) {
                                correctAnswers++
                            }
                        }
                    },
                    onContinue = {
                        advanceLesson()
                    }
                )
            }

            is Exercise.MultiSelect -> {

                MultiSelectExercise(
                    exercise = exercise,
                    selectedOptionIds = selectedOptionIds,
                    answerSubmitted = answerSubmitted,
                    buttonText = continueButtonText,
                    onOptionToggle = { optionId ->

                        if (!answerSubmitted) {

                            selectedOptionIds =
                                if (optionId in selectedOptionIds) {

                                    selectedOptionIds - optionId

                                } else {

                                    selectedOptionIds + optionId
                                }
                        }
                    },
                    onSubmit = {

                        answerSubmitted = true

                        val isCorrect =
                            selectedOptionIds.toSet() ==
                                exercise.correctOptionIds

                        if (isCorrect) {
                            correctAnswers++
                        }
                    },
                    onContinue = {
                        advanceLesson()
                    }
                )
            }
            
            is Exercise.BugReportReview -> {

                BugReportReviewExercise(
                    exercise = exercise,
                    selectedOptionId = selectedOptionId,
                    answerSubmitted = answerSubmitted,
                    buttonText = continueButtonText,
                    onReportSelected = { reportId ->

                        if (!answerSubmitted) {
                            selectedOptionId = reportId
                        }
                    },
                    onSubmit = {

                        answerSubmitted = true

                        if (
                            selectedOptionId ==
                            exercise.correctReportId
                        ) {
                            correctAnswers++
                        }
                    },
                    onContinue = {
                        advanceLesson()
                    }
                )
            }
        }
    }
}