@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.example.e_index.ui.add.student

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_index.R
import com.example.e_index.ui.add.subject.CategoryChip
import com.example.e_index.ui.theme.EIndexTheme

@Composable
fun AddStudentScreen(
    addStudentViewModel: AddStudentViewModel,
    onAddSubjectDetailsClicked: () -> Unit
) {

    val addStudentState by addStudentViewModel.addStudentState.collectAsState()

    AddStudentContent(
        addStudentState = addStudentState,
        onUserIntent = { intent ->
            addStudentViewModel.processIntent(intent)
        },
        onAddSubjectDetailsClicked = onAddSubjectDetailsClicked
    )
}

@Composable
fun AddStudentContent(
    addStudentState: AddStudentViewState,
    onUserIntent: (AddStudentIntent) -> Unit,
    onAddSubjectDetailsClicked: () -> Unit
) {

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = stringResource(id = R.string.add_student),
            fontSize = 22.sp
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.name,
            onValueChange = {
                 onUserIntent(AddStudentIntent.NameChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_name)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.surname,
            onValueChange = {
                onUserIntent(AddStudentIntent.SurnameChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_surname)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.indexNumber,
            onValueChange = {
                onUserIntent(AddStudentIntent.IndexNumberChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_index_number)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.jmbg,
            onValueChange = {
                if (it.length <= 13) {
                    onUserIntent(AddStudentIntent.JmbgChanged(it))
                }
            },
            label = { Text(stringResource(id = R.string.label_jmbg)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            isError = addStudentState.jmbg.length in 1..12,
            supportingText = {
                if (addStudentState.jmbg.length in 1..12) {
                    Text(text = stringResource(id = R.string.error_jmbg_format))
                }
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.username,
            onValueChange = {
                onUserIntent(AddStudentIntent.UsernameChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_username)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addStudentState.password,
            onValueChange = {
                onUserIntent(AddStudentIntent.PasswordChanged(it))
            },
            label = { Text(stringResource(id = R.string.label_password)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
        )

        Row(
            modifier = Modifier
                .padding(top = 48.dp, bottom = 16.dp)
                .clickable {
                    onAddSubjectDetailsClicked()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.add_subject_to_student),
                fontSize = 20.sp
            )
            Icon(
                modifier = Modifier.padding(start = 12.dp),
                painter = painterResource(id = R.drawable.ic_add_circle),
                contentDescription = null
            )
        }

        if (addStudentState.addedStudentSubjects.isNotEmpty()) {
            FlowRow {
                addStudentState.addedStudentSubjects.forEach {
                    CategoryChip(text = it.name)
                }
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 32.dp),
            onClick = {
                if (addStudentState.allFieldsPopulated()) {
                    onUserIntent(AddStudentIntent.InsertStudent)
                    Toast.makeText(context, R.string.add_student_success, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, R.string.error_all_fields_must_be_populated, Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.label_add_to_database),
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddStudentContentPreview() {
    EIndexTheme {
        AddStudentContent(
            addStudentState = AddStudentViewState(),
            onUserIntent = {},
            onAddSubjectDetailsClicked = {}
        )
    }
}