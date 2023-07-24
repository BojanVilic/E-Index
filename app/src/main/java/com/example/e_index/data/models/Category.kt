package com.example.e_index.data.models

import androidx.room.Entity

@Entity(
    tableName = "categories",
    primaryKeys = ["subjectId", "schoolYearId"]
)
data class Category(
    val subjectId: Long,
    val schoolYearId: Long,
    val name: String,
    val maxPoints: Int,
    val minPoints: Int
)