package com.example.e_index.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.data.models.entities.Admin
import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject
import com.example.e_index.data.models.entities.Subject

@Database(
    entities = [
        Admin::class,
        Category::class,
        Student::class,
        Subject::class,
        SchoolYear::class,
        StudentCategory::class,
        StudentSubject::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun adminDao(): AdminDao
    abstract fun studentDao(): StudentDao
    abstract fun subjectDao(): SubjectDao
    abstract fun schoolYearDao(): SchoolYearDao

}