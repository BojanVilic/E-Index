package com.example.e_index.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    private val _loginState = MutableStateFlow(LoginViewState())
    val loginState: StateFlow<LoginViewState>
        get() = _loginState.asStateFlow()

    suspend fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginClicked -> loginRepository.logIn(
                username = _loginState.value.username,
                password = _loginState.value.password,
                role = _loginState.value.role
            )
            is LoginIntent.UsernameChanged -> _loginState.update { it.copy(username = intent.newUsername) }
            is LoginIntent.PasswordChanged -> _loginState.update { it.copy(password = intent.newPassword) }
            is LoginIntent.RoleChanged -> _loginState.update { it.copy(role = intent.newRole) }
        }
    }

    init {
        viewModelScope.launch {
            loginRepository.loginStatus.collect { result ->

                result.onSuccess {
                    _loginState.update { it.copy(loginSuccess = true) }
                }

                result.onFailure { throwable ->
                    _loginState.update { it.copy(errorMessage = throwable.message?: "") }
                }
            }
        }
    }
}