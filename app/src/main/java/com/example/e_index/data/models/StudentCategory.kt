package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "student_category",
    primaryKeys = ["categoryId", "studentId"],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"]
        ),
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SchoolYear::class,
            parentColumns = ["id"],
            childColumns = ["schoolYearId"]
        )
    ]
)
data class StudentCategory(
    val categoryId: Long,
    val studentId: Long,
    val schoolYearId: Long,
    val points: Int,
    val passed: Boolean
)
