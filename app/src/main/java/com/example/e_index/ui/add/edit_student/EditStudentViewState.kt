package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.Category
import com.example.e_index.data.models.SchoolYear
import com.example.e_index.data.models.Student
import com.example.e_index.data.models.StudentCategory
import com.example.e_index.data.models.StudentSubject
import com.example.e_index.data.models.Subject
import com.example.e_index.ui.add.student.CategoryPerformance
import com.example.e_index.ui.add.student.calculateMark

data class EditStudentViewState(
    val students: List<Student> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedStudent: Student? = null,
    val selectedSchoolYear: SchoolYear? = null,
    val selectedSubject: Subject? = null,
    val addedStudentSubjects: List<Subject> = emptyList(),
    val categoryPerformanceMap: Map<Long, CategoryPerformance> = emptyMap()
)

fun EditStudentViewState.asStudentSubjectList(): List<StudentSubject> {
    return addedStudentSubjects.map { subject ->
        val relevantCategoryPerformances = categoryPerformanceMap.filterValues { it.subjectId == subject.id }
        val earnedPointsSum = relevantCategoryPerformances.values.sumOf { it.earnedPoints }
        val passed = relevantCategoryPerformances.values.all { it.hasEarnedMinimumPoints }

        StudentSubject(
            subjectId = subject.id,
            schoolYearId = subject.schoolYearId,
            mark = earnedPointsSum.calculateMark(),
            passed = passed,
            sumPoints = earnedPointsSum
        )
    }
}


fun EditStudentViewState.asStudentCategoryEntity(): List<StudentCategory> {
    return categoryPerformanceMap.values.map { categoryPerformance ->
        StudentCategory(
            categoryId = categoryPerformance.categoryId,
            schoolYearId = categoryPerformance.schoolYearId,
            points = categoryPerformance.earnedPoints,
            passed = categoryPerformance.hasEarnedMinimumPoints
        )
    }
}
