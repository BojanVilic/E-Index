package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.Student
import com.example.e_index.data.models.Subject
import com.example.e_index.ui.add.student.CategoryPerformance

sealed class EditStudentIntent {
    data class StudentChanged(val student: Student): EditStudentIntent()
    data class EditSubjectDetailsClicked(val subjectId: Long): EditStudentIntent()
    data class CategoryPointsChanged(val categoryPerformance: CategoryPerformance) : EditStudentIntent()
    data class UpsertStudentSubjectDetails(val subject: Subject) : EditStudentIntent()
}
