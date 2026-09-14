package com.segaud.learnqa.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.segaud.learnqa.ui.screens.LearnScreen
import com.segaud.learnqa.ui.screens.ProgressScreen
import com.segaud.learnqa.ui.screens.SettingsScreen

@Composable
fun AppShell() {

    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {

                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Text("📘") },
                    label = { Text("Learn") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Text("📊") },
                    label = { Text("Progress") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Text("⚙️") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> LearnScreen()
                1 -> ProgressScreen()
                2 -> SettingsScreen()
            }
        }
    }
}