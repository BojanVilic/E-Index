@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.add.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.add.AddViewModel
import com.example.e_index.ui.add.uimodels.AdminUi
import com.example.e_index.ui.add.uimodels.fieldsValid
import com.example.e_index.ui.theme.EIndexTheme
import kotlinx.coroutines.launch

@Composable
fun AddAdminScreen(
    addViewModel: AddViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    AddAdminContent(
        onAddAdmin = { adminUi ->
            coroutineScope.launch {
                addViewModel.insertAdmin(adminUi)
            }
        }
    )
}

@Composable
fun AddAdminContent(
    onAddAdmin: (AdminUi) -> Unit
) {

    val context = LocalContext.current

    var fieldsValid by remember { mutableStateOf(false) }
    val admin by remember { mutableStateOf(AdminUi()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_admin),
            fontSize = 22.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = admin.username.value,
            onValueChange = {
                admin.username.value = it
                fieldsValid = admin.fieldsValid()
            },
            label = { Text(stringResource(id = R.string.label_username)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = admin.password.value,
            onValueChange = {
                admin.password.value = it
                fieldsValid = admin.fieldsValid()
            },
            label = { Text(stringResource(id = R.string.label_password)) }
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            onClick = {
                onAddAdmin(admin)
                Toast.makeText(context, R.string.add_admin_success, Toast.LENGTH_LONG).show()
            },
            enabled = fieldsValid
        ) {
            Text(
                text = stringResource(id = R.string.add_label),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddAdminContentPreview() {
    EIndexTheme {
        AddAdminContent(
            onAddAdmin = {}
        )
    }
}