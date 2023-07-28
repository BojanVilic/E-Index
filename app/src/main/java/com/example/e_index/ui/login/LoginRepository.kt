package com.example.e_index.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.e_index.data.UserRole
import com.example.e_index.data.dao.AdminDao
import com.example.e_index.data.dao.StudentDao
import com.example.e_index.data.models.Admin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val adminDao: AdminDao,
    private val studentDao: StudentDao
) {

    companion object {
        var currentSessionRole: UserRole? by mutableStateOf(null)
    }

    private val _loginStatus = MutableStateFlow<Result<UserRole?>>(Result.failure(Throwable()))
    val loginStatus: StateFlow<Result<UserRole?>>
        get() = _loginStatus

    suspend fun logIn(username: String, password: String, role: UserRole) {
        Log.d("LoginRepository", "$username $password $role")

        if (role == UserRole.ADMIN) {
            adminLogin(username, password)
        } else {
            studentLogin(username, password)
        }
    }

    private suspend fun adminLogin(username: String, password: String) {
        if (
            username.trim().equals("admin", ignoreCase = true)
            && password.trim().equals("admin", ignoreCase = true)
        ) {
            _loginStatus.emit(Result.success(UserRole.ADMIN))
            currentSessionRole = UserRole.ADMIN
            Result.success(Admin(username = "admin", password = "admin"))
        } else {
            val admin = adminDao.getAdminByUsernameAndPassword(username, password)
            if (admin != null) {
                _loginStatus.emit(Result.success(UserRole.ADMIN))
            } else {
                _loginStatus.emit(Result.failure(Throwable("Pogrešno korisničko ime ili lozinka")))
            }
        }
    }

    private suspend fun studentLogin(username: String, password: String){
        val student = studentDao.getStudentByUsernameAndPassword(username, password)
        if (student != null) {
            _loginStatus.emit(Result.success(UserRole.STUDENT))
        } else {
            _loginStatus.emit(Result.failure(Throwable("Pogrešno korisničko ime ili lozinka")))
        }
    }
}