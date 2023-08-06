package com.example.e_index.di

import com.example.e_index.ui.add.AddRepository
import com.example.e_index.ui.add.AddViewModel
import com.example.e_index.ui.add.delete_student.DeleteStudentViewModel
import com.example.e_index.ui.add.edit_student.EditStudentViewModel
import com.example.e_index.ui.add.student.AddStudentViewModel
import com.example.e_index.ui.add.subject.AddSubjectViewModel
import com.example.e_index.ui.login.LoginRepository
import com.example.e_index.ui.login.LoginViewModel
import com.example.e_index.ui.search.SearchRepository
import com.example.e_index.ui.search.admin_search.categories.AdminCategoriesSearchViewModel
import com.example.e_index.ui.search.admin_search.students.AdminStudentSearchViewModel
import com.example.e_index.ui.search.admin_search.subjects.AdminSubjectSearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun getLoginViewModel(
        loginRepository: LoginRepository
    ): LoginViewModel {
        return LoginViewModel(
            loginRepository
        )
    }

    @Provides
    fun getAddViewModel(
        addRepository: AddRepository
    ): AddViewModel {
        return AddViewModel(
            addRepository
        )
    }

    @Provides
    fun getAddStudentViewModel(
        addRepository: AddRepository
    ): AddStudentViewModel {
        return AddStudentViewModel(
            addRepository
        )
    }

    @Provides
    fun getAddSubjectViewModel(
        addRepository: AddRepository
    ): AddSubjectViewModel {
        return AddSubjectViewModel(
            addRepository
        )
    }

    @Provides
    fun getDeleteStudentViewModel(
        addRepository: AddRepository
    ): DeleteStudentViewModel {
        return DeleteStudentViewModel(
            addRepository
        )
    }

    @Provides
    fun getEditStudentViewModel(
        addRepository: AddRepository
    ): EditStudentViewModel {
        return EditStudentViewModel(
            addRepository
        )
    }

    @Provides
    fun getAdminSubjectSearchViewModel(
        searchRepository: SearchRepository,
        addRepository: AddRepository
    ): AdminSubjectSearchViewModel {
        return AdminSubjectSearchViewModel(
            searchRepository,
            addRepository
        )
    }

    @Provides
    fun getAdminStudentSearchViewModel(
        searchRepository: SearchRepository,
        addRepository: AddRepository
    ): AdminStudentSearchViewModel {
        return AdminStudentSearchViewModel(
            searchRepository,
            addRepository
        )
    }

    @Provides
    fun getAdminCategoriesSearchViewModel(
        addRepository: AddRepository
    ): AdminCategoriesSearchViewModel {
        return AdminCategoriesSearchViewModel(
            addRepository
        )
    }
}