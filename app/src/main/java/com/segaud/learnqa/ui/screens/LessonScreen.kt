package com.segaud.learnqa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LessonScreen(
    onBack: () -> Unit
) {

    var answer by rememberSaveable {
        mutableStateOf<Boolean?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        TextButton(
            onClick = onBack
        ) {
            Text("← Back")
        }

        Text(
            text = "What is software testing?",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Exercise 1",
            style = MaterialTheme.typography.labelLarge
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = "Requirement",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "A password must contain at least 8 characters."
                )

                Text(
                    text = "The application accepts the password:"
                )

                Text(
                    text = "abcdefg",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Text(
            text = "Does the application meet the requirement?",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = {
                answer = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Yes")
        }

        OutlinedButton(
            onClick = {
                answer = false
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("No")
        }

        if (answer != null) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    if (answer == false) {

                        Text(
                            text = "Correct!",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "The password contains only 7 characters, but the requirement says it must contain at least 8."
                        )

                    } else {

                        Text(
                            text = "Not quite.",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = "Count the characters in \"abcdefg\" and compare that with the requirement."
                        )
                    }
                }
            }
        }
    }
}