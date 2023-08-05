package com.example.e_index.ui.search.student_search

import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.response_models.StudentPointsDetails
import com.example.e_index.data.models.response_models.StudentSubjectDetails

data class StudentSearchViewState(
    val schoolYears: List<SchoolYear> = emptyList(),
    val selectedSchoolYear: SchoolYear? = null,
    val studentPointsDetails: List<StudentPointsDetails> = emptyList(),
    val studentSubjectDetails: List<StudentSubjectDetails> = emptyList()
)
