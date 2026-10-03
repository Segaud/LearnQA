package com.segaud.learnqa.ui.components.exercises

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.segaud.learnqa.model.Exercise

@Composable
fun MultiSelectExercise(
    exercise: Exercise.MultiSelect,
    selectedOptionIds: List<String>,
    answerSubmitted: Boolean,
    buttonText: String,
    onOptionToggle: (String) -> Unit,
    onSubmit: () -> Unit,
    onContinue: () -> Unit
) {

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
                onOptionToggle(option.id)
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
            onClick = onSubmit,
            enabled = selectedOptionIds.isNotEmpty(),
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
            buttonText = buttonText,
            onContinue = onContinue
        )
    }
}