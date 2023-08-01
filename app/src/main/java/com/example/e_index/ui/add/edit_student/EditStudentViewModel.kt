package com.example.e_index.ui.add.edit_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.SubjectDetails
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
class EditStudentViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    private val _editStudentState = MutableStateFlow(EditStudentViewState())
    val editStudentState: StateFlow<EditStudentViewState>
        get() = _editStudentState.asStateFlow()

    fun processIntent(intent: EditStudentIntent) {
        when (intent) {
            is EditStudentIntent.StudentChanged -> {
                _editStudentState.update { it.copy(selectedStudent = intent.student) }
                getSubjectDetails()
            }
            is EditStudentIntent.EditSubjectDetailsClicked -> loadCategories(intent.subjectId)
        }
    }

    private fun loadCategories(subjectId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.value.selectedStudent?.id?.let { studentId ->
                val subjectDetailsList = addRepository.getSubjectDetails(studentId)

                _editStudentState.update { it.copy(categories = subjectDetailsList.find { subjectDetails -> subjectDetails.subject.id == subjectId }!!.categories) }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            addRepository.getAllStudents().collect { students ->
                _editStudentState.update { it.copy(students = students) }
            }
        }
    }

    private fun getSubjectDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            _editStudentState.value.selectedStudent?.id?.let { studentId ->
                val subjectDetailsList = addRepository.getSubjectDetails(studentId)

                _editStudentState.update { it.copy(subjects = subjectDetailsList.map { subjectDetails -> subjectDetails.subject }) }

//                studentDetailsList.forEach { studentDetails ->
//                    Log.d("SubjectDetails", "Student name: ${studentDetails.student.name}")
//                    studentDetails.studentSubjects.forEach { studentSubject ->
//                        Log.d("SubjectDetails", "Subject name: ${subjectDetailsList.find { it.subject.id == studentSubject.subjectId }!!.subject.name}")
//                        Log.d("SubjectDetails", "Sum points: ${studentSubject.sumPoints}")
//                    }
//                    studentDetails.studentCategories.forEach { studentCategory ->
//                        val categoryName = findCategoryName(studentCategory.categoryId, subjectDetailsList)
//                        Log.d("SubjectDetails", "Category name: ${categoryName ?: "N/A"}")
//                        Log.d("SubjectDetails", "Category id: ${studentCategory.categoryId}")
//                        Log.d("SubjectDetails", "Category points: ${studentCategory.points}")
//                    }
//                    Log.d("SubjectDetails", "------------------------------------")
//                }
            }
        }
    }

    fun findCategoryName(categoryId: Long, subjectDetailsList: List<SubjectDetails>): String? {
        val subjectDetails = subjectDetailsList.find { it.categories.any { it.id == categoryId } }
        return subjectDetails?.categories?.find { it.id == categoryId }?.name
    }
}