package com.example.e_index.ui.add

import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.models.SchoolYear
import javax.inject.Inject

class AddRepository @Inject constructor(
    private val adminDao: AdminDao,
    studentDao: StudentDao
) {

    suspend fun insertSchoolYear(schoolYear: SchoolYear) {
        adminDao.insertSchoolYear(schoolYear)
    }
}