package com.example.e_index.ui.add.edit_student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_index.R
import com.example.e_index.data.models.Student
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun EditStudentSubjectScreen(
    editStudentSubjectsViewModel: EditStudentSubjectsViewModel
) {
    val studentList by editStudentSubjectsViewModel.studentList.collectAsState()

    EditStudentSubjectContent(
        studentList = studentList
    )
}

@Composable
fun EditStudentSubjectContent(
    studentList: List<Student>
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
            selectedValue = selectedStudent?.let { "${it.name} ${it.surname}, ${it.indexNumber}" }
                ?: "",
            onValueChange = { newStudent ->
                selectedStudent =
                    studentList.find { "${it.name} ${it.surname}, ${it.indexNumber}" == newStudent }
            },
            label = stringResource(id = R.string.label_student)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun EditStudentSubjectContentPreview() {
    EIndexTheme {
        EditStudentSubjectContent(
            studentList = emptyList()
        )
    }
}