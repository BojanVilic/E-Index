package com.example.e_index.di

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

}