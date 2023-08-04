package com.example.e_index.ui.add.edit_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.Subject
import com.example.e_index.data.models.SubjectDetails
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
                getSubjectDetails()
            }
            is EditStudentIntent.EditSubjectDetailsClicked -> loadSubjectDetails(intent.subjectId)
            is EditStudentIntent.CategoryPointsChanged -> {
                val updatedCategoryPerformanceMap = _editStudentState.value.categoryPerformanceMap.toMutableMap()
                updatedCategoryPerformanceMap[intent.categoryPerformance.categoryId] = intent.categoryPerformance

                _editStudentState.update { it.copy(categoryPerformanceMap = updatedCategoryPerformanceMap) }
            }

            is EditStudentIntent.UpsertStudentSubjectDetails -> upsertStudentWithRelevantData(intent.subject)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            addRepository.getAllStudents().collect { students ->
                _editStudentState.update { it.copy(students = students) }
            }
        }
    }

    private fun loadSubjectDetails(subjectId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedSubjectDetails = subjectDetailsList.find { subjectDetails -> subjectDetails.subject.id == subjectId }!!
            val studentPointsByCategory = addRepository.getStudentPointsByCategory(
                _editStudentState.value.selectedStudent!!.id,
                subjectId,
                selectedSubjectDetails.schoolYear.id
            )

            val categoryPerformanceMap = studentPointsByCategory.associate { it.categoryId to CategoryPerformance(
                categoryId = it.categoryId,
                subjectId = it.subjectId,
                schoolYearId = it.schoolYearId,
                earnedPoints = it.points,
                hasEarnedMinimumPoints = it.passed
            )}

            _editStudentState.update {
                it.copy(
                    categories = selectedSubjectDetails.categories,
                    selectedSchoolYear = selectedSubjectDetails.schoolYear,
                    selectedSubject = selectedSubjectDetails.subject,
                    categoryPerformanceMap = categoryPerformanceMap
                )
            }
        }
    }

    private fun getSubjectDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.value.selectedStudent?.id?.let { studentId ->
                subjectDetailsList = addRepository.getSubjectDetails(studentId)
                _editStudentState.update { it.copy(subjects = subjectDetailsList.map { subjectDetails -> subjectDetails.subject }) }
            }
        }
    }

    private fun upsertStudentWithRelevantData(subject: Subject) {
        _editStudentState.update {
            it.copy(
                addedStudentSubjects = it.addedStudentSubjects + subject
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
}