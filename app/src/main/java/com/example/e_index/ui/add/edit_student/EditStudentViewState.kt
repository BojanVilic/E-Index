package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.Category
import com.example.e_index.data.models.SchoolYear
import com.example.e_index.data.models.Student
import com.example.e_index.data.models.StudentPointsByCategory
import com.example.e_index.data.models.Subject
import com.example.e_index.ui.add.student.CategoryPerformance

data class EditStudentViewState(
    val students: List<Student> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val categories: List<Category> = emptyList(),
    val categoryPoints: List<StudentPointsByCategory> = emptyList(),
    val selectedStudent: Student? = null,
    val selectedSchoolYear: SchoolYear? = null,
    val selectedSubject: Subject? = null,
    val addedStudentSubjects: List<Subject> = emptyList(),
    val categoryPerformanceMap: Map<Long, CategoryPerformance> = emptyMap()
)
