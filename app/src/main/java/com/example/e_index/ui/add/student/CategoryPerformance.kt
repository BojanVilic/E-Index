package com.example.e_index.ui.add.student

import com.example.e_index.data.models.Category

data class CategoryPerformance(
    val categoryId: Long,
    val schoolYearId: Long,
    val earnedPoints: Int,
    val hasEarnedMinimumPoints: Boolean
)

fun calculateCategoryPerformance(category: Category, points: String): CategoryPerformance {
    val earnedPoints = points.toIntOrNull() ?: 0
    val hasEarnedMinimumPoints = earnedPoints >= category.minPoints

    return CategoryPerformance(
        categoryId = category.id,
        schoolYearId = category.schoolYearId,
        earnedPoints = earnedPoints,
        hasEarnedMinimumPoints = hasEarnedMinimumPoints
    )
}