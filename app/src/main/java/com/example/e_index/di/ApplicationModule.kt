package com.example.e_index.di

import android.content.Context
import androidx.room.Room
import com.example.e_index.data.AppDatabase
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.CategoryDao
import com.example.e_index.data.dao.GradeDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun getAdminDao(appDatabase: AppDatabase): AdminDao {
        return appDatabase.adminDao()
    }

    @Singleton
    @Provides
    fun getCategoryDao(appDatabase: AppDatabase): CategoryDao {
        return appDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun getGradeDao(appDatabase: AppDatabase): GradeDao {
        return appDatabase.gradeDao()
    }

    @Singleton
    @Provides
    fun getStudentDao(appDatabase: AppDatabase): StudentDao {
        return appDatabase.studentDao()
    }

    @Singleton
    @Provides
    fun getSubjectDao(appDatabase: AppDatabase): SubjectDao {
        return appDatabase.subjectDao()
    }

    @Provides
    @Singleton
    fun getAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "e_index_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}