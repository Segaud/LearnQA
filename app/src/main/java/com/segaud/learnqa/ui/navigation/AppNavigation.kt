package com.segaud.learnqa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.segaud.learnqa.ui.screens.LearnScreen
import com.segaud.learnqa.ui.screens.LessonScreen
import com.segaud.learnqa.ui.screens.ProgressScreen
import com.segaud.learnqa.ui.screens.SettingsScreen
import com.segaud.learnqa.data.SampleContent

object Routes {
    const val LEARN = "learn"
    const val PROGRESS = "progress"
    const val SETTINGS = "settings"
    const val LESSON = "lesson"
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.LEARN,
        modifier = modifier
    ) {

        composable(Routes.LEARN) {
            LearnScreen(
                onStartLesson = {
                    navController.navigate(Routes.LESSON)
                }
            )
        }

        composable(Routes.PROGRESS) {
            ProgressScreen()
        }

        composable(Routes.SETTINGS) {
            SettingsScreen()
        }

        composable(Routes.LESSON) {
            val lesson = SampleContent
                .qaFundamentals
                .units
                .first()
                .lessons
                .first()

            LessonScreen(
                lesson = lesson,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}