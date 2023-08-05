package com.example.e_index.ui.search.admin_search.categories

import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Subject

sealed class AdminCategoriesSearchIntent {
    data class SchoolYearChanged(val schoolYear: SchoolYear) : AdminCategoriesSearchIntent()
    data class SubjectChanged(val subject: Subject) : AdminCategoriesSearchIntent()
}
