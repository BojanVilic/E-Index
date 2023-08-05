package com.example.e_index.ui.add

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.data.models.entities.Admin
import com.example.e_index.data.models.entities.Category
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.entities.Student
import com.example.e_index.data.models.entities.StudentCategory
import com.example.e_index.data.models.entities.StudentSubject
import com.example.e_index.data.models.entities.Subject
import com.example.e_index.data.models.response_models.StudentDetails
import com.example.e_index.data.models.response_models.StudentPointsByCategory
import com.example.e_index.data.models.response_models.SubjectDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddRepository @Inject constructor(
    private val adminDao: AdminDao,
    private val studentDao: StudentDao,
    private val subjectDao: SubjectDao,
    private val schoolYearDao: SchoolYearDao
) {

    suspend fun insertSchoolYear(schoolYear: SchoolYear) {
        schoolYearDao.insertSchoolYear(schoolYear)
    }

    suspend fun insertAdmin(admin: Admin) {
        adminDao.insertAdmin(admin)
    }

    suspend fun insertSubjectWithCategories(
        subjectName: String,
        categories: List<Category>,
        selectedSchoolYearId: Long?
    ) {
        if (selectedSchoolYearId != null) {
            subjectDao.insertSubjectWithCategories(
                subject = Subject(
                    name = subjectName,
                    schoolYearId = selectedSchoolYearId
                ),
                categories = categories,
                schoolYearId = selectedSchoolYearId
            )
        }
    }

    suspend fun getAllSchoolYears(): List<SchoolYear> {
        return schoolYearDao.getAllSchoolYears()
    }

    suspend fun getAllSubjects(schoolYearId: Long): List<Subject> {
        return subjectDao.getAllSubjectsForYear(schoolYearId)
    }

    suspend fun getCategoriesForSubject(subjectId: Long, schoolYearId: Long): List<Category> {
        return subjectDao.getCategoriesForSubject(subjectId, schoolYearId)
    }

    suspend fun insertStudentWithRelevantData(
        student: Student,
        studentSubjectList: List<StudentSubject>,
        studentCategoryList: List<StudentCategory>
    ) {
        studentDao.insertStudentWithRelatedData(
            student = student,
            studentSubjects = studentSubjectList,
            studentCategories = studentCategoryList
        )
    }

    suspend fun insertStudentCategory(studentCategories: List<StudentCategory>) {
        studentDao.insertStudentCategories(studentCategories)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }


    fun getAllStudents(): Flow<List<Student>> {
        return studentDao.getAllStudents()
    }

    suspend fun getSubjectDetails(studentId: Long): List<SubjectDetails> {
        return subjectDao.getSubjectDetails(studentId)
    }

    suspend fun getStudentDetails(studentId: Long): List<StudentDetails> {
        return studentDao.getStudentDetails(studentId)
    }

    suspend fun getStudentPointsByCategoryForSubject(
        studentId: Long,
        subjectId: Long,
        schoolYearId: Long
    ): List<StudentPointsByCategory> {
        return studentDao.getStudentPointsByCategoryForSubject(studentId, subjectId, schoolYearId)
    }

    suspend fun getAllStudentPointsByCategory(studentId: Long): List<StudentPointsByCategory> {
        return studentDao.getAllStudentPointsByCategory(studentId)
    }
}