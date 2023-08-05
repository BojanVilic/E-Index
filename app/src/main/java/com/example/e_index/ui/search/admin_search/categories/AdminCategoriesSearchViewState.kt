package com.example.e_index.ui.search.admin_search.categories

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Subject

data class AdminCategoriesSearchViewState(
    val schoolYears: List<SchoolYear> = emptyList(),
    val selectedSchoolYear: SchoolYear? = null,
    val subjects: List<Subject> = emptyList(),
    val selectedSubject: Subject? = null,
    val categories: List<Category> = emptyList()
)
