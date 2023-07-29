package com.example.e_index.ui.add

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.SchoolYearDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.dao.SubjectDao
import com.example.e_index.data.models.Admin
import com.example.e_index.data.models.Category
import com.example.e_index.data.models.SchoolYear
import com.example.e_index.data.models.Subject
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
                    name = subjectName
                ),
                categories = categories,
                schoolYearId = selectedSchoolYearId
            )
        }
    }

    suspend fun getAllSchoolYears(): List<SchoolYear> {
        return schoolYearDao.getAllSchoolYears()
    }
}