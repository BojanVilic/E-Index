package com.example.e_index.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.add.AddSchoolYearScreen
import com.example.e_index.ui.add.AddScreen

private const val ADD_SCHOOL_YEAR = "add_school_year"

fun NavGraphBuilder.addScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Add.route) {
        AddScreen(
            onAddSchoolYearClicked = {
                navController.navigate(ADD_SCHOOL_YEAR)
            },
            onAddStudentClicked = {},
            onAddSubjectClicked = {}
        )
    }

    composable(ADD_SCHOOL_YEAR) {
        AddSchoolYearScreen()
    }
}