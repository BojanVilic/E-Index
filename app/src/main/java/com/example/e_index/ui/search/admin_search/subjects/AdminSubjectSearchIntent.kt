package com.example.e_index.ui.search.admin_search.subjects

import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Subject

sealed class AdminSubjectSearchIntent {
    data class SchoolYearChanged(val schoolYear: SchoolYear) : AdminSubjectSearchIntent()
    data class SubjectChanged(val subject: Subject) : AdminSubjectSearchIntent()
    data class PassedOnlyFilterChanged(val value: Boolean) : AdminSubjectSearchIntent()
    data class MarkFilterChanged(val value: String) : AdminSubjectSearchIntent()
}
