package com.segaud.learnqa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.segaud.learnqa.data.SampleContent
import com.segaud.learnqa.data.progress.ProgressRepository
import com.segaud.learnqa.ui.screens.LearnScreen
import com.segaud.learnqa.ui.screens.LessonScreen
import com.segaud.learnqa.ui.screens.ProgressScreen
import com.segaud.learnqa.ui.screens.SettingsScreen

object Routes {

    const val LEARN = "learn"
    const val PROGRESS = "progress"
    const val SETTINGS = "settings"

    const val LESSON = "lesson/{lessonId}"

    fun lesson(lessonId: String): String {
        return "lesson/$lessonId"
    }
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    progressRepository: ProgressRepository,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = Routes.LEARN,
        modifier = modifier
    ) {

        composable(Routes.LEARN) {

            LearnScreen(
                progressRepository = progressRepository,
                onStartLesson = { lessonId ->

                    navController.navigate(
                        Routes.lesson(lessonId)
                    )
                }
            )
        }

        composable(Routes.PROGRESS) {

            ProgressScreen(
                progressRepository = progressRepository
            )
        }

        composable(Routes.SETTINGS) {

            SettingsScreen()
        }

        composable(
            route = Routes.LESSON,
            arguments = listOf(
                navArgument("lessonId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val lessonId =
                backStackEntry.arguments
                    ?.getString("lessonId")

            val lesson =
                SampleContent
                    .qaFundamentals
                    .units
                    .flatMap { unit ->
                        unit.lessons
                    }
                    .firstOrNull { lesson ->
                        lesson.id == lessonId
                    }

            if (lesson != null) {

                LessonScreen(
                    lesson = lesson,
                    progressRepository = progressRepository,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}