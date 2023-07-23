package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.e_index.data.models.Admin

@Dao
interface AdminDao {
    // Requirement 2
    @Insert
    suspend fun insertAdmin(admin: Admin): Long
}
