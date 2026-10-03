package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
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
fun ProgressScreen(
    progressRepository: ProgressRepository
) {

    val subject =
        SampleContent.qaFundamentals

    val allLessons =
        subject.units.flatMap { unit ->
            unit.lessons
        }

    val allLessonIds =
        allLessons.map { lesson ->
            lesson.id
        }

    val totalLessons =
        allLessons.size

    val totalAvailableXp =
        allLessons.sumOf { lesson ->
            lesson.exercises.size * 20
        }

    val totalXp by
        progressRepository.totalXp.collectAsState(
            initial = 0
        )

    val completedLessons by
        progressRepository
            .completedLessonCount(allLessonIds)
            .collectAsState(initial = 0)

    val xpProgress =
        if (totalAvailableXp == 0) {
            0f
        } else {
            (
                totalXp.toFloat() /
                    totalAvailableXp
                ).coerceIn(0f, 1f)
        }

    val completionProgress =
        if (totalLessons == 0) {
            0f
        } else {
            completedLessons.toFloat() /
                totalLessons
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Progress",
            style = MaterialTheme.typography.headlineLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "XP",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "$totalXp / $totalAvailableXp XP",
                    style = MaterialTheme.typography.headlineMedium
                )

                LinearProgressIndicator(
                    progress = {
                        xpProgress
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Best scores earned across all lessons"
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Course completion",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "$completedLessons / $totalLessons lessons completed",
                    style = MaterialTheme.typography.headlineSmall
                )

                LinearProgressIndicator(
                    progress = {
                        completionProgress
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Text(
            text = "Units",
            style = MaterialTheme.typography.headlineSmall
        )

        subject.units.forEachIndexed { index, unit ->

            UnitProgressCard(
                unit = unit,
                unitNumber = index + 1,
                progressRepository = progressRepository
            )
        }
    }
}

@Composable
private fun UnitProgressCard(
    unit: LearningUnit,
    unitNumber: Int,
    progressRepository: ProgressRepository
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

    val unitComplete =
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
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Unit $unitNumber",
                style = MaterialTheme.typography.labelLarge
            )

            Text(
                text = unit.title,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = if (unitComplete) {
                    "✓ Complete"
                } else {
                    "$completedLessons / $totalLessons lessons completed"
                },
                style = MaterialTheme.typography.titleSmall
            )

            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "$unitXp / $availableXp XP"
            )
        }
    }
}