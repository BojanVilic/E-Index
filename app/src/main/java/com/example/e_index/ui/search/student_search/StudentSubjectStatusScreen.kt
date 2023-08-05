package com.example.e_index.ui.search.student_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun StudentSubjectStatusScreen(
    studentSearchViewModel: StudentSearchViewModel = hiltViewModel()
) {
    val studentSearchViewState by studentSearchViewModel.studentSearchViewState.collectAsState()

    StudentSubjectStatusContent(
        studentSearchViewState = studentSearchViewState,
        onUserIntent = studentSearchViewModel::processIntent
    )
}

@Composable
private fun StudentSubjectStatusContent(
    studentSearchViewState: StudentSearchViewState,
    onUserIntent: (StudentSearchIntent) -> Unit
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
            options = studentSearchViewState.schoolYears.map { it.name },
            selectedValue = studentSearchViewState.selectedSchoolYear?.name?: "",
            onValueChange = { newSchoolYear ->
                onUserIntent(StudentSearchIntent.SchoolYearChanged(studentSearchViewState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year)
        )

        LazyColumn {
            items(studentSearchViewState.studentSubjectDetails.size) { index ->

                val data = studentSearchViewState.studentSubjectDetails[index]

                Column {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "${data.subjectName}-${data.schoolYearName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                    )
                    Text(
                        text = "Status: ${if (data.passed) "Polozeno" else "Nije polozeno"}",
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Ocena: ${data.mark}",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentSubjectStatusContentPreview() {
    EIndexTheme {
        StudentSubjectStatusContent(
            studentSearchViewState = StudentSearchViewState(),
            onUserIntent = {}
        )
    }
}