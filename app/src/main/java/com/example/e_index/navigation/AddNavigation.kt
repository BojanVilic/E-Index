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
import com.example.e_index.ui.add.AddScreen
import com.example.e_index.ui.add.admin_and_school_year.AddAdminScreen
import com.example.e_index.ui.add.admin_and_school_year.AddSchoolYearScreen
import com.example.e_index.ui.add.delete_student.DeleteStudentsScreen
import com.example.e_index.ui.add.edit_student.EditStudentSubjectScreen
import com.example.e_index.ui.add.edit_student.EditStudentViewModel
import com.example.e_index.ui.add.edit_student.EditSubjectDetailsEntryScreen
import com.example.e_index.ui.add.student.AddStudentScreen
import com.example.e_index.ui.add.student.AddStudentViewModel
import com.example.e_index.ui.add.subject.AddSubjectScreen
import com.example.e_index.ui.add.subject.SubjectDetailsEntryScreen

const val ADD_SCHOOL_YEAR = "add_school_year"
const val ADD_STUDENT = "add_student"
const val ADD_SUBJECT = "add_subject"
const val ADD_ADMIN = "add_admin"
const val DELETE_STUDENT = "delete_student"
const val EDIT_STUDENT = "edit_student"
const val SUBJECT_DETAILS_ENTRY = "subject_details_entry"
const val EDIT_SUBJECT_DETAILS_ENTRY = "edit_subject_details_entry"

fun NavGraphBuilder.addScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Add.route) {
        AddScreen(
            onMenuItemClicked = { addMenuItem ->
                navController.navigate(addMenuItem.destination)
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

    navigation(
        startDestination = EDIT_STUDENT,
        route = "edit_student_subject_route"
    ) {
        composable(EDIT_STUDENT) {
            val viewModel = it.sharedViewModel<EditStudentViewModel>(navController)

            EditStudentSubjectScreen(
                editStudentViewModel = viewModel,
                onSubjectDetailsClicked = {
                    navController.navigate(EDIT_SUBJECT_DETAILS_ENTRY)
                }
            )
        }
        composable(EDIT_SUBJECT_DETAILS_ENTRY) {
            val viewModel = it.sharedViewModel<EditStudentViewModel>(navController)

            EditSubjectDetailsEntryScreen(
                editStudentViewModel = viewModel
            )
        }
    }
    composable(ADD_ADMIN) {
        AddAdminScreen()
    }
    composable(ADD_SUBJECT) {
        AddSubjectScreen()
    }
    composable(DELETE_STUDENT) {
        DeleteStudentsScreen()
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