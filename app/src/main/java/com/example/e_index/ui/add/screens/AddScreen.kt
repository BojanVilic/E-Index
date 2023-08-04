package com.example.e_index.ui.add.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.MainMenuOption

@Composable
fun AddScreen(
    onAddSchoolYearClicked: () -> Unit,
    onAddStudentClicked: () -> Unit,
    onAddSubjectClicked: () -> Unit,
    onAddAdminClicked: () -> Unit,
    onDeleteStudentClicked: () -> Unit,
    onEditStudent: () -> Unit
) {
    AddContent(
        onAddSchoolYearClicked = onAddSchoolYearClicked,
        onAddStudentClicked = onAddStudentClicked,
        onAddSubjectClicked = onAddSubjectClicked,
        onAddAdminClicked = onAddAdminClicked,
        onDeleteStudentClicked = onDeleteStudentClicked,
        onEditStudent = onEditStudent
    )
}

@Composable
fun AddContent(
    onAddSchoolYearClicked: () -> Unit,
    onAddStudentClicked: () -> Unit,
    onAddSubjectClicked: () -> Unit,
    onAddAdminClicked: () -> Unit,
    onDeleteStudentClicked: () -> Unit,
    onEditStudent: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        MainMenuOption(
            title = R.string.add_school_year,
            onRowClicked = onAddSchoolYearClicked
        )
        MainMenuOption(
            title = R.string.add_student,
            onRowClicked = onAddStudentClicked
        )
        MainMenuOption(
            title = R.string.add_subject,
            onRowClicked = onAddSubjectClicked
        )
        MainMenuOption(
            title = R.string.add_admin,
            onRowClicked = onAddAdminClicked
        )
        MainMenuOption(
            title = R.string.delete_student,
            onRowClicked = onDeleteStudentClicked
        )
        MainMenuOption(
            title = R.string.edit_student_subjects,
            onRowClicked = onEditStudent
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddContentPreview() {
    EIndexTheme {
        AddContent(
            onAddSchoolYearClicked = {},
            onAddStudentClicked = {},
            onAddSubjectClicked = {},
            onAddAdminClicked = {},
            onDeleteStudentClicked = {},
            onEditStudent = {}
        )
    }
}