package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "student_subject",
    primaryKeys = ["subjectId", "studentId"],
    foreignKeys = [
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"]
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
data class StudentSubject(
    val subjectId: Long,
    val studentId: Long = 0,
    val schoolYearId: Long,
    val mark: Int,
    val passed: Boolean,
    val sumPoints: Int
)
