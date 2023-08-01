@file:OptIn(ExperimentalLayoutApi::class)

package com.example.e_index.ui.add.edit_student

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_index.R
import com.example.e_index.ui.add.screens.CategoryChip
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun EditStudentSubjectScreen(
    editStudentViewModel: EditStudentViewModel,
    onEditSubjectDetailsClicked: () -> Unit
) {
    val editStudentState by editStudentViewModel.editStudentState.collectAsState()

    EditStudentSubjectContent(
        editStudentState = editStudentState,
        onUserIntent = { intent ->
            editStudentViewModel.processIntent(intent)
        },
        onEditSubjectDetailsClicked = onEditSubjectDetailsClicked
    )
}

@Composable
fun EditStudentSubjectContent(
    editStudentState: EditStudentViewState,
    onUserIntent: (EditStudentIntent) -> Unit,
    onEditSubjectDetailsClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = editStudentState.students.map { "${it.name} ${it.surname}, ${it.indexNumber}" },
            selectedValue = editStudentState.selectedStudent?.let { "${it.name} ${it.surname}, ${it.indexNumber}" } ?: "",
            onValueChange = { newStudent ->
                onUserIntent(EditStudentIntent.StudentChanged(editStudentState.students.find { "${it.name} ${it.surname}, ${it.indexNumber}" == newStudent }!!))
            },
            label = stringResource(id = R.string.label_student)
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.student_subjects_list),
            fontSize = 22.sp
        )

        FlowRow {
            editStudentState.subjects.forEach {
                CategoryChip(
                    text = it.name,
                    onChipClicked = { subjectName ->
                        onUserIntent(EditStudentIntent.EditSubjectDetailsClicked(
                            editStudentState.subjects.find { subject -> subject.name == subjectName }!!.id
                        ))
                        onEditSubjectDetailsClicked()
                    }
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun EditStudentSubjectContentPreview() {
    EIndexTheme {
        EditStudentSubjectContent(
            editStudentState = EditStudentViewState(),
            onUserIntent = {},
            onEditSubjectDetailsClicked = {}
        )
    }
}