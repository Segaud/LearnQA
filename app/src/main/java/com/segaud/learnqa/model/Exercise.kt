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

    data class MultiSelect(
        override val id: String,
        override val prompt: String,
        val options: List<AnswerOption>,
        val correctOptionIds: Set<String>,
        val explanation: String
    ) : Exercise

    data class BugReportReview(
        override val id: String,
        override val prompt: String,
        val reports: List<BugReportOption>,
        val correctReportId: String,
        val explanation: String
    ) : Exercise
}

data class AnswerOption(
    val id: String,
    val text: String
)

data class BugReportOption(
    val id: String,
    val title: String,
    val steps: String,
    val expected: String,
    val actual: String
)