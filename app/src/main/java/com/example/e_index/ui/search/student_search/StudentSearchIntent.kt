package com.example.e_index.ui.search.student_search

import com.example.e_index.data.models.entities.SchoolYear

sealed class StudentSearchIntent {
    data class SchoolYearChanged(val schoolYear: SchoolYear) : StudentSearchIntent()
}
