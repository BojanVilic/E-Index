package com.example.e_index.ui.search.student_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.ui.login.LoginRepository
import com.example.e_index.ui.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
): ViewModel() {

    private val _studentSearchViewState = MutableStateFlow(StudentSearchViewState())
    val studentSearchViewState: StateFlow<StudentSearchViewState>
        get() = _studentSearchViewState.asStateFlow()

    fun processIntent(intent: StudentSearchIntent) {
        when (intent) {
            is StudentSearchIntent.SchoolYearChanged -> {
                _studentSearchViewState.update {
                    it.copy(selectedSchoolYear = intent.schoolYear)
                }
                getAllSubjectDetailsForStudent(intent.schoolYear)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            LoginRepository.currentStudentId?.let { studentId ->
                _studentSearchViewState.update { it.copy(
                    studentPointsDetails = searchRepository.getStudentPointsDetailsForAllSubjects(studentId),
                    schoolYears = searchRepository.getAllSchoolYears()
                    )
                }
            }
        }
    }

    private fun getAllSubjectDetailsForStudent(schoolYear: SchoolYear) {
        viewModelScope.launch(Dispatchers.IO) {
            LoginRepository.currentStudentId?.let { studentId ->
                _studentSearchViewState.update { it.copy(studentSubjectDetails = searchRepository.getAllSubjectDetailsForStudent(studentId, schoolYear.id)) }
            }
        }
    }
}