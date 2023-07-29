package com.example.e_index.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.add.screens.AddAdminScreen
import com.example.e_index.ui.add.screens.AddSchoolYearScreen
import com.example.e_index.ui.add.screens.AddScreen
import com.example.e_index.ui.add.screens.AddStudentScreen
import com.example.e_index.ui.add.screens.AddSubjectScreen

private const val ADD_SCHOOL_YEAR = "add_school_year"
private const val ADD_STUDENT = "add_student"
private const val ADD_ADMIN = "add_admin"
private const val ADD_SUBJECT = "add_subject"

fun NavGraphBuilder.addScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Add.route) {
        AddScreen(
            onAddSchoolYearClicked = {
                navController.navigate(ADD_SCHOOL_YEAR)
            },
            onAddStudentClicked = {
                navController.navigate(ADD_STUDENT)
            },
            onAddSubjectClicked = {
                navController.navigate(ADD_SUBJECT)
            },
            onAddAdminClicked = {
                navController.navigate(ADD_ADMIN)
            }
        )
    }

    composable(ADD_SCHOOL_YEAR) {
        AddSchoolYearScreen()
    }
    composable(ADD_STUDENT) {
        AddStudentScreen()
    }
    composable(ADD_ADMIN) {
        AddAdminScreen()
    }
    composable(ADD_SUBJECT) {
        AddSubjectScreen()
    }
}