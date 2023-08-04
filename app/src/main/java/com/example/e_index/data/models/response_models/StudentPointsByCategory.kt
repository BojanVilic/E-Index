package com.example.e_index.data.models.response_models

data class StudentPointsByCategory(
    val categoryId: Long,
    val points: Int,
    val subjectId: Long,
    val schoolYearId: Long,
    val passed: Boolean
)