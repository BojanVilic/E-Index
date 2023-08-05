package com.example.e_index.data.models.response_models

import androidx.room.Embedded
import com.example.e_index.data.models.entities.Student

data class StudentInfo(
    @Embedded val student: Student,
    val mark: Int,
    val passed: Boolean
)