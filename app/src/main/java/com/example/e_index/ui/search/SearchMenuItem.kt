package com.example.e_index.ui.search

import androidx.annotation.StringRes
import com.example.e_index.R
import com.example.e_index.navigation.SEARCH_CATEGORIES_FOR_SUBJECT
import com.example.e_index.navigation.SEARCH_STUDENTS
import com.example.e_index.navigation.SEARCH_STUDENT_POINTS_BY_SUBJECT
import com.example.e_index.navigation.SEARCH_STUDENT_SUBJECT_INFO
import com.example.e_index.navigation.SEARCH_SUBJECT_PASSED_STATUS

sealed class SearchMenuItem(
    @StringRes val titleRes: Int,
    val destination: String
) {
    object Students : SearchMenuItem(
        titleRes = R.string.search_students,
        destination = SEARCH_STUDENTS
    )

    object StudentSubjectsInfo : SearchMenuItem(
        titleRes = R.string.search_student_subjects_info,
        destination = SEARCH_STUDENT_SUBJECT_INFO
    )

    object CategoriesForSubject : SearchMenuItem(
        titleRes = R.string.search_categories_for_subject,
        destination = SEARCH_CATEGORIES_FOR_SUBJECT
    )

    object StudentPointsBySubject : SearchMenuItem(
        titleRes = R.string.search_student_points_for_subjects,
        destination = SEARCH_STUDENT_POINTS_BY_SUBJECT
    )

    object SubjectsPassedStatus : SearchMenuItem(
        titleRes = R.string.search_student_passed_subjects,
        destination = SEARCH_SUBJECT_PASSED_STATUS
    )
}

val adminSearchMenuItems = listOf(
    SearchMenuItem.Students,
    SearchMenuItem.StudentSubjectsInfo,
    SearchMenuItem.CategoriesForSubject
)

val studentSearchMenuItems = listOf(
    SearchMenuItem.StudentPointsBySubject,
    SearchMenuItem.SubjectsPassedStatus
)