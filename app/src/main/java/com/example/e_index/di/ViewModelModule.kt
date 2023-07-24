package com.example.e_index.di

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
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
        adminDao: AdminDao,
        studentDao: StudentDao
    ): LoginViewModel {
        return LoginViewModel(
            adminDao,
            studentDao
        )
    }

}