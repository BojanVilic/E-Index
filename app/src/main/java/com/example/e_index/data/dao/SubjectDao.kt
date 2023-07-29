package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.example.e_index.data.models.Category
import com.example.e_index.data.models.Subject

@Dao
interface SubjectDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjectWithCategories(subject: Subject, categories: List<Category>, schoolYearId: Long) {
        val subjectId = insertSubject(subject)

        val categoriesWithSubjectId = categories.map { category ->
            category.copy(subjectId = subjectId, schoolYearId = schoolYearId)
        }
        insertCategories(categoriesWithSubjectId)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: Subject): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<Category>)

}
