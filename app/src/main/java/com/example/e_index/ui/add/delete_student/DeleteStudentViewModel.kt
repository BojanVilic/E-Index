package com.example.e_index.ui.add.delete_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.models.Student
import com.example.e_index.ui.add.AddRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteStudentViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    private val _studentList = MutableStateFlow(emptyList<Student>())
    val studentList: StateFlow<List<Student>>
        get() = _studentList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            addRepository.getAllStudents().collect {
                _studentList.value = it
            }
        }
    }

    fun deleteStudent(student: Student?) {
        student?.let {
            viewModelScope.launch(Dispatchers.IO) {
                addRepository.deleteStudent(it)
            }
        }
    }
}