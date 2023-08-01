package com.example.e_index.ui.add.edit_student

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_index.R
import com.example.e_index.ui.add.screens.EnterPointsForCategoryRow
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun EditSubjectDetailsEntryScreen(
    editStudentViewModel: EditStudentViewModel
) {

    val editStudentState by editStudentViewModel.editStudentState.collectAsState()

    EditSubjectDetailsEntryContent(
        editStudentState = editStudentState,
        onUserIntent = { intent ->
            editStudentViewModel.processIntent(intent)
        }
    )
}

@Composable
fun EditSubjectDetailsEntryContent(
    editStudentState: EditStudentViewState,
    onUserIntent: (EditStudentIntent) -> Unit
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = emptyList(),
            selectedValue = editStudentState.selectedSchoolYear?.name?: "",
            onValueChange = { newSchoolYear ->
//                onUserIntent(AddStudentIntent.SchoolYearChanged(addStudentState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year),
            enabled = false
        )

        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = emptyList(),
            selectedValue = editStudentState.selectedSubject?.name?: "",
            onValueChange = { newSubject ->
//                onUserIntent(AddStudentIntent.SubjectChanged(addStudentState.subjects.find { it.name == newSubject }!!))
            },
            label = stringResource(id = R.string.label_subject),
            enabled = false
        )

        if (editStudentState.selectedSubject != null) {

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.add_points_for_categories),
                fontSize = 22.sp
            )

            editStudentState.categories.forEach { category ->
                EnterPointsForCategoryRow(
                    subject = editStudentState.selectedSubject,
                    category = category,
                    onUserIntent = {
//                        onUserIntent(it)
                    },
                    points = (editStudentState.categoryPoints.find { it.categoryId == category.id }?.points?: 0).toString()
                )
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onClick = {
                    Toast.makeText(context, R.string.add_subject_details_success, Toast.LENGTH_LONG).show()

//                    onUserIntent(AddStudentIntent.AddStudentSubjectDetails(editStudentState.selectedSubject))
                }
            ) {
                Text(
                    text = stringResource(id = R.string.add_subject_details),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubjectDetailsEntryContentPreview() {
    EIndexTheme {
        EditSubjectDetailsEntryContent(
            editStudentState = EditStudentViewState(),
            onUserIntent = {}
        )
    }
}