package com.example.e_index.ui.add.student

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.Subject

data class CategoryPerformance(
    val categoryId: Long,
    val subjectId: Long,
    val schoolYearId: Long,
    val earnedPoints: Int,
    val hasEarnedMinimumPoints: Boolean
)

fun calculateCategoryPerformance(subject: Subject, category: Category, points: String): CategoryPerformance {
    val earnedPoints = points.toIntOrNull() ?: 0
    val hasEarnedMinimumPoints = earnedPoints >= category.minPoints

    return CategoryPerformance(
        categoryId = category.id,
        subjectId = subject.id,
        schoolYearId = category.schoolYearId,
        earnedPoints = earnedPoints,
        hasEarnedMinimumPoints = hasEarnedMinimumPoints
    )
}