package com.example.e_index.ui.login

import com.example.e_index.data.UserRole

data class LoginViewState(
    val username: String = "admin",
    val password: String = "admin",
    val role: UserRole = UserRole.ADMIN,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val loginSuccess: Boolean = false
)