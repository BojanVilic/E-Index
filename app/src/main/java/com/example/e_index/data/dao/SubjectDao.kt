package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.e_index.data.models.Subject

@Dao
interface SubjectDao {

    @Insert
    suspend fun insertSubject(subject: Subject): Long

}
