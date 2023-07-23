package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.Subject
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    // Requirement 3
    @Insert
    suspend fun insertSubject(subject: Subject): Long

    // Requirement 8
    @Transaction
    @Query("SELECT * FROM subjects WHERE id IN (SELECT subjectId FROM grades WHERE studentId = :studentId AND schoolYear = :schoolYear AND points >= 51)")
    fun getPassedSubjectsForStudent(studentId: Long, schoolYear: String): Flow<List<Subject>>

    // Requirement 9
    @Transaction
    @Query("SELECT * FROM subjects WHERE id IN (SELECT subjectId FROM grades WHERE studentId = :studentId AND schoolYear = :schoolYear AND points < 51)")
    fun getFailedSubjectsForStudent(studentId: Long, schoolYear: String): Flow<List<Subject>>

}
