package com.example.e_index.data.models.entities

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
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val subjectId: Long = -1,
    val schoolYearId: Long = -1,
    val name: String,
    val maxPoints: Int,
    val minPoints: Int
)