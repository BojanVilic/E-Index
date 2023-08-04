package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_index.data.models.entities.SchoolYear

@Dao
interface SchoolYearDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchoolYear(schoolYear: SchoolYear): Long

    @Query("SELECT * FROM school_years")
    suspend fun getAllSchoolYears(): List<SchoolYear>
}