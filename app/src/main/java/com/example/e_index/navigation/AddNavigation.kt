package com.example.e_index.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.e_index.ui.add.screens.AddAdminScreen
import com.example.e_index.ui.add.screens.AddSchoolYearScreen
import com.example.e_index.ui.add.screens.AddScreen
import com.example.e_index.ui.add.screens.AddStudentScreen
import com.example.e_index.ui.add.screens.AddSubjectScreen
import com.example.e_index.ui.add.screens.SubjectDetailsEntryScreen
import com.example.e_index.ui.add.student.AddStudentViewModel

private const val ADD_SCHOOL_YEAR = "add_school_year"
private const val ADD_STUDENT = "add_student"
private const val ADD_ADMIN = "add_admin"
private const val ADD_SUBJECT = "add_subject"
private const val SUBJECT_DETAILS_ENTRY = "subject_details_entry"

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
    navigation(
        startDestination = ADD_STUDENT,
        route = "add_student_route"
    ) {
        composable(ADD_STUDENT) {
            val viewModel = it.sharedViewModel<AddStudentViewModel>(navController)

            AddStudentScreen(
                addStudentViewModel = viewModel,
                onAddSubjectDetailsClicked = {
                    navController.navigate(SUBJECT_DETAILS_ENTRY)
                }
            )
        }
        composable(SUBJECT_DETAILS_ENTRY) {
            val viewModel = it.sharedViewModel<AddStudentViewModel>(navController)

            SubjectDetailsEntryScreen(
                addStudentViewModel = viewModel
            )
        }
    }
    composable(ADD_ADMIN) {
        AddAdminScreen()
    }
    composable(ADD_SUBJECT) {
        AddSubjectScreen()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}