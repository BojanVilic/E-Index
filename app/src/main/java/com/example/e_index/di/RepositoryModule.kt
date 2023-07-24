package com.example.e_index.di

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.ui.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun getLoginRepository(
        adminDao: AdminDao,
        studentDao: StudentDao
    ): LoginRepository {
        return LoginRepository(
            adminDao,
            studentDao
        )
    }
}