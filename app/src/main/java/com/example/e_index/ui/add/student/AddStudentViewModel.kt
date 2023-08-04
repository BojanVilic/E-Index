package com.example.e_index.ui.add.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.entities.Student
import com.example.e_index.ui.add.AddRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStudentViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    private val _addStudentState = MutableStateFlow(AddStudentViewState())
    val addStudentState: StateFlow<AddStudentViewState>
        get() = _addStudentState.asStateFlow()

    fun processIntent(intent: AddStudentIntent) {
        when (intent) {
            is AddStudentIntent.NameChanged -> _addStudentState.update { it.copy(name = intent.input) }
            is AddStudentIntent.SurnameChanged -> _addStudentState.update { it.copy(surname = intent.input) }
            is AddStudentIntent.IndexNumberChanged -> _addStudentState.update { it.copy(indexNumber = intent.input) }
            is AddStudentIntent.JmbgChanged -> _addStudentState.update { it.copy(jmbg = intent.input) }
            is AddStudentIntent.UsernameChanged -> _addStudentState.update { it.copy(username = intent.input) }
            is AddStudentIntent.PasswordChanged -> _addStudentState.update { it.copy(password = intent.input) }
            is AddStudentIntent.SchoolYearChanged -> {
                _addStudentState.update {
                    it.copy(selectedSchoolYear = intent.schoolYear)
                }
                getSubjectsForSchoolYear(intent.schoolYear.id)
                resetSubjectAndCategories()
            }
            is AddStudentIntent.SubjectChanged -> {
                _addStudentState.update {
                    it.copy(selectedSubject = intent.subject)
                }
                _addStudentState.value.selectedSchoolYear?.let { schoolYear ->
                    getCategoriesForSubjectAndSchoolYear(intent.subject.id, schoolYear.id)
                }
            }
            is AddStudentIntent.CategoryPointsChanged -> {
                val updatedCategoryPerformanceMap = _addStudentState.value.categoryPerformanceMap.toMutableMap()
                updatedCategoryPerformanceMap[intent.categoryPerformance.categoryId] = intent.categoryPerformance

                _addStudentState.update { it.copy(categoryPerformanceMap = updatedCategoryPerformanceMap) }
            }
            is AddStudentIntent.AddStudentSubjectDetails -> {
                val categoryDefaultValuesMap = _addStudentState.value.categories.associate { category ->
                        category.id to CategoryPerformance(
                            categoryId = category.id,
                            subjectId = category.subjectId,
                            schoolYearId = intent.subject.schoolYearId,
                            earnedPoints = 0,
                            hasEarnedMinimumPoints = false
                        )
                    }

                val updatedCategoryPerformanceMap =  categoryDefaultValuesMap + _addStudentState.value.categoryPerformanceMap

                _addStudentState.update {
                    it.copy(
                        addedStudentSubjects = it.addedStudentSubjects + intent.subject,
                        categoryPerformanceMap = updatedCategoryPerformanceMap
                    )
                }
                resetSubjectDetailsEntryFields()
            }
            is AddStudentIntent.InsertStudent -> insertStudentWithRelevantData()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _addStudentState.update { it.copy(schoolYears = addRepository.getAllSchoolYears()) }
        }
    }

    private fun resetSubjectAndCategories() {
        _addStudentState.update { it.copy(selectedSubject = null, categories = emptyList()) }
    }

    private fun getSubjectsForSchoolYear(schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _addStudentState.update { it.copy(subjects = addRepository.getAllSubjects(schoolYearId)) }
        }
    }

    private fun getCategoriesForSubjectAndSchoolYear(subjectId: Long, schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _addStudentState.update { it.copy(categories = addRepository.getCategoriesForSubject(subjectId, schoolYearId)) }
        }
    }

    private fun insertStudentWithRelevantData() {
        viewModelScope.launch(Dispatchers.IO) {
            val student = Student(
                name = _addStudentState.value.name,
                surname = _addStudentState.value.surname,
                indexNumber = _addStudentState.value.indexNumber,
                jmbg = _addStudentState.value.jmbg,
                username = _addStudentState.value.username,
                password = _addStudentState.value.password
            )

            addRepository.insertStudentWithRelevantData(
                student = student,
                studentSubjectList = _addStudentState.value.asStudentSubjectList(),
                studentCategoryList = _addStudentState.value.asStudentCategoryEntity()
            )
        }
    }

    private fun resetSubjectDetailsEntryFields() {
        _addStudentState.update { it.copy(selectedSubject = null, selectedSchoolYear = null, subjects = emptyList()) }
    }
}