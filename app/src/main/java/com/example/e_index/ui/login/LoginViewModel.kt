package com.example.e_index.ui.login

import androidx.lifecycle.ViewModel
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    adminDao: AdminDao,
    studentDao: StudentDao
): ViewModel() {

    fun masterAdminCheck() {

    }

    fun studentLogin() {

    }

    fun adminLogin() {

    }
}