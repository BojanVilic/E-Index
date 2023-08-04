package com.example.e_index.ui.search

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.data.models.response_models.StudentPointsDetails
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val adminDao: AdminDao,
    private val studentDao: StudentDao,
    private val subjectDao: SubjectDao,
    private val schoolYearDao: SchoolYearDao
) {
    suspend fun getStudentPointsDetailsForAllSubjects(studentId: Long): List<StudentPointsDetails> {
        return studentDao.getStudentPointsDetailsForAllSubjects(studentId)
    }
}