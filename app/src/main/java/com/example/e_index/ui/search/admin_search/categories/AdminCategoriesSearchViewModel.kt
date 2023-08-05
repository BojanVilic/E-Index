package com.example.e_index.ui.search.admin_search.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class AdminCategoriesSearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val addRepository: AddRepository
): ViewModel() {

    private val _adminCategoriesSearchViewState = MutableStateFlow(AdminCategoriesSearchViewState())
    val adminCategoriesSearchViewState: StateFlow<AdminCategoriesSearchViewState>
        get() = _adminCategoriesSearchViewState.asStateFlow()

    fun processIntent(intent: AdminCategoriesSearchIntent) {
        when (intent) {
            is AdminCategoriesSearchIntent.SchoolYearChanged -> {
                _adminCategoriesSearchViewState.update {
                    it.copy(
                        selectedSchoolYear = intent.schoolYear,
                        selectedSubject = null
                    )
                }
                getSubjectsForSchoolYear(intent.schoolYear.id)
            }
            is AdminCategoriesSearchIntent.SubjectChanged -> {
                _adminCategoriesSearchViewState.update { it.copy(selectedSubject = intent.subject) }
                getCategoriesForSubject(intent.subject.id)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _adminCategoriesSearchViewState.update { it.copy(schoolYears = addRepository.getAllSchoolYears()) }
        }
    }

    private fun getSubjectsForSchoolYear(schoolYearId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _adminCategoriesSearchViewState.update { it.copy(subjects = addRepository.getAllSubjects(schoolYearId)) }
        }
    }

    private fun getCategoriesForSubject(subjectId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _adminCategoriesSearchViewState.update { it.copy(categories = addRepository.getCategoriesForSubject(
                subjectId = subjectId,
                schoolYearId = _adminCategoriesSearchViewState.value.selectedSchoolYear!!.id
            )) }
        }
    }
}