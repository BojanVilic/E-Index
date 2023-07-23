package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.e_index.data.models.Category

@Dao
interface CategoryDao {
    // Requirement 11
    @Insert
    suspend fun insertCategories(categories: List<Category>)

}
