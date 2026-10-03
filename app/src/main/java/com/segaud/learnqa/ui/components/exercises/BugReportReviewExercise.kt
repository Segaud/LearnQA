package com.segaud.learnqa.ui.components.exercises

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.model.Exercise

@Composable
fun BugReportReviewExercise(
    exercise: Exercise.BugReportReview,
    selectedOptionId: String?,
    answerSubmitted: Boolean,
    buttonText: String,
    onReportSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onContinue: () -> Unit
) {

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
                onReportSelected(report.id)
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
            onClick = onSubmit,
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
            buttonText = buttonText,
            onContinue = onContinue
        )
    }
}