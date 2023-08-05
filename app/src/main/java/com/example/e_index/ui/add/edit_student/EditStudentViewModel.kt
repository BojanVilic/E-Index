package com.example.e_index.ui.add.edit_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.data.models.response_models.SubjectDetails
import com.example.e_index.ui.add.AddRepository
import com.example.e_index.ui.add.student.CategoryPerformance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditStudentViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    private val _editStudentState = MutableStateFlow(EditStudentViewState())
    val editStudentState: StateFlow<EditStudentViewState>
        get() = _editStudentState.asStateFlow()

    private var subjectDetailsList: List<SubjectDetails> = emptyList()

    fun processIntent(intent: EditStudentIntent) {
        when (intent) {
            is EditStudentIntent.StudentChanged -> {
                _editStudentState.update { it.copy(selectedStudent = intent.student) }
                getSubjectsForStudent()
            }
            is EditStudentIntent.EditStudentCategoryPointsClicked -> loadSubjectDetails(intent.subjectId)
            is EditStudentIntent.CategoryPointsChanged -> {
                val updatedCategoryPerformanceMap = _editStudentState.value.categoryPerformanceMap.toMutableMap()
                updatedCategoryPerformanceMap[intent.categoryPerformance.categoryId] = intent.categoryPerformance

                _editStudentState.update { it.copy(categoryPerformanceMap = updatedCategoryPerformanceMap) }
            }
            is EditStudentIntent.InsertNewSubjectDetails -> insertNewSubject(intent.subject)
            is EditStudentIntent.AddNewSubjectClicked -> {
                resetSubjectDetailsEntryFields()
            }
            is EditStudentIntent.SchoolYearChanged -> {
                _editStudentState.update {
                    it.copy(selectedSchoolYear = intent.schoolYear)
                }
                getSubjectsForSchoolYear(intent.schoolYear.id)
                resetSubjectAndCategories()
            }
            is EditStudentIntent.SubjectChanged -> {
                _editStudentState.update {
                    it.copy(selectedSubject = intent.subject)
                }
                _editStudentState.value.selectedSchoolYear?.let { schoolYear ->
                    getCategoriesForSubjectAndSchoolYear(intent.subject.id, schoolYear.id)
                }
            }
            is EditStudentIntent.UpdateCategoryPoints -> updateCategoryPoints()
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            addRepository.getAllStudents().collect { students ->
                _editStudentState.update { it.copy(students = students) }
                _editStudentState.update { it.copy(schoolYears = addRepository.getAllSchoolYears()) }
            }
        }
    }

    private fun resetSubjectAndCategories() {
        _editStudentState.update { it.copy(selectedSubject = null, categories = emptyList()) }
    }

    private fun getSubjectsForSchoolYear(schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.update { it.copy(allSubjects = addRepository.getAllSubjects(schoolYearId)) }
        }
    }

    private fun getCategoriesForSubjectAndSchoolYear(subjectId: Long, schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.update { it.copy(categories = addRepository.getCategoriesForSubject(subjectId, schoolYearId)) }
        }
    }

    private fun loadSubjectDetails(subjectId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedSubjectDetails = subjectDetailsList.find { subjectDetails -> subjectDetails.subject.id == subjectId }!!

            _editStudentState.update {
                it.copy(
                    categories = selectedSubjectDetails.categories,
                    selectedSchoolYear = selectedSubjectDetails.schoolYear,
                    selectedSubject = selectedSubjectDetails.subject,
                    screenType = ScreenType.EDIT_SUBJECT_POINTS
                )
            }
        }
    }

    private fun getSubjectsForStudent() {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.value.selectedStudent?.id?.let { studentId ->
                addRepository.getSubjectDetails(studentId).collect { subjectsList ->
                    subjectDetailsList = subjectsList

                    val studentPointsByCategoryForAllSubjects = addRepository.getAllStudentPointsByCategory(studentId = studentId)
                    val categoryPerformanceMap = mutableMapOf<Long, CategoryPerformance>()

                    studentPointsByCategoryForAllSubjects.forEach { studentPoints ->
                        categoryPerformanceMap[studentPoints.categoryId] = CategoryPerformance(
                            categoryId = studentPoints.categoryId,
                            subjectId = studentPoints.subjectId,
                            schoolYearId = studentPoints.schoolYearId,
                            earnedPoints = studentPoints.points,
                            hasEarnedMinimumPoints = studentPoints.passed
                        )
                    }

                    _editStudentState.update { it.copy(
                        studentSubjects = subjectDetailsList.map { subjectDetails -> subjectDetails.subject },
                        categoryPerformanceMap = categoryPerformanceMap
                    )}
                }
            }
        }
    }

    private fun insertNewSubject(subject: Subject) {
        val categoryDefaultValuesMap = _editStudentState.value.categories.associate { category ->
            category.id to CategoryPerformance(
                categoryId = category.id,
                subjectId = category.subjectId,
                schoolYearId = subject.schoolYearId,
                earnedPoints = 0,
                hasEarnedMinimumPoints = false
            )
        }

        val updatedCategoryPerformanceMap =  categoryDefaultValuesMap + _editStudentState.value.categoryPerformanceMap

        _editStudentState.update {
            it.copy(
                studentSubjects = it.studentSubjects + subject,
                categoryPerformanceMap = updatedCategoryPerformanceMap
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val student = _editStudentState.value.selectedStudent!!

            addRepository.insertStudentWithRelevantData(
                student = student,
                studentSubjectList = _editStudentState.value.asStudentSubjectList(),
                studentCategoryList = _editStudentState.value.asStudentCategoryEntity()
            )
        }
    }

    private fun updateCategoryPoints() {
        viewModelScope.launch(Dispatchers.IO) {
            val student = _editStudentState.value.selectedStudent!!

            addRepository.insertStudentWithRelevantData(
                student = student,
                studentSubjectList = _editStudentState.value.asStudentSubjectList(),
                studentCategoryList = _editStudentState.value.asStudentCategoryEntity()
            )
        }
    }

    private fun resetSubjectDetailsEntryFields() {
        _editStudentState.update { it.copy(
            selectedSubject = null,
            selectedSchoolYear = null,
            screenType = ScreenType.ADD_NEW_SUBJECT
        )}
    }
}