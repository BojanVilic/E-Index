package com.example.e_index.ui.login

import com.example.e_index.data.UserRole

sealed class LoginIntent {
    object LoginClicked : LoginIntent()
    data class UsernameChanged(val newUsername: String) : LoginIntent()
    data class PasswordChanged(val newPassword: String) : LoginIntent()
    data class RoleChanged(val newRole: UserRole) : LoginIntent()
}
