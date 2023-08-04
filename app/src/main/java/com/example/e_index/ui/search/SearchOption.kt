package com.example.e_index.ui.search

import androidx.annotation.StringRes
import com.example.e_index.R
import com.example.e_index.navigation.SEARCH_CATEGORIES_FOR_SUBJECT
import com.example.e_index.navigation.SEARCH_STUDENTS
import com.example.e_index.navigation.SEARCH_STUDENT_POINTS_BY_SUBJECT
import com.example.e_index.navigation.SEARCH_STUDENT_SUBJECT_INFO
import com.example.e_index.navigation.SEARCH_SUBJECT_PASSED_STATUS

sealed class SearchOption(
    @StringRes val titleRes: Int
) {
    object Students : SearchOption(titleRes = R.string.search_students)
    object StudentSubjectsInfo : SearchOption(titleRes = R.string.search_student_subjects_info)
    object CategoriesForSubject : SearchOption(titleRes = R.string.search_categories_for_subject)

    object StudentPointsBySubject : SearchOption(titleRes = R.string.search_student_points_for_subjects)
    object SubjectsPassedStatus : SearchOption(titleRes = R.string.search_student_passed_subjects)
}

val adminSearchOptions = listOf(
    SearchOption.Students,
    SearchOption.StudentSubjectsInfo,
    SearchOption.CategoriesForSubject
)

val studentSearchOptions = listOf(
    SearchOption.StudentPointsBySubject,
    SearchOption.SubjectsPassedStatus
)

val searchOptionNavigationMap = mapOf(
    SearchOption.Students to SEARCH_STUDENTS,
    SearchOption.StudentSubjectsInfo to SEARCH_STUDENT_SUBJECT_INFO,
    SearchOption.CategoriesForSubject to SEARCH_CATEGORIES_FOR_SUBJECT,
    SearchOption.StudentPointsBySubject to SEARCH_STUDENT_POINTS_BY_SUBJECT,
    SearchOption.SubjectsPassedStatus to SEARCH_SUBJECT_PASSED_STATUS
)