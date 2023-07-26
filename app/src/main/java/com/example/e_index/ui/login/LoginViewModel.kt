package com.example.e_index.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_index.data.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginState = mutableStateOf(LoginViewState())
    val loginState: LoginViewState
        get() = _loginState.value

    suspend fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginClicked -> loginRepository.logIn(
                username = loginState.username.value,
                password = loginState.password.value,
                role = loginState.role.value
            )
            is LoginIntent.UsernameChanged -> _loginState.value.username.value = intent.newUsername
            is LoginIntent.PasswordChanged -> _loginState.value.password.value = intent.newPassword
            is LoginIntent.RoleChanged -> _loginState.value.role.value = intent.newRole
        }
    }

    init {
        viewModelScope.launch {
            loginRepository.loginStatus.collect {
                if (it == UserRole.ADMIN) {
                    _loginState.value.loginSuccess.value = true
                    Log.d("LoginViewModel", "Admin log in successful")
                }
            }
        }
    }
}