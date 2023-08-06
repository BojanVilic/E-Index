package com.example.e_index.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.e_index.ui.login.LoginScreen
import com.example.e_index.ui.login.LoginViewModel

const val LOGIN = "login"

fun NavGraphBuilder.loginScreen(navController: NavHostController) {
    composable(LOGIN) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        LoginScreen(
            loginViewModel = loginViewModel,
            onLoginSuccess = {
                navController.navigate(TopLevelDestinations.Search.route) {
                    popUpTo(LOGIN) {
                        inclusive = true
                    }
                }
            }
        )
    }
}