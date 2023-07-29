package com.example.e_index.ui.add.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_index.R
import com.example.e_index.ui.add.student.AddStudentIntent
import com.example.e_index.ui.add.student.AddStudentViewModel
import com.example.e_index.ui.add.student.AddStudentViewState
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun SubjectDetailsEntryScreen(
    addStudentViewModel: AddStudentViewModel
) {

    val addStudentState by addStudentViewModel.addStudentState.collectAsState()

    SubjectDetailsEntryContent(
        addStudentState = addStudentState,
        onUserIntent = { intent ->
            addStudentViewModel.processIntent(intent)
        }
    )
}

@Composable
fun SubjectDetailsEntryContent(
    addStudentState: AddStudentViewState,
    onUserIntent: (AddStudentIntent) -> Unit
) {

    val scrollState = rememberScrollState()

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
            options = addStudentState.schoolYears.map { it.name },
            selectedValue = addStudentState.selectedSchoolYear?.name?: "",
            onValueChange = { newSchoolYear ->
                onUserIntent(AddStudentIntent.SchoolYearChanged(addStudentState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year)
        )

        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = addStudentState.subjects.map { it.name },
            selectedValue = addStudentState.selectedSubject?.name?: "",
            onValueChange = { newSubject ->
                onUserIntent(AddStudentIntent.SubjectChanged(addStudentState.subjects.find { it.name == newSubject }!!))
            },
            label = stringResource(id = R.string.label_subject)
        )

        if (addStudentState.selectedSubject != null) {

            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = R.string.add_points_for_categories),
                fontSize = 22.sp
            )

            addStudentState.categories.forEach { category ->
                EnterPointsForCategoryRow(
                    category = category,
                    onUserIntent = {
                        onUserIntent(it)
                    }
                )
            }

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp),
                onClick = {
                    onUserIntent(AddStudentIntent.AddStudentSubjectDetails(addStudentState.selectedSubject))
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
        SubjectDetailsEntryContent(
            addStudentState = AddStudentViewState(),
            onUserIntent = {}
        )
    }
}