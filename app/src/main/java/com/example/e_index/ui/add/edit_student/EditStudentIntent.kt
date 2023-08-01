package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.Student

sealed class EditStudentIntent {
    data class StudentChanged(val student: Student): EditStudentIntent()
    data class EditSubjectDetailsClicked(val subjectId: Long): EditStudentIntent()
}
