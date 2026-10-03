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
                            ),

                            Exercise.MultipleChoice(
                                id = "blank_username",
                                prompt = """
                                    Requirement:

                                    A username must not be empty.

                                    The application accepts a blank username.

                                    What does this indicate?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "passed",
                                        text = "The test passed"
                                    ),
                                    AnswerOption(
                                        id = "defect",
                                        text = "The application contains a defect"
                                    ),
                                    AnswerOption(
                                        id = "nothing_wrong",
                                        text = "Nothing is wrong"
                                    )
                                ),
                                correctOptionId = "defect",
                                explanation = "The application accepts behaviour that contradicts the requirement."
                            ),

                            Exercise.MultipleChoice(
                                id = "minimum_age",
                                prompt = """
                                    Requirement:

                                    Users aged 18 or older can register.

                                    Which value would be especially useful to test?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "12",
                                        text = "12"
                                    ),
                                    AnswerOption(
                                        id = "18",
                                        text = "18"
                                    ),
                                    AnswerOption(
                                        id = "45",
                                        text = "45"
                                    )
                                ),
                                correctOptionId = "18",
                                explanation = "18 is the exact point where the application's behaviour should change."
                            )
                        )
                    ),

                    Lesson(
                        id = "defects_failures_debugging",
                        title = "Defects, failures and debugging",
                        description = "Learn the difference between observing a problem and finding its cause.",
                        exercises = listOf(

                            Exercise.MultipleChoice(
                                id = "observe_failure",
                                prompt = """
                                    Requirement:

                                    Only users aged 18 or older may register.

                                    The application allows a 17-year-old user to register.

                                    What has the tester observed?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "failure",
                                        text = "A failure"
                                    ),
                                    AnswerOption(
                                        id = "debugging",
                                        text = "Debugging"
                                    ),
                                    AnswerOption(
                                        id = "success",
                                        text = "Expected behaviour"
                                    )
                                ),
                                correctOptionId = "failure",
                                explanation = "The actual behaviour does not match the expected behaviour, so the tester has observed a failure."
                            ),

                            Exercise.MultipleChoice(
                                id = "what_is_debugging",
                                prompt = """
                                    Which statement best describes debugging?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "execute_tests",
                                        text = "Executing tests to look for unexpected behaviour"
                                    ),
                                    AnswerOption(
                                        id = "find_cause",
                                        text = "Finding and fixing the cause of a failure"
                                    ),
                                    AnswerOption(
                                        id = "write_requirements",
                                        text = "Writing requirements for a feature"
                                    )
                                ),
                                correctOptionId = "find_cause",
                                explanation = "Testing can reveal failures. Debugging investigates the underlying cause and is used to correct it."
                            ),

                            Exercise.MultipleChoice(
                                id = "unexpected_result",
                                prompt = """
                                    A test produces an unexpected result.

                                    What should the tester do next?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "assume_bug",
                                        text = "Immediately assume the developer introduced a defect"
                                    ),
                                    AnswerOption(
                                        id = "investigate",
                                        text = "Investigate, reproduce the result and gather evidence"
                                    ),
                                    AnswerOption(
                                        id = "ignore",
                                        text = "Ignore it unless another tester sees it"
                                    )
                                ),
                                correctOptionId = "investigate",
                                explanation = "An unexpected result should be investigated and reproduced before conclusions are made about its cause."
                            )
                        )
                    )
                )
            )
        )
    )
}