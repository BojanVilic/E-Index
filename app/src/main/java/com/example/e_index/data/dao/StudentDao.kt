package com.example.e_index.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject
import com.example.e_index.data.models.response_models.StudentInfo
import com.example.e_index.data.models.response_models.StudentPointsByCategory
import com.example.e_index.data.models.response_models.StudentPointsDetails
import com.example.e_index.data.models.response_models.StudentSubjectDetails
import com.example.e_index.data.models.response_models.StudentSubjectStatus
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

    @Query(
        """
        SELECT sc.categoryId, sc.points, ss.subjectId, ss.schoolYearId, sc.passed
        FROM student_category sc
        INNER JOIN student_subject ss ON sc.studentId = ss.studentId AND sc.schoolYearId = ss.schoolYearId
        WHERE sc.studentId = :studentId
        """
    )
    fun getAllStudentPointsByCategory(studentId: Long): Flow<List<StudentPointsByCategory>>

    @Query(
        """
        SELECT
        c.id AS categoryId,
        c.name AS categoryName,
        COALESCE(sc.points, 0) AS categoryPoints,
        ss.subjectId,
        s.name AS subjectName,
        ss.schoolYearId,
        sy.name AS schoolYearName,
        ss.passed
        FROM subjects s
        JOIN student_subject ss ON s.id = ss.subjectId
        JOIN school_years sy ON ss.schoolYearId = sy.id
        JOIN categories c ON s.id = c.subjectId
        LEFT JOIN student_category sc ON ss.studentId = sc.studentId AND ss.schoolYearId = sc.schoolYearId AND c.id = sc.categoryId
        WHERE ss.studentId = :studentId
        """
    )
    suspend fun getStudentPointsDetailsForAllSubjects(studentId: Long): List<StudentPointsDetails>

    @Query(
        """
            SELECT s.name AS subjectName, sy.name AS schoolYearName, ss.mark, ss.passed
            FROM student_subject ss
            JOIN subjects s ON ss.subjectId = s.id
            JOIN school_years sy ON ss.schoolYearId = sy.id
            WHERE ss.studentId = :studentId AND ss.schoolYearId = :schoolYearId
        """
    )
    suspend fun getAllSubjectDetailsForStudent(studentId: Long, schoolYearId: Long): List<StudentSubjectDetails>

    @Query(
        """
        SELECT s.*, ss.mark, ss.passed
        FROM students s
        INNER JOIN student_subject ss ON s.id = ss.studentId
        WHERE ss.subjectId = :subjectId AND ss.schoolYearId = :schoolYearId
        """
    )
    suspend fun getStudentsForSearchParams(subjectId: Long, schoolYearId: Long): List<StudentInfo>

    @Query(
        """
        SELECT ss.subjectId, s.name AS subjectName, ss.schoolYearId, sy.name AS schoolYearName, ss.passed
        FROM student_subject ss
        INNER JOIN subjects s ON ss.subjectId = s.id
        INNER JOIN school_years sy ON ss.schoolYearId = sy.id
        WHERE ss.studentId = :studentId
        """
    )
    suspend fun getPassedAndFailedSubjectsForStudent(studentId: Long): List<StudentSubjectStatus>
}

