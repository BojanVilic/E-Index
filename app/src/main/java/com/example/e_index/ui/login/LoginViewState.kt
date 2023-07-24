package com.example.e_index.ui.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.e_index.data.UserRole

data class LoginViewState(
    val username: MutableState<String> = mutableStateOf(""),
    val password: MutableState<String> = mutableStateOf(""),
    val role: MutableState<UserRole> = mutableStateOf(UserRole.ADMIN),
    val isLoading: MutableState<Boolean> = mutableStateOf(false),
    val errorMessage: MutableState<String> = mutableStateOf("")
)