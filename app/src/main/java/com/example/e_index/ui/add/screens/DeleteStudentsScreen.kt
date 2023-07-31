package com.example.e_index.ui.add.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.data.models.Student
import com.example.e_index.ui.add.delete_student.DeleteStudentViewModel
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun DeleteStudentsScreen(
    deleteStudentViewModel: DeleteStudentViewModel = hiltViewModel()
) {

    val studentList by deleteStudentViewModel.studentList.collectAsState()

    DeleteStudentsContent(
        studentList = studentList,
        onDeleteUserClicked = {
            deleteStudentViewModel.deleteStudent(it)
        }
    )
}

@Composable
fun DeleteStudentsContent(
    studentList: List<Student>,
    onDeleteUserClicked: (Student?) -> Unit
) {

    var selectedStudent by remember { mutableStateOf<Student?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = studentList.map { "${it.name} ${it.surname}, ${it.indexNumber}" },
            selectedValue = selectedStudent?.let { "${it.name} ${it.surname}, ${it.indexNumber}" } ?: "",
            onValueChange = { newStudent ->
                selectedStudent = studentList.find { "${it.name} ${it.surname}, ${it.indexNumber}" == newStudent }
            },
            label = stringResource(id = R.string.label_student)
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            onClick = {
                onDeleteUserClicked(selectedStudent)
                selectedStudent = null
            }
        ) {
            Text(
                text = stringResource(id = R.string.label_delete_from_database),
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun DeleteStudentsContentPreview() {
    EIndexTheme {
        DeleteStudentsContent(
            studentList = emptyList(),
            onDeleteUserClicked = {}
        )
    }
}