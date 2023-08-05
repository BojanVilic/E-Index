package com.example.e_index.ui.add.student

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject
import com.example.e_index.data.models.entities.Subject

data class AddStudentViewState(
    val name: String = "",
    val surname: String = "",
    val indexNumber: String = "",
    val jmbg: String = "",
    val username: String = "",
    val password: String = "",
    val schoolYears: List<SchoolYear> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val categories: List<Category> = emptyList(),
    val selectedSubject: Subject? = null,
    val selectedSchoolYear: SchoolYear? = null,
    val addedStudentSubjects: List<Subject> = emptyList(),
    val categoryPerformanceMap: Map<Long, CategoryPerformance> = emptyMap()
)

fun AddStudentViewState.asStudentSubjectList(): List<StudentSubject> {
    return addedStudentSubjects.map { subject ->
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


fun AddStudentViewState.asStudentCategoryEntity(): List<StudentCategory> {
    return categoryPerformanceMap.values.map { categoryPerformance ->
        StudentCategory(
            categoryId = categoryPerformance.categoryId,
            schoolYearId = categoryPerformance.schoolYearId,
            points = categoryPerformance.earnedPoints,
            passed = categoryPerformance.hasEarnedMinimumPoints
        )
    }
}

fun Int.calculateMark(): Int {
    return when (this) {
        in 51..60 -> 6
        in 61..70 -> 7
        in 71..80 -> 8
        in 81..90 -> 9
        in 91..100 -> 10
        else -> 5
    }
}