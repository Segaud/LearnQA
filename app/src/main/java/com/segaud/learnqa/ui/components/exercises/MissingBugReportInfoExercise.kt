package com.segaud.learnqa.ui.components.exercises

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.segaud.learnqa.model.Exercise
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ButtonDefaults

@Composable
fun MissingBugReportInfoExercise(
    exercise: Exercise.MissingBugReportInfo,
    selectedOptionId: String?,
    answerSubmitted: Boolean,
    buttonText: String,
    onOptionSelected: (String) -> Unit,
    onSubmit: () -> Unit,
    onContinue: () -> Unit
) {

    Text(
        text = exercise.prompt,
        style = MaterialTheme.typography.titleMedium
    )

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Text(
                text = exercise.report.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Steps",
                style = MaterialTheme.typography.labelLarge
            )

            Text(exercise.report.steps)

            Text(
                text = "Expected",
                style = MaterialTheme.typography.labelLarge
            )

            Text(exercise.report.expected)

            Text(
                text = "Actual",
                style = MaterialTheme.typography.labelLarge
            )

            Text(exercise.report.actual)
        }
    }

    exercise.options.forEach { option ->

        val isSelected =
            selectedOptionId == option.id

        OutlinedButton(
            onClick = {
                onOptionSelected(option.id)
            },
            enabled = !answerSubmitted,
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                }
            ),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isSelected) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
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
            onClick = onSubmit,
            enabled = selectedOptionId != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check answer")
        }
    }

    if (answerSubmitted) {

        val isCorrect =
            selectedOptionId == exercise.correctOptionId

        ExerciseFeedback(
            isCorrect = isCorrect,
            explanation = exercise.explanation,
            buttonText = buttonText,
            onContinue = onContinue
        )
    }
}