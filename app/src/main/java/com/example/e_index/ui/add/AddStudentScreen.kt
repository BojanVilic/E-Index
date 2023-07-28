@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_index.R
import com.example.e_index.ui.add.uimodels.StudentUi
import com.example.e_index.ui.theme.EIndexTheme

@Composable
fun AddStudentScreen() {
    AddStudentContent()
}

@Composable
fun AddStudentContent() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_student),
            fontSize = 22.sp
        )

        val student by remember { mutableStateOf(StudentUi()) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.name,
            onValueChange = {
                student.name = it
            },
            label = { Text(stringResource(id = R.string.label_name)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.surname,
            onValueChange = {
                student.surname = it
            },
            label = { Text(stringResource(id = R.string.label_surname)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.indexNumber,
            onValueChange = {
                student.indexNumber = it
            },
            label = { Text(stringResource(id = R.string.label_index_number)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.jmbg,
            onValueChange = {
                student.jmbg = it
            },
            label = { Text(stringResource(id = R.string.label_jmbg)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.username,
            onValueChange = {
                student.username = it
            },
            label = { Text(stringResource(id = R.string.label_username)) }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = student.password,
            onValueChange = {
                student.password = it
            },
            label = { Text(stringResource(id = R.string.label_password)) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddStudentContentPreview() {
    EIndexTheme {
        AddStudentContent()
    }
}