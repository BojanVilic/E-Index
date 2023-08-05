package com.example.e_index.ui.search.admin_search.subjects

import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.data.models.response_models.StudentInfo

data class AdminSubjectSearchViewState(
    val schoolYears: List<SchoolYear> = emptyList(),
    val selectedSchoolYear: SchoolYear? = null,
    val subjects: List<Subject> = emptyList(),
    val selectedSubject: Subject? = null,
    val passedOnlyFilter: Boolean = false,
    val minMarkFilter: Int = 5,
    val minMarkFilterValue: String = "",
    val students: List<StudentInfo> = emptyList()
)