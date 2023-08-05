package com.example.e_index.data.models.response_models

data class StudentPointsDetails(
    val categoryId: Long = -1,
    val categoryName: String = "",
    val categoryPoints: Int = 0,
    val subjectId: Long = -1,
    val subjectName: String = "",
    val schoolYearId: Long = -1,
    val schoolYearName: String = "",
    val passed: Boolean = false
)