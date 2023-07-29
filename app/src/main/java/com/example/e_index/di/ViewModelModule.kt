package com.example.e_index.di

import com.example.e_index.ui.add.AddRepository
import com.example.e_index.ui.add.AddViewModel
import com.example.e_index.ui.add.student.AddStudentViewModel
import com.example.e_index.ui.login.LoginRepository
import com.example.e_index.ui.login.LoginViewModel
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

}