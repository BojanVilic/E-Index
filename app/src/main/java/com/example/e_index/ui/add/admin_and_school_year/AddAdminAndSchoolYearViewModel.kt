package com.example.e_index.ui.add.admin_and_school_year

import androidx.lifecycle.ViewModel
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.ui.add.AddRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddAdminAndSchoolYearViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    suspend fun insertSchoolYear(schoolYear: SchoolYear) {
        addRepository.insertSchoolYear(schoolYear)
    }

    suspend fun insertAdmin(adminUi: AdminUi) {
        addRepository.insertAdmin(adminUi.asEntity())
    }
}