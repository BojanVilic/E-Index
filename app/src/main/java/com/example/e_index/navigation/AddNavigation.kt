package com.example.e_index.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.add.AddSchoolYearScreen
import com.example.e_index.ui.add.AddScreen
import com.example.e_index.ui.add.AddStudentScreen

private const val ADD_SCHOOL_YEAR = "add_school_year"
private const val ADD_STUDENT = "add_student"

fun NavGraphBuilder.addScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Add.route) {
        AddScreen(
            onAddSchoolYearClicked = {
                navController.navigate(ADD_SCHOOL_YEAR)
            },
            onAddStudentClicked = {
                navController.navigate(ADD_STUDENT)
            },
            onAddSubjectClicked = {}
        )
    }

    composable(ADD_SCHOOL_YEAR) {
        AddSchoolYearScreen()
    }
    composable(ADD_STUDENT) {
        AddStudentScreen()
    }
}