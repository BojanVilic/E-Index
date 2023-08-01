package com.example.e_index.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class StudentDetails(
    @Embedded
    val student: Student,
    @Relation(
        parentColumn = "id",
        entityColumn = "studentId"
    )
    val studentSubjects: List<StudentSubject>,
    @Relation(
        parentColumn = "id",
        entityColumn = "studentId"
    )
    val studentCategories: List<StudentCategory>
)
