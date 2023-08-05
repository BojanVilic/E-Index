package com.example.e_index.ui.search.admin_search.students

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun AdminStudentSearchScreen(
    adminStudentSearchViewModel: AdminStudentSearchViewModel = hiltViewModel()
) {
    val viewState by adminStudentSearchViewModel.adminStudentSearchViewState.collectAsState()

    AdminStudentSearchContent(
        viewState = viewState,
        onUserIntent = {
            adminStudentSearchViewModel.processIntent(it)
        }
    )
}

@Composable
private fun AdminStudentSearchContent(
    viewState: AdminStudentSearchViewState,
    onUserIntent: (AdminStudentSearchIntent) -> Unit
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
            options = viewState.students.map { "${it.name} ${it.surname}, ${it.indexNumber}" },
            selectedValue = viewState.selectedStudent?.let { "${it.name} ${it.surname}, ${it.indexNumber}" } ?: "",
            onValueChange = { newStudent ->
                onUserIntent(AdminStudentSearchIntent.StudentChanged(viewState.students.find { "${it.name} ${it.surname}, ${it.indexNumber}" == newStudent }!!))
            },
            label = stringResource(id = R.string.label_student)
        )

        if (viewState.selectedStudent != null) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.show_passed_only_subjects))
                Switch(
                    checked = viewState.passedOnlyFilter,
                    onCheckedChange = {
                        onUserIntent(AdminStudentSearchIntent.PassedOnlyFilterChanged(it))
                    }
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.show_failed_only_subjects))
                Switch(
                    checked = viewState.failedOnlyFilter,
                    onCheckedChange = {
                        onUserIntent(AdminStudentSearchIntent.FailedOnlyFilterChanged(it))
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items(viewState.studentSubjectStatusList.size) { index ->
                val studentSubjectStatus = viewState.studentSubjectStatusList[index]
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("${studentSubjectStatus.subjectName}-${studentSubjectStatus.schoolYearName}: ")
                            }
                            val statusColor = if (studentSubjectStatus.passed) Color.Green else Color.Red
                            withStyle(style = SpanStyle(color = statusColor)) {
                                append(if (studentSubjectStatus.passed) "Položeno" else "Nije položeno")
                            }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminStudentSearchContentPreview() {
    EIndexTheme {
        AdminStudentSearchContent(
            viewState = AdminStudentSearchViewState(),
            onUserIntent = {}
        )
    }
}