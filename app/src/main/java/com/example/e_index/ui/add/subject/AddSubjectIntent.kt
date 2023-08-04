package com.example.e_index.ui.add.subject

import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear

sealed class AddSubjectIntent {
    data class EnterSubjectName(val name: String) : AddSubjectIntent()
    data class AddCategory(val category: Category) : AddSubjectIntent()
    data class SelectSchoolYear(val schoolYear: SchoolYear?) : AddSubjectIntent()
    object InsertSubject : AddSubjectIntent()
}
