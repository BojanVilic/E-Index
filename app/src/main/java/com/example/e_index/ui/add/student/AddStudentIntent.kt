package com.example.e_index.ui.add.student

import com.example.e_index.data.models.SchoolYear
import com.example.e_index.data.models.Subject

sealed class AddStudentIntent {
    data class NameChanged(val input: String) : AddStudentIntent()
    data class SurnameChanged(val input: String) : AddStudentIntent()
    data class IndexNumberChanged(val input: String) : AddStudentIntent()
    data class JmbgChanged(val input: String) : AddStudentIntent()
    data class UsernameChanged(val input: String) : AddStudentIntent()
    data class PasswordChanged(val input: String) : AddStudentIntent()
    data class SchoolYearChanged(val schoolYear: SchoolYear) : AddStudentIntent()
    data class SubjectChanged(val subject: Subject) : AddStudentIntent()
    data class CategoryPointsChanged(val categoryPerformance: CategoryPerformance) : AddStudentIntent()
    object InsertStudent : AddStudentIntent()
    data class AddStudentSubjectDetails(val subject: Subject) : AddStudentIntent()
}
