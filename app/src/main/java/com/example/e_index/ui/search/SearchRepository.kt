package com.example.e_index.ui.search

import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.models.entities.SchoolYear
import com.example.e_index.data.models.response_models.StudentInfo
import com.example.e_index.data.models.response_models.StudentPointsDetails
import com.example.e_index.data.models.response_models.StudentSubjectDetails
import com.example.e_index.data.models.response_models.StudentSubjectStatus
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val studentDao: StudentDao,
    private val schoolYearDao: SchoolYearDao
) {
    suspend fun getStudentPointsDetailsForAllSubjects(studentId: Long): List<StudentPointsDetails> {
        return studentDao.getStudentPointsDetailsForAllSubjects(studentId)
    }

    suspend fun getAllSubjectDetailsForStudent(studentId: Long, schoolYearId: Long): List<StudentSubjectDetails> {
        return studentDao.getAllSubjectDetailsForStudent(studentId, schoolYearId)
    }

    suspend fun getAllSchoolYears(): List<SchoolYear> {
        return schoolYearDao.getAllSchoolYears()
    }

    suspend fun getStudentsForSearchParams(subjectId: Long, schoolYearId: Long): List<StudentInfo> {
        return studentDao.getStudentsForSearchParams(subjectId, schoolYearId)
    }

    suspend fun getPassedAndFailedSubjectsForStudent(studentId: Long): List<StudentSubjectStatus> {
        return studentDao.getPassedAndFailedSubjectsForStudent(studentId)
    }
}