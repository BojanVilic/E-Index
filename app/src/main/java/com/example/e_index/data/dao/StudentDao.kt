package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.Student

@Dao
interface StudentDao {

    @Transaction
    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM students WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getStudentByUsernameAndPassword(username: String, password: String): Student?
}

