package com.example.e_index.data.models.response_models

data class StudentSubjectStatus(
    val subjectId: Long,
    val subjectName: String,
    val schoolYearId: Long,
    val schoolYearName: String,
    val passed: Boolean
)