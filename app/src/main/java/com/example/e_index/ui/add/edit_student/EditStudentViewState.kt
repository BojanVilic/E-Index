package com.example.e_index.ui.add.edit_student

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.ui.add.student.CategoryPerformance
import com.example.e_index.ui.add.student.calculateMark

data class EditStudentViewState(
    val students: List<Student> = emptyList(),
    val studentSubjects: List<Subject> = emptyList(),
    val schoolYears: List<SchoolYear> = emptyList(),
    val allSubjects: List<Subject> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedStudent: Student? = null,
    val selectedSchoolYear: SchoolYear? = null,
    val selectedSubject: Subject? = null,
    val categoryPerformanceMap: Map<Long, CategoryPerformance> = emptyMap(),
    val screenType: ScreenType = ScreenType.EDIT_SUBJECT_POINTS
)

fun EditStudentViewState.asStudentSubjectList(): List<StudentSubject> {
    return studentSubjects.map { subject ->
        val relevantCategoryPerformances = categoryPerformanceMap.filterValues { it.subjectId == subject.id }
        val earnedPointsSum = relevantCategoryPerformances.values.sumOf { it.earnedPoints }
        val passed = relevantCategoryPerformances.values.all { it.hasEarnedMinimumPoints } && earnedPointsSum > 51

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

enum class ScreenType {
    ADD_NEW_SUBJECT,
    EDIT_SUBJECT_POINTS
}
