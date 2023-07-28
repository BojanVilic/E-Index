package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_index.data.models.Admin
import com.example.e_index.data.models.SchoolYear

@Dao
interface AdminDao {

    @Insert
    suspend fun insertAdmin(admin: Admin): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchoolYear(schoolYear: SchoolYear): Long

    @Query("SELECT * FROM admins WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getAdminByUsernameAndPassword(username: String, password: String): Admin?
}
