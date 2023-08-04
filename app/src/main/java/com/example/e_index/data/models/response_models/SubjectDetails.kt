package com.example.e_index.data.models.response_models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Subject

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