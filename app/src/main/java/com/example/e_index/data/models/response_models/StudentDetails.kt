package com.example.e_index.data.models.response_models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject

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
