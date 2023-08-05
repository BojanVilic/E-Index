package com.example.e_index.data.models.response_models

data class StudentSubjectDetails(
    val subjectName: String = "",
    val schoolYearName: String = "",
    val mark: Int,
    val passed: Boolean
)
