@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.data.UserRole
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {

    val loginState by loginViewModel.loginState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(loginState.loginSuccess) {
        if (loginState.loginSuccess) {
            onLoginSuccess()
        }
    }

    LoginContent(
        viewState = loginState,
        onUserIntent = { loginIntent ->
            coroutineScope.launch {
                loginViewModel.processIntent(loginIntent)
            }
        }
    )
}

@Composable
fun LoginContent(
    viewState: LoginViewState,
    onUserIntent: (LoginIntent) -> Unit
) {

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(id = R.string.label_main_login_title),
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = viewState.username,
            onValueChange = {
                onUserIntent(LoginIntent.UsernameChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_username)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = viewState.errorMessage.isNotBlank(),
            supportingText = {
                Text(text = viewState.errorMessage)
            }
        )

        OutlinedTextField(
            value = viewState.password,
            onValueChange = {
                onUserIntent(LoginIntent.PasswordChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_password)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            isError = viewState.errorMessage.isNotBlank(),
            supportingText = {
                Text(text = viewState.errorMessage)
            }
        )

        val options = UserRole.values().map { it.value }

        DropdownSelectionMenu(
            options = options,
            selectedValue = viewState.role.value,
            onValueChange = {
                onUserIntent(LoginIntent.RoleChanged(UserRole.stringToUserRole(it)))
            },
            label = stringResource(id = R.string.label_user_type)
        )

        Button(
            modifier = Modifier
                .padding(top = 32.dp)
                .width(250.dp),
            onClick = { onUserIntent(LoginIntent.LoginClicked) }) {
            Text(text = stringResource(id = R.string.label_login))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    EIndexTheme {
        LoginContent(
            viewState = LoginViewState(),
            onUserIntent = {}
        )
    }
}