@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.login

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.data.UserRole
import com.example.e_index.ui.theme.EIndexTheme
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
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "e-index log in",
            fontSize = 24.sp
        )

        OutlinedTextField(
            value = viewState.username,
            onValueChange = {
                onUserIntent(LoginIntent.UsernameChanged(it))
            },
            label = { Text(text = "Korisnicko ime") },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            value = viewState.password,
            onValueChange = {
                onUserIntent(LoginIntent.PasswordChanged(it))
            },
            label = { Text(text = "Lozinka") },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        val options = UserRole.values().map { it.value }
        var expanded by remember { mutableStateOf(false) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = viewState.role.value,
                onValueChange = {},
                label = { Text("Tip korisnika") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onUserIntent(LoginIntent.RoleChanged(UserRole.stringToUserRole(selectionOption)))
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Button(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .width(250.dp),
            onClick = { onUserIntent(LoginIntent.LoginClicked) }) {
            Text(text = "Log in")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginContentPreview() {
    EIndexTheme {
        LoginContent(
            viewState = LoginViewState(),
            onUserIntent = {}
        )
    }
}