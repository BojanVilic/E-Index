package com.example.e_index.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.CategoryDao
import com.example.e_index.data.dao.GradeDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.data.models.Admin
import com.example.e_index.data.models.Category
import com.example.e_index.data.models.Grade
import com.example.e_index.data.models.Student
import com.example.e_index.data.models.Subject

@Database(
    entities = [
        Admin::class,
        Category::class,
        Grade::class,
        Student::class,
        Subject::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun adminDao(): AdminDao
    abstract fun categoryDao(): CategoryDao
    abstract fun gradeDao(): GradeDao
    abstract fun studentDao(): StudentDao
    abstract fun subjectDao(): SubjectDao

}