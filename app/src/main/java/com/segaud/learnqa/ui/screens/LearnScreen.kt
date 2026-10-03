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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.data.SampleContent
import com.segaud.learnqa.data.progress.ProgressRepository
import com.segaud.learnqa.model.LearningUnit

@Composable
fun LearnScreen(
    progressRepository: ProgressRepository,
    onOpenUnit: (String) -> Unit
) {

    val subject =
        SampleContent.qaFundamentals

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

        Text(
            text = "Learn",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = subject.title,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = subject.description,
            style = MaterialTheme.typography.bodyLarge
        )

        subject.units.forEachIndexed { index, unit ->

            val previousUnit =
                if (index == 0) {
                    null
                } else {
                    subject.units[index - 1]
                }

            val unitUnlocked =
                if (previousUnit == null) {

                    true

                } else {

                    val previousLessonIds =
                        previousUnit.lessons.map { lesson ->
                            lesson.id
                        }

                    progressRepository
                        .areLessonsCompleted(
                            previousLessonIds
                        )
                        .collectAsState(
                            initial = false
                        )
                        .value
                }

            UnitCard(
                unit = unit,
                unitNumber = index + 1,
                unitUnlocked = unitUnlocked,
                progressRepository =
                    progressRepository,
                onOpenUnit = onOpenUnit
            )
        }
    }
}

@Composable
private fun UnitCard(
    unit: LearningUnit,
    unitNumber: Int,
    unitUnlocked: Boolean,
    progressRepository: ProgressRepository,
    onOpenUnit: (String) -> Unit
) {

    val lessonIds =
        unit.lessons.map { lesson ->
            lesson.id
        }

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

    val unitCompleted =
        totalLessons > 0 &&
            completedLessons == totalLessons

    val progress =
        if (totalLessons == 0) {
            0f
        } else {
            completedLessons.toFloat() /
                totalLessons
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor =
                if (unitUnlocked) {
                    MaterialTheme.colorScheme
                        .primaryContainer
                } else {
                    MaterialTheme.colorScheme
                        .surfaceVariant
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
                style =
                    MaterialTheme.typography.labelLarge,
                color =
                    MaterialTheme.colorScheme.primary
            )

            Text(
                text = unit.title
                    .substringAfter("—")
                    .trim(),
                style =
                    MaterialTheme.typography.headlineSmall
            )

            Text(
                text = unit.description
            )

            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text =
                    "$completedLessons / $totalLessons lessons • " +
                        "$unitXp / $availableXp XP"
            )

            if (unitCompleted) {

                Text(
                    text = "✓ Unit complete",
                    style =
                        MaterialTheme.typography.titleSmall
                )

            } else if (!unitUnlocked) {

                Text(
                    text =
                        "Complete the previous unit to unlock."
                )
            }

            Button(
                onClick = {
                    onOpenUnit(unit.id)
                },
                enabled = unitUnlocked,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    when {
                        unitCompleted ->
                            "Review unit"

                        unitUnlocked ->
                            "View lessons"

                        else ->
                            "Locked"
                    }
                )
            }
        }
    }
}