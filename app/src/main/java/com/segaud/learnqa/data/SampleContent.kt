package com.segaud.learnqa.data

import com.segaud.learnqa.model.AnswerOption
import com.segaud.learnqa.model.Exercise
import com.segaud.learnqa.model.LearningUnit
import com.segaud.learnqa.model.Lesson
import com.segaud.learnqa.model.Subject

object SampleContent {

    val qaFundamentals = Subject(
        id = "qa_fundamentals",
        title = "QA Fundamentals",
        description = "Learn how testers investigate software, find defects and help teams release better products.",
        units = listOf(
            LearningUnit(
                id = "introduction_to_testing",
                title = "Unit 1 — Introduction to Testing",
                description = "Learn what software testing is and why it matters.",
                lessons = listOf(
                    Lesson(
                        id = "what_is_software_testing",
                        title = "What is software testing?",
                        description = "Discover how testers compare expected and actual behaviour.",
                        exercises = listOf(
                            Exercise.MultipleChoice(
                                id = "password_requirement",
                                prompt = """
                                    Requirement:

                                    A password must contain at least 8 characters.

                                    The application accepts the password "abcdefg".

                                    Does the application meet the requirement?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "yes",
                                        text = "Yes"
                                    ),
                                    AnswerOption(
                                        id = "no",
                                        text = "No"
                                    )
                                ),
                                correctOptionId = "no",
                                explanation = "The password contains only 7 characters, but the requirement says it must contain at least 8."
                            )
                        )
                    )
                )
            )
        )
    )
}