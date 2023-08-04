package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.e_index.data.models.entities.Admin

@Dao
interface AdminDao {

    @Insert
    suspend fun insertAdmin(admin: Admin): Long

    @Query("SELECT * FROM admins WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getAdminByUsernameAndPassword(username: String, password: String): Admin?
}
