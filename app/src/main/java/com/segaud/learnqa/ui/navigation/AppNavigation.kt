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
import com.segaud.learnqa.ui.screens.UnitLessonsScreen

object Routes {

    const val LEARN = "learn"
    const val PROGRESS = "progress"
    const val SETTINGS = "settings"

    const val UNIT = "unit/{unitId}"
    const val LESSON = "lesson/{lessonId}"

    fun unit(unitId: String): String {
        return "unit/$unitId"
    }

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
                onOpenUnit = { unitId ->

                    navController.navigate(
                        Routes.unit(unitId)
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
            route = Routes.UNIT,
            arguments = listOf(
                navArgument("unitId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val unitId =
                backStackEntry.arguments
                    ?.getString("unitId")

            val units =
                SampleContent.qaFundamentals.units

            val unitIndex =
                units.indexOfFirst { unit ->
                    unit.id == unitId
                }

            if (unitIndex >= 0) {

                val unit =
                    units[unitIndex]

                val previousUnit =
                    if (unitIndex == 0) {
                        null
                    } else {
                        units[unitIndex - 1]
                    }

                UnitLessonsScreen(
                    unit = unit,
                    unitNumber = unitIndex + 1,
                    previousUnit = previousUnit,
                    progressRepository = progressRepository,
                    onBack = {
                        navController.popBackStack()
                    },
                    onStartLesson = { lessonId ->

                        navController.navigate(
                            Routes.lesson(lessonId)
                        )
                    }
                )
            }
        }
        
        composable(
            route = Routes.UNIT,
            arguments = listOf(
                navArgument("unitId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val unitId =
                backStackEntry.arguments
                    ?.getString("unitId")

            val units =
                SampleContent.qaFundamentals.units

            val unitIndex =
                units.indexOfFirst { unit ->
                    unit.id == unitId
                }

            if (unitIndex >= 0) {

                val unit =
                    units[unitIndex]

                val previousUnit =
                    if (unitIndex == 0) {
                        null
                    } else {
                        units[unitIndex - 1]
                    }

                UnitLessonsScreen(
                    unit = unit,
                    unitNumber = unitIndex + 1,
                    previousUnit = previousUnit,
                    progressRepository = progressRepository,
                    onBack = {
                        navController.popBackStack()
                    },
                    onStartLesson = { lessonId ->

                        navController.navigate(
                            Routes.lesson(lessonId)
                        )
                    }
                )
            }
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