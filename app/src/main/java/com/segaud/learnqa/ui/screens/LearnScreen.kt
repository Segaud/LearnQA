package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.SampleContent
import com.segaud.learnqa.data.progress.ProgressRepository
import com.segaud.learnqa.model.LearningUnit
import com.segaud.learnqa.model.Lesson
import androidx.compose.material3.LinearProgressIndicator

@Composable
fun LearnScreen(
    progressRepository: ProgressRepository,
    onStartLesson: (String) -> Unit
) {

    val subject = SampleContent.qaFundamentals

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
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

        Text(
            text = subject.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = subject.description,
            style = MaterialTheme.typography.bodyMedium
        )

        subject.units.forEachIndexed { unitIndex, unit ->

            val previousUnit =
                if (unitIndex == 0) {
                    null
                } else {
                    subject.units[unitIndex - 1]
                }

            val previousUnitCompleted =
                if (previousUnit == null) {

                    true

                } else {

                    val previousLessonIds =
                        previousUnit.lessons.map { lesson ->
                            lesson.id
                        }

                    progressRepository
                        .areLessonsCompleted(previousLessonIds)
                        .collectAsState(initial = false)
                        .value
                }

            UnitSection(
                unit = unit,
                unitNumber = unitIndex + 1,
                unitUnlocked = previousUnitCompleted,
                progressRepository = progressRepository,
                onStartLesson = onStartLesson
            )
        }
    }
}

@Composable
private fun UnitSection(
    unit: LearningUnit,
    unitNumber: Int,
    unitUnlocked: Boolean,
    progressRepository: ProgressRepository,
    onStartLesson: (String) -> Unit
) {

    val lessonIds =
        unit.lessons.map { lesson ->
            lesson.id
        }

    val unitCompleted by
        progressRepository
            .areLessonsCompleted(lessonIds)
            .collectAsState(initial = false)

    val completedLessons by
        progressRepository
            .completedLessonCount(lessonIds)
            .collectAsState(initial = 0)

    val unitXp by
        progressRepository
            .lessonBestXpTotal(lessonIds)
            .collectAsState(initial = 0)

    val totalLessons =
        unit.lessons.size

    val availableXp =
        unit.lessons.sumOf { lesson ->
            lesson.exercises.size * 20
        }

    val unitProgress =
        if (totalLessons == 0) {
            0f
        } else {
            completedLessons.toFloat() /
                totalLessons
        }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor =
                    if (unitUnlocked) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = "UNIT $unitNumber",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = unit.title
                        .substringAfter("—")
                        .trim(),
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = unit.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                LinearProgressIndicator(
                    progress = {
                        unitProgress
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text =
                        "$completedLessons / $totalLessons lessons • " +
                            "$unitXp / $availableXp XP",
                    style = MaterialTheme.typography.bodyMedium
                )

                when {
                    unitCompleted -> {
                        Text(
                            text = "✓ Unit complete",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    !unitUnlocked -> {
                        Text(
                            text = "Complete the previous unit to unlock.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        unit.lessons.forEachIndexed { index, lesson ->

            val previousLessonId =
                if (index == 0) {
                    null
                } else {
                    unit.lessons[index - 1].id
                }

            LessonCard(
                lesson = lesson,
                lessonNumber = index + 1,
                previousLessonId = previousLessonId,
                unitUnlocked = unitUnlocked,
                progressRepository = progressRepository,
                onStartLesson = onStartLesson
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
                .isLessonCompleted(previousLessonId)
                .collectAsState(initial = false)
                .value
        }

    val isUnlocked =
        unitUnlocked &&
            (previousLessonCompleted || lessonCompleted)

    val maxXp =
        lesson.exercises.size * 20

    val cardColor =
        when {
            lessonCompleted ->
                MaterialTheme.colorScheme.secondaryContainer

            !isUnlocked ->
                MaterialTheme.colorScheme.surfaceVariant

            else ->
                MaterialTheme.colorScheme.surface
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
                if (isUnlocked) {
                    4.dp
                } else {
                    1.dp
                }
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Lesson $lessonNumber",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = lesson.description,
                style = MaterialTheme.typography.bodyMedium
            )

            when {

                lessonCompleted -> {

                    Text(
                        text = "✓ Completed",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Best score: $bestXp / $maxXp XP",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                isUnlocked -> {

                    Text(
                        text = "Ready • Up to $maxXp XP",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                else -> {

                    Text(
                        text =
                            if (!unitUnlocked) {
                                "Complete the previous unit to unlock."
                            } else {
                                "Complete the previous lesson to unlock."
                            },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

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