package com.example.e_index.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.e_index.data.UserRole
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    adminDao: AdminDao,
    studentDao: StudentDao
) {

    companion object {
        var currentSessionRole: UserRole? by mutableStateOf(null)
    }

    private val _loginStatus = MutableStateFlow<UserRole?>(null)
    val loginStatus: StateFlow<UserRole?>
        get() = _loginStatus

    suspend fun logIn(username: String, password: String, role: UserRole) {
        Log.d("LoginRepository", "$username $password $role")

        if (
            username.trim().equals("admin", ignoreCase = true)
            && password.trim().equals("admin", ignoreCase = true)
            && role == UserRole.ADMIN
        ) {

            _loginStatus.emit(UserRole.ADMIN)
            currentSessionRole = UserRole.ADMIN
        }
    }

    private fun masterAdminCheck() {

    }

    private fun studentLogin() {

    }

    private fun adminLogin() {
    }
}