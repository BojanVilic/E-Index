package com.example.e_index.ui.search.admin_search.students

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.response_models.StudentSubjectStatus
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
class AdminStudentSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val addRepository: AddRepository
): ViewModel() {

    private val _adminStudentSearchViewState = MutableStateFlow(AdminStudentSearchViewState())
    val adminStudentSearchViewState: StateFlow<AdminStudentSearchViewState>
        get() = _adminStudentSearchViewState.asStateFlow()

    private var originalStudentSubjectStatusList: List<StudentSubjectStatus> = emptyList()

    fun processIntent(intent: AdminStudentSearchIntent) {
        when (intent) {
            is AdminStudentSearchIntent.StudentChanged -> {
                _adminStudentSearchViewState.update { it.copy(selectedStudent = intent.student) }
                getPassedAndFailedSubjectsForStudent(intent.student.id)
            }

            is AdminStudentSearchIntent.FailedOnlyFilterChanged -> {
                _adminStudentSearchViewState.update { it.copy(failedOnlyFilter = intent.value, passedOnlyFilter = false) }
                filterStudentSubjectStatusList()
            }
            is AdminStudentSearchIntent.PassedOnlyFilterChanged -> {
                _adminStudentSearchViewState.update { it.copy(passedOnlyFilter = intent.value, failedOnlyFilter = false) }
                filterStudentSubjectStatusList()
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            addRepository.getAllStudents().collect { students ->
                _adminStudentSearchViewState.update { it.copy(students = students) }
            }
        }
    }

    private fun getPassedAndFailedSubjectsForStudent(studentId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val studentSubjectStatusList = searchRepository.getPassedAndFailedSubjectsForStudent(studentId)
            originalStudentSubjectStatusList = studentSubjectStatusList
            _adminStudentSearchViewState.update { it.copy(studentSubjectStatusList = studentSubjectStatusList) }
        }
    }

    private fun filterStudentSubjectStatusList() {
        val studentList = originalStudentSubjectStatusList.filter { student ->
            when {
                _adminStudentSearchViewState.value.failedOnlyFilter -> !student.passed
                _adminStudentSearchViewState.value.passedOnlyFilter -> student.passed
                else -> true
            }
        }

        _adminStudentSearchViewState.update { it.copy(studentSubjectStatusList = studentList) }
    }
}