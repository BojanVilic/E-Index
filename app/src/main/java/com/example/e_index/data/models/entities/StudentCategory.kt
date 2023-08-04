package com.example.e_index.data.models.entities

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
    val categoryId: Long = 0,
    val studentId: Long = 0,
    val schoolYearId: Long = 0,
    val points: Int = 0,
    val passed: Boolean = false
)
