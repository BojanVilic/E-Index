package com.example.e_index.ui.search.student_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.response_models.StudentPointsDetails
import com.example.e_index.ui.login.LoginRepository
import com.example.e_index.ui.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private val _studentPointsDetailsState = MutableStateFlow<List<StudentPointsDetails>>(listOf())
    val studentPointsDetailsState: StateFlow<List<StudentPointsDetails>>
        get() = _studentPointsDetailsState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            LoginRepository.currentStudentId?.let { studentId ->
                _studentPointsDetailsState.value = searchRepository.getStudentPointsDetailsForAllSubjects(studentId)
            }
        }
    }
}