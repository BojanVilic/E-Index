package com.example.e_index.ui.add.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.ui.add.AddRepository
import com.example.e_index.ui.add.uimodels.CategoryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSubjectViewModel @Inject constructor(
    private val addRepository: AddRepository
): ViewModel() {

    private val _addSubjectState = MutableStateFlow(AddSubjectViewState())
    val addSubjectState: StateFlow<AddSubjectViewState>
        get() = _addSubjectState.asStateFlow()

    suspend fun processIntent(intent: AddSubjectIntent) {
        when (intent) {
            is AddSubjectIntent.AddCategory -> _addSubjectState.update { it.copy(
                categories = it.categories.plus(intent.category),
                currentCategory = CategoryUi()
            ) }
            is AddSubjectIntent.EnterSubjectName -> _addSubjectState.update { it.copy(subjectName = intent.name) }
            is AddSubjectIntent.SelectSchoolYear -> _addSubjectState.update { it.copy(selectedSchoolYear = intent.schoolYear) }
            is AddSubjectIntent.InsertSubject -> {
                addRepository.insertSubjectWithCategories(
                    _addSubjectState.value.subjectName,
                    _addSubjectState.value.categories,
                    _addSubjectState.value.selectedSchoolYear!!.id
                )
                _addSubjectState.update {
                    it.copy(
                        selectedSchoolYear = null,
                        subjectName = "",
                        categories = emptyList(),
                        currentCategory = CategoryUi()
                    )
                }
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _addSubjectState.update { it.copy(schoolYears = addRepository.getAllSchoolYears()) }
        }
    }
}