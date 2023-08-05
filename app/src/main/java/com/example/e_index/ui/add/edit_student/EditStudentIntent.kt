package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.ui.add.student.CategoryPerformance

sealed class EditStudentIntent {
    data class StudentChanged(val student: Student): EditStudentIntent()
    data class EditStudentCategoryPointsClicked(val subjectId: Long): EditStudentIntent()
    data class CategoryPointsChanged(val categoryPerformance: CategoryPerformance) : EditStudentIntent()
    data class InsertNewSubjectDetails(val subject: Subject) : EditStudentIntent()
    object UpdateCategoryPoints : EditStudentIntent()
    object AddNewSubjectClicked : EditStudentIntent()
    data class SchoolYearChanged(val schoolYear: SchoolYear) : EditStudentIntent()
    data class SubjectChanged(val subject: Subject) : EditStudentIntent()
}