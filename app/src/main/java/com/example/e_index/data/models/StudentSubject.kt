package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "student_subject",
    primaryKeys = ["studentId", "subjectId", "schoolYearId"],
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SchoolYear::class,
            parentColumns = ["id"],
            childColumns = ["schoolYearId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StudentSubject(
    val studentId: Long,
    val subjectId: Long,
    val schoolYearId: Long
)
