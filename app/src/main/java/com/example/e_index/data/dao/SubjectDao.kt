package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.data.models.response_models.SubjectDetails
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM subjects WHERE schoolYearId = :schoolYearId")
    suspend fun getAllSubjectsForYear(schoolYearId: Long): List<Subject>

    @Query("SELECT * FROM categories WHERE subjectId = :subjectId AND schoolYearId = :schoolYearId")
    suspend fun getCategoriesForSubject(subjectId: Long, schoolYearId: Long): List<Category>

    @Transaction
    @Query("SELECT * FROM subjects WHERE id IN (SELECT subjectId FROM student_subject WHERE studentId = :studentId)")
    fun getSubjectDetails(studentId: Long): Flow<List<SubjectDetails>>
}
