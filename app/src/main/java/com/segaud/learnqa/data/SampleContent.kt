package com.segaud.learnqa.data

import com.segaud.learnqa.model.AnswerOption
import com.segaud.learnqa.model.Exercise
import com.segaud.learnqa.model.LearningUnit
import com.segaud.learnqa.model.Lesson
import com.segaud.learnqa.model.Subject
import com.segaud.learnqa.model.BugReportOption

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
                    ),
                    
                    Lesson(
                        id = "why_we_test",
                        title = "Why do we test software?",
                        description = "Explore how testing reduces risk and provides information about software quality.",
                        exercises = listOf(

                            Exercise.MultipleChoice(
                                id = "purpose_of_testing",
                                prompt = """
                                    What is one of the main purposes of software testing?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "prove_perfect",
                                        text = "Prove that the software has no defects"
                                    ),
                                    AnswerOption(
                                        id = "provide_information",
                                        text = "Provide information about software quality and risk"
                                    ),
                                    AnswerOption(
                                        id = "replace_debugging",
                                        text = "Replace the need for debugging"
                                    )
                                ),
                                correctOptionId = "provide_information",
                                explanation = "Testing provides information about quality and risk. It cannot prove that software contains no defects."
                            ),

                            Exercise.MultiSelect(
                                id = "testing_benefits",
                                prompt = """
                                    Which of these are benefits of software testing?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "find_defects",
                                        text = "Finding defects before users encounter them"
                                    ),
                                    AnswerOption(
                                        id = "reduce_risk",
                                        text = "Reducing the risk of software failures"
                                    ),
                                    AnswerOption(
                                        id = "quality_information",
                                        text = "Providing information about product quality"
                                    ),
                                    AnswerOption(
                                        id = "guarantee_perfection",
                                        text = "Guaranteeing that the software is perfect"
                                    )
                                ),
                                correctOptionIds = setOf(
                                    "find_defects",
                                    "reduce_risk",
                                    "quality_information"
                                ),
                                explanation = "Testing can reveal defects, reduce risk and provide information about quality, but it cannot guarantee that software is defect-free."
                            ),

                            Exercise.MultiSelect(
                                id = "tester_activities",
                                prompt = """
                                    Which activities could form part of a tester's work?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "review_requirements",
                                        text = "Reviewing requirements for problems or ambiguity"
                                    ),
                                    AnswerOption(
                                        id = "execute_tests",
                                        text = "Executing tests and comparing expected and actual results"
                                    ),
                                    AnswerOption(
                                        id = "report_defects",
                                        text = "Reporting defects with useful evidence"
                                    ),
                                    AnswerOption(
                                        id = "guarantee_no_failures",
                                        text = "Guaranteeing that no user will ever experience a failure"
                                    )
                                ),
                                correctOptionIds = setOf(
                                    "review_requirements",
                                    "execute_tests",
                                    "report_defects"
                                ),
                                explanation = "Testing includes activities such as reviewing requirements, executing tests and reporting defects. Testing cannot guarantee that failures will never occur."
                            )
                        )
                    ),
                    
                    Lesson(
                        id = "writing_bug_reports",
                        title = "Writing useful bug reports",
                        description = "Learn how to communicate defects clearly enough for another person to reproduce them.",
                        exercises = listOf(

                            Exercise.MultipleChoice(
                                id = "purpose_bug_report",
                                prompt = """
                                    What is the main purpose of a good bug report?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "blame",
                                        text = "Explain who caused the defect"
                                    ),
                                    AnswerOption(
                                        id = "reproduce",
                                        text = "Help someone understand and reproduce the problem"
                                    ),
                                    AnswerOption(
                                        id = "prove_severity",
                                        text = "Prove that the defect is high severity"
                                    )
                                ),
                                correctOptionId = "reproduce",
                                explanation = "A useful bug report communicates enough information for someone else to understand, investigate and reproduce the problem."
                            ),

                            Exercise.BugReportReview(
                                id = "choose_bug_report",
                                prompt = """
                                    A user cannot complete checkout.

                                    Which bug report would be most useful to a developer?
                                """.trimIndent(),
                                reports = listOf(

                                    BugReportOption(
                                        id = "report_a",
                                        title = "Checkout broken",
                                        steps = """
                                            1. Add an item to the basket
                                            2. Open checkout
                                        """.trimIndent(),
                                        expected = "It should work.",
                                        actual = "It doesn't."
                                    ),

                                    BugReportOption(
                                        id = "report_b",
                                        title = "Checkout button does not respond after entering valid card details",
                                        steps = """
                                            1. Add an item to basket. 
                                            2. Open checkout. 
                                            3. Enter valid delivery and card details. 
                                            4. Tap 'Pay now'.
                                            """.trimIndent(),
                                        expected = "Payment is submitted and an order confirmation is displayed.",
                                        actual = "The Pay now button shows a loading indicator briefly, then returns to its normal state. No order is created."
                                    ),

                                    BugReportOption(
                                        id = "report_c",
                                        title = "Payment bug",
                                        steps = "Customer reported a problem.",
                                        expected = "No bugs.",
                                        actual = "Payment issue."
                                    )
                                ),
                                correctReportId = "report_b",
                                explanation = "Report B gives a specific title, reproducible steps, expected behaviour and the actual behaviour observed."
                            ),

                            Exercise.MultiSelect(
                                id = "useful_bug_fields",
                                prompt = """
                                    Which information is normally useful when reporting a reproducible defect?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "steps",
                                        text = "Steps to reproduce"
                                    ),
                                    AnswerOption(
                                        id = "expected_actual",
                                        text = "Expected and actual results"
                                    ),
                                    AnswerOption(
                                        id = "environment",
                                        text = "Relevant device, browser or environment information"
                                    ),
                                    AnswerOption(
                                        id = "developer_blame",
                                        text = "The name of the developer you think caused it"
                                    )
                                ),
                                correctOptionIds = setOf(
                                    "steps",
                                    "expected_actual",
                                    "environment"
                                ),
                                explanation = "Useful defect reports contain reproducible evidence and context. Assigning blame is not part of describing the defect."
                            )
                        )
                    ),
                        
                    Lesson(
                        id = "investigating_defects",
                        title = "Investigating unexpected behaviour",
                        description = "Learn what evidence to gather before reporting a defect.",
                        exercises = listOf(

                            Exercise.MissingBugReportInfo(
                                id = "missing_expected_result",
                                prompt = """
                                    Review this bug report.

                                    Which important piece of information is missing?
                                """.trimIndent(),
                                report = BugReportOption(
                                    id = "login_error_report",
                                    title = "Login shows an error after entering valid credentials",
                                    steps = """
                                        1. Open the app
                                        2. Enter a registered email address
                                        3. Enter the correct password
                                        4. Tap "Log in"
                                    """.trimIndent(),
                                    expected = "Not provided",
                                    actual = "A 'Something went wrong' message appears and the user remains on the login screen."
                                ),
                                options = listOf(
                                    AnswerOption(
                                        id = "expected",
                                        text = "The expected behaviour"
                                    ),
                                    AnswerOption(
                                        id = "developer",
                                        text = "The name of the developer who worked on login"
                                    ),
                                    AnswerOption(
                                        id = "estimate",
                                        text = "How long the fix should take"
                                    )
                                ),
                                correctOptionId = "expected",
                                explanation = "The report explains how to reproduce the problem and what actually happens, but it does not state what should happen instead."
                            ),

                            Exercise.MultipleChoice(
                                id = "intermittent_failure",
                                prompt = """
                                    A tester sees a failure once but cannot immediately reproduce it.

                                    What is the best next step?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "ignore",
                                        text = "Ignore it because it only happened once"
                                    ),
                                    AnswerOption(
                                        id = "investigate",
                                        text = "Try to reproduce it and gather information about the conditions"
                                    ),
                                    AnswerOption(
                                        id = "report_certain",
                                        text = "Report it immediately as a confirmed defect"
                                    )
                                ),
                                correctOptionId = "investigate",
                                explanation = "Intermittent behaviour should be investigated. Repeating the test and recording the conditions can help identify when and why the failure occurs."
                            ),

                            Exercise.MultiSelect(
                                id = "useful_investigation_evidence",
                                prompt = """
                                    Which information could help investigate an unexpected failure?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "device",
                                        text = "Device, operating system or browser version"
                                    ),
                                    AnswerOption(
                                        id = "logs",
                                        text = "Relevant logs or error messages"
                                    ),
                                    AnswerOption(
                                        id = "recording",
                                        text = "A screenshot or screen recording"
                                    ),
                                    AnswerOption(
                                        id = "guess",
                                        text = "A guess about which developer caused it"
                                    )
                                ),
                                correctOptionIds = setOf(
                                    "device",
                                    "logs",
                                    "recording"
                                ),
                                explanation = "Environment details, logs and visual evidence can all help reproduce and diagnose a failure. Guessing who caused it does not provide useful evidence."
                            )
                        )
                    )
                )
            ),
                
            LearningUnit(
                id = "test_design_basics",
                title = "Unit 2 — Test Design Basics",
                description = "Learn how testers turn requirements and risks into effective tests.",
                lessons = listOf(

                    Lesson(
                        id = "test_conditions_and_cases",
                        title = "Test conditions and test cases",
                        description = "Learn how to decide what needs testing and turn it into a repeatable test.",
                        exercises = listOf(

                            Exercise.MultipleChoice(
                                id = "test_condition_definition",
                                prompt = """
                                    Requirement:

                                    A customer can reset their password using a registered email address.

                                    Which is an example of a test condition?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "condition",
                                        text = "Password reset with a registered email address"
                                    ),
                                    AnswerOption(
                                        id = "step",
                                        text = "Tap the Reset Password button"
                                    ),
                                    AnswerOption(
                                        id = "bug",
                                        text = "Reset password is broken"
                                    )
                                ),
                                correctOptionId = "condition",
                                explanation = "A test condition identifies something that should be tested. Detailed actions belong in a test case."
                            ),

                            Exercise.MultiSelect(
                                id = "test_case_contents",
                                prompt = """
                                    Which information can form part of a test case?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "preconditions",
                                        text = "Preconditions"
                                    ),
                                    AnswerOption(
                                        id = "steps",
                                        text = "Test steps"
                                    ),
                                    AnswerOption(
                                        id = "expected",
                                        text = "Expected result"
                                    ),
                                    AnswerOption(
                                        id = "developer",
                                        text = "The developer who wrote the feature"
                                    )
                                ),
                                correctOptionIds = setOf(
                                    "preconditions",
                                    "steps",
                                    "expected"
                                ),
                                explanation = "A test case commonly describes its setup, actions and expected outcome. The developer's identity is not part of the test itself."
                            ),

                            Exercise.MultipleChoice(
                                id = "repeatable_test",
                                prompt = """
                                    Why should a test case be clear and repeatable?
                                """.trimIndent(),
                                options = listOf(
                                    AnswerOption(
                                        id = "same_result",
                                        text = "So another tester can execute it consistently"
                                    ),
                                    AnswerOption(
                                        id = "longer",
                                        text = "So the test documentation is as long as possible"
                                    ),
                                    AnswerOption(
                                        id = "avoid_exploration",
                                        text = "So exploratory testing is never required"
                                    )
                                ),
                                correctOptionId = "same_result",
                                explanation = "A clear test case allows another person to understand the setup, actions and expected result and execute it consistently."
                            )
                        )
                    )
                )
            )
        )
    )
}