package com.example.e_index.ui.add

import androidx.lifecycle.ViewModel
import com.example.e_index.data.models.SchoolYear
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    suspend fun insertSchoolYear(schoolYear: SchoolYear) {
        addRepository.insertSchoolYear(schoolYear)
    }

}