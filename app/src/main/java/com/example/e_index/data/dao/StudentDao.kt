package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.Student
import com.example.e_index.data.models.StudentCategory
import com.example.e_index.data.models.StudentDetails
import com.example.e_index.data.models.StudentPointsByCategory
import com.example.e_index.data.models.StudentSubject
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * FROM students WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getStudentByUsernameAndPassword(username: String, password: String): Student?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentWithRelatedData(
        student: Student,
        studentSubjects: List<StudentSubject>,
        studentCategories: List<StudentCategory>
    ) {
        val studentId = insertStudent(student)
        val studentSubjectsWithStudentIds = studentSubjects.map { it.copy(studentId = studentId) }
        val studentCategoriesWithStudentIds = studentCategories.map { it.copy(studentId = studentId) }

        insertStudentSubjects(studentSubjectsWithStudentIds)
        insertStudentCategories(studentCategoriesWithStudentIds)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentCategories(studentCategories: List<StudentCategory>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentSubjects(studentSubjects: List<StudentSubject>)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<Student>>

    @Transaction
    @Query("SELECT * FROM students WHERE id IN (SELECT studentId FROM student_subject WHERE studentId = :studentId)")
    suspend fun getStudentDetails(studentId: Long): List<StudentDetails>

    @Query(
        """
        SELECT sc.categoryId, sc.points
        FROM student_category sc
        INNER JOIN student_subject ss ON sc.studentId = ss.studentId AND sc.schoolYearId = ss.schoolYearId
        WHERE sc.studentId = :studentId AND ss.subjectId = :subjectId AND sc.schoolYearId = :schoolYearId
        """
    )
    suspend fun getStudentPointsByCategory(
        studentId: Long,
        subjectId: Long,
        schoolYearId: Long
    ): List<StudentPointsByCategory>
}

