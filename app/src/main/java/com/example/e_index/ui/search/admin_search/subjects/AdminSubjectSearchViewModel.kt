package com.example.e_index.ui.search.admin_search.subjects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.response_models.StudentInfo
import com.example.e_index.ui.add.AddRepository
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
class AdminSubjectSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val addRepository: AddRepository
): ViewModel() {

    private val _adminSubjectSearchViewState = MutableStateFlow(AdminSubjectSearchViewState())
    val adminSubjectSearchViewState: StateFlow<AdminSubjectSearchViewState>
        get() = _adminSubjectSearchViewState.asStateFlow()

    private var originalStudentList: List<StudentInfo> = emptyList()

    fun processIntent(intent: AdminSubjectSearchIntent) {
        when (intent) {
            is AdminSubjectSearchIntent.SchoolYearChanged -> {
                _adminSubjectSearchViewState.update {
                    it.copy(
                        selectedSchoolYear = intent.schoolYear,
                        selectedSubject = null
                    )
                }
                clearFilters()
                getSubjectsForSchoolYear(intent.schoolYear.id)
            }
            is AdminSubjectSearchIntent.SubjectChanged -> {
                _adminSubjectSearchViewState.update {
                    it.copy(selectedSubject = intent.subject)
                }
                clearFilters()
                getStudentsForSearchParams()
            }
            is AdminSubjectSearchIntent.MarkFilterChanged -> {
                val newMarkValue = if ((intent.value.toIntOrNull()?: 0) in 5..10) intent.value.toInt() else 5
                _adminSubjectSearchViewState.update { it.copy(
                    minMarkFilterValue = intent.value,
                    minMarkFilter = newMarkValue
                ) }
                filterStudentList()
            }
            is AdminSubjectSearchIntent.PassedOnlyFilterChanged -> {
                _adminSubjectSearchViewState.update { it.copy(passedOnlyFilter = intent.value) }
                filterStudentList()
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _adminSubjectSearchViewState.update { it.copy(schoolYears = addRepository.getAllSchoolYears()) }
        }
    }

    private fun getSubjectsForSchoolYear(schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _adminSubjectSearchViewState.update { it.copy(subjects = addRepository.getAllSubjects(schoolYearId)) }
        }
    }

    private fun getStudentsForSearchParams() {
        viewModelScope.launch(Dispatchers.IO) {
            val students = searchRepository.getStudentsForSearchParams(
                subjectId = _adminSubjectSearchViewState.value.selectedSubject!!.id,
                schoolYearId = _adminSubjectSearchViewState.value.selectedSchoolYear!!.id
            )
            originalStudentList = students
            _adminSubjectSearchViewState.update { it.copy(students = students) }
        }
    }

    private fun filterStudentList() {
        val filterPassedOnly = _adminSubjectSearchViewState.value.passedOnlyFilter
        val minMarkFilter = _adminSubjectSearchViewState.value.minMarkFilter

        val studentList = originalStudentList.filter { student ->
            student.mark >= minMarkFilter && (!filterPassedOnly || student.passed)
        }

        _adminSubjectSearchViewState.update { it.copy(students = studentList) }
    }

    private fun clearFilters() {
        _adminSubjectSearchViewState.update { it.copy(
            passedOnlyFilter = false,
            minMarkFilter = 5,
            minMarkFilterValue = ""
        )}
    }
}