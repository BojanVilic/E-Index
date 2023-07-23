package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.Grade
import kotlinx.coroutines.flow.Flow

@Dao
interface GradeDao {
    // Requirement 10
    @Insert
    suspend fun insertGrade(grade: Grade): Long

    // Requirement 12
    @Transaction
    @Query("SELECT * FROM grades WHERE studentId = :studentId AND subjectId = :subjectId AND schoolYear = :schoolYear")
    fun getGradeForStudentAndSubjectFlow(studentId: Long, subjectId: Long, schoolYear: String): Flow<Grade?>

    // Requirement 5
    @Transaction
    @Query("SELECT * FROM grades WHERE subjectId = :subjectId AND schoolYear = :schoolYear AND points >= 51")
    fun getStudentsWhoPassedSubject(subjectId: Long, schoolYear: String): Flow<List<Grade>>

}
