package com.example.e_index.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.search.SearchScreen
import com.example.e_index.ui.search.searchOptionNavigationMap

const val SEARCH_STUDENTS = "search_students"
const val SEARCH_STUDENT_SUBJECT_INFO = "search_student_subject_info"
const val SEARCH_CATEGORIES_FOR_SUBJECT = "search_categories_for_subject"
const val SEARCH_STUDENT_POINTS_BY_SUBJECT = "search_student_points_by_subject"
const val SEARCH_SUBJECT_PASSED_STATUS = "search_subject_passed_status"

fun NavGraphBuilder.searchScreen(navController: NavHostController) {
    composable(TopLevelDestinations.Search.route) {
        SearchScreen(
            onMenuOptionClicked = { searchOption ->
                navController.navigate(searchOptionNavigationMap[searchOption]!!)
            }
        )
    }
}