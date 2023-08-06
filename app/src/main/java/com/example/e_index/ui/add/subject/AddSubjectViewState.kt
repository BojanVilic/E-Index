package com.example.e_index.ui.add.subject

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.ui.add.uimodels.CategoryUi

data class AddSubjectViewState(
    val subjectName: String = "",
    val categories: List<Category> = emptyList(),
    val schoolYears: List<SchoolYear> = emptyList(),
    val selectedSchoolYear: SchoolYear? = null,
    val currentCategory: CategoryUi = CategoryUi()
)