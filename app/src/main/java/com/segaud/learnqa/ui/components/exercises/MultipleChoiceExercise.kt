package com.segaud.learnqa.ui.components.exercises

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.segaud.learnqa.model.Exercise

@Composable
fun MultipleChoiceExercise(
    exercise: Exercise.MultipleChoice,
    selectedOptionId: String?,
    answerSubmitted: Boolean,
    buttonText: String,
    onOptionSelected: (String) -> Unit,
    onContinue: () -> Unit
) {

    Text(
        text = exercise.prompt,
        style = MaterialTheme.typography.titleMedium
    )

    exercise.options.forEach { option ->

        OutlinedButton(
            onClick = {
                onOptionSelected(option.id)
            },
            enabled = !answerSubmitted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(option.text)
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