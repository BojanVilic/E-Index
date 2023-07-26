package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Transaction
import com.example.e_index.data.models.Student

@Dao
interface StudentDao {

    @Transaction
    @Insert
    suspend fun insertStudent(student: Student)
}

