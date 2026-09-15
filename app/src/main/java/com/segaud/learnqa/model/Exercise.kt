package com.segaud.learnqa.model

sealed interface Exercise {
    val id: String
    val prompt: String

    data class MultipleChoice(
        override val id: String,
        override val prompt: String,
        val options: List<AnswerOption>,
        val correctOptionId: String,
        val explanation: String
    ) : Exercise
}

data class AnswerOption(
    val id: String,
    val text: String
)