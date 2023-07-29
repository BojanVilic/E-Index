package com.example.e_index.di

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.ui.add.AddRepository
import com.example.e_index.ui.login.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun getLoginRepository(
        adminDao: AdminDao,
        studentDao: StudentDao
    ): LoginRepository {
        return LoginRepository(
            adminDao,
            studentDao
        )
    }

    @Provides
    @Singleton
    fun getAddRepository(
        adminDao: AdminDao,
        studentDao: StudentDao,
        subjectDao: SubjectDao,
        schoolYearDao: SchoolYearDao
    ): AddRepository {
        return AddRepository(
            adminDao,
            studentDao,
            subjectDao,
            schoolYearDao
        )
    }
}