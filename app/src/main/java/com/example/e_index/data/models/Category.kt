package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"]
        ),
        ForeignKey(
            entity = SchoolYear::class,
            parentColumns = ["id"],
            childColumns = ["schoolYearId"]
        )
    ]
)
data class Category(
    @PrimaryKey
    val id: Long,
    val subjectId: Long,
    val schoolYearId: Long,
    val name: String,
    val maxPoints: Int,
    val minPoints: Int
)