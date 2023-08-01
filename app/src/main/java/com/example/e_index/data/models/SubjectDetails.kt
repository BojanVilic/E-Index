package com.example.e_index.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class SubjectDetails(
    @Embedded val subject: Subject,
    @Relation(
        entity = Category::class,
        parentColumn = "id",
        entityColumn = "subjectId"
    )
    val categories: List<Category>,
    @Relation(
        entity = SchoolYear::class,
        parentColumn = "schoolYearId",
        entityColumn = "id"
    )
    val schoolYear: SchoolYear
)