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

    Spacer(
        modifier = Modifier.height(8.dp)
    )

    Text(
        text = unit.title,
        style = MaterialTheme.typography.titleLarge
    )

    Text(
        text = unit.description,
        style = MaterialTheme.typography.bodyMedium
    )

    if (unitCompleted) {

        Text(
            text = "✓ Unit $unitNumber complete",
            style = MaterialTheme.typography.titleSmall
        )

    } else if (!unitUnlocked) {

        Text(
            text = "Complete the previous unit to unlock.",
            style = MaterialTheme.typography.bodyMedium
        )
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

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
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

            if (lessonCompleted) {

                Text(
                    text = "✓ Completed",
                    style = MaterialTheme.typography.titleSmall
                )

                Text(
                    text = "Best score: $bestXp / $maxXp XP",
                    style = MaterialTheme.typography.bodyMedium
                )

            } else if (!isUnlocked) {

                Text(
                    text = if (!unitUnlocked) {
                        "Complete the previous unit to unlock."
                    } else {
                        "Complete the previous lesson to unlock."
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
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