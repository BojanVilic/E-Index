package com.example.e_index.ui.search.admin_search.students

import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.response_models.StudentSubjectStatus

data class AdminStudentSearchViewState(
    val students: List<Student> = emptyList(),
    val selectedStudent: Student? = null,
    val studentSubjectStatusList: List<StudentSubjectStatus> = emptyList(),
    val passedOnlyFilter: Boolean = false,
    val failedOnlyFilter: Boolean = false
)