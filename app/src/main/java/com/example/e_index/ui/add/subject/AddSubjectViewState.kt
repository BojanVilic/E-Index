package com.example.e_index.ui.add.subject

import com.example.e_index.data.models.Category
import com.example.e_index.data.models.SchoolYear

data class AddSubjectViewState(
    val subjectName: String = "",
    val categories: List<Category> = emptyList(),
    val schoolYears: List<SchoolYear> = emptyList(),
    val selectedSchoolYearId: Long? = null
)