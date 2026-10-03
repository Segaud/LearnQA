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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.progress.ProgressRepository
import com.segaud.learnqa.model.LearningUnit
import com.segaud.learnqa.model.Lesson

@Composable
fun UnitLessonsScreen(
    unit: LearningUnit,
    unitNumber: Int,
    previousUnit: LearningUnit?,
    progressRepository: ProgressRepository,
    onBack: () -> Unit,
    onStartLesson: (String) -> Unit
) {

    val unitUnlocked =
        if (previousUnit == null) {

            true

        } else {

            val previousIds =
                previousUnit.lessons.map {
                    it.id
                }

            progressRepository
                .areLessonsCompleted(previousIds)
                .collectAsState(initial = false)
                .value
        }

    val lessonIds =
        unit.lessons.map { it.id }

    val completedLessons by
        progressRepository
            .completedLessonCount(lessonIds)
            .collectAsState(initial = 0)

    val progress =
        if (unit.lessons.isEmpty()) {
            0f
        } else {
            completedLessons.toFloat() /
                unit.lessons.size
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back to units")
        }

        Text(
            text = "UNIT $unitNumber",
            style =
                MaterialTheme.typography.labelLarge
        )

        Text(
            text = unit.title
                .substringAfter("—")
                .trim(),
            style =
                MaterialTheme.typography.headlineLarge
        )

        Text(
            text = unit.description,
            style =
                MaterialTheme.typography.bodyLarge
        )

        LinearProgressIndicator(
            progress = {
                progress
            },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text =
                "$completedLessons / " +
                    "${unit.lessons.size} lessons completed"
        )

        unit.lessons.forEachIndexed {
                index,
                lesson ->

            val previousLessonId =
                if (index == 0) {
                    null
                } else {
                    unit.lessons[index - 1].id
                }

            LessonCard(
                lesson = lesson,
                lessonNumber = index + 1,
                previousLessonId =
                    previousLessonId,
                unitUnlocked =
                    unitUnlocked,
                progressRepository =
                    progressRepository,
                onStartLesson =
                    onStartLesson
            )
        }
    }
}

@Composable
private fun LessonCard(
    lesson: Lesson,
    lessonNumber: Int,
    previousLessonId: String?,
    unitUnlocked: Boolean,
    progressRepository: ProgressRepository,
    onStartLesson: (String) -> Unit
) {

    val lessonCompleted by
        progressRepository
            .isLessonCompleted(lesson.id)
            .collectAsState(initial = false)

    val bestXp by
        progressRepository
            .lessonBestXp(lesson.id)
            .collectAsState(initial = 0)

    val previousLessonCompleted =
        if (previousLessonId == null) {

            true

        } else {

            progressRepository
                .isLessonCompleted(
                    previousLessonId
                )
                .collectAsState(initial = false)
                .value
        }

    val isUnlocked =
        unitUnlocked &&
            (
                previousLessonCompleted ||
                    lessonCompleted
                )

    val maxXp =
        lesson.exercises.size * 20

    val cardColor =
        when {
            lessonCompleted ->
                MaterialTheme.colorScheme
                    .secondaryContainer

            !isUnlocked ->
                MaterialTheme.colorScheme
                    .surfaceVariant

            else ->
                MaterialTheme.colorScheme.surface
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Lesson $lessonNumber",
                style =
                    MaterialTheme.typography.labelLarge
            )

            Text(
                text = lesson.title,
                style =
                    MaterialTheme.typography.titleLarge
            )

            Text(
                text = lesson.description
            )

            when {

                lessonCompleted -> {

                    Text(
                        text = "✓ Completed"
                    )

                    Text(
                        text =
                            "Best score: " +
                                "$bestXp / $maxXp XP"
                    )
                }

                isUnlocked -> {

                    Text(
                        text =
                            "Ready • Up to $maxXp XP"
                    )
                }

                else -> {

                    Text(
                        text =
                            "Complete the previous lesson to unlock."
                    )
                }
            }

            Button(
                onClick = {
                    onStartLesson(lesson.id)
                },
                enabled = isUnlocked,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    when {
                        lessonCompleted ->
                            "Review lesson"

                        isUnlocked ->
                            "Start lesson"

                        else ->
                            "Locked"
                    }
                )
            }
        }
    }
}