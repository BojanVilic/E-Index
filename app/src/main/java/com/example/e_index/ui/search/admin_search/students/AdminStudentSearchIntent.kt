package com.example.e_index.ui.search.admin_search.students

import com.example.e_index.data.models.entities.Student

sealed class AdminStudentSearchIntent {
    data class StudentChanged(val student: Student) : AdminStudentSearchIntent()
    data class PassedOnlyFilterChanged(val value: Boolean) : AdminStudentSearchIntent()
    data class FailedOnlyFilterChanged(val value: Boolean) : AdminStudentSearchIntent()
}