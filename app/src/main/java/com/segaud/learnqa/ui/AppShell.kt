package com.segaud.learnqa.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.segaud.learnqa.ui.navigation.AppNavigation
import com.segaud.learnqa.ui.navigation.Routes
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.segaud.learnqa.data.progress.ProgressRepository

@Composable
fun AppShell() {

    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    val showBottomBar = currentRoute != Routes.LESSON
    
    val context = LocalContext.current

    val progressRepository = remember {
        ProgressRepository(context.applicationContext)
    }

    Scaffold(
        bottomBar = {

            if (showBottomBar) {

                NavigationBar {

                    NavigationBarItem(
                        selected = currentRoute == Routes.LEARN,
                        onClick = {
                            navController.navigate(Routes.LEARN) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("📘")
                        },
                        label = {
                            Text("Learn")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Routes.PROGRESS,
                        onClick = {
                            navController.navigate(Routes.PROGRESS) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("📊")
                        },
                        label = {
                            Text("Progress")
                        }
                    )

                    NavigationBarItem(
                        selected = currentRoute == Routes.SETTINGS,
                        onClick = {
                            navController.navigate(Routes.SETTINGS) {
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Text("⚙️")
                        },
                        label = {
                            Text("Settings")
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        AppNavigation(
            navController = navController,
            progressRepository = progressRepository,
            modifier = Modifier.padding(innerPadding)
        )
    }
}