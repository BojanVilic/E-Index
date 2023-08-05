@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.search.admin_search.subjects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun AdminSubjectSearchScreen(
    adminSubjectSearchViewModel: AdminSubjectSearchViewModel = hiltViewModel()
) {
    val viewState by adminSubjectSearchViewModel.adminSubjectSearchViewState.collectAsState()

    AdminSubjectSearchContent(
        viewState = viewState,
        onUserIntent = {
            adminSubjectSearchViewModel.processIntent(it)
        }
    )
}

@Composable
private fun AdminSubjectSearchContent(
    viewState: AdminSubjectSearchViewState,
    onUserIntent: (AdminSubjectSearchIntent) -> Unit
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
            options = viewState.schoolYears.map { it.name },
            selectedValue = viewState.selectedSchoolYear?.name?: "",
            onValueChange = { newSchoolYear ->
                onUserIntent(AdminSubjectSearchIntent.SchoolYearChanged(viewState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year)
        )

        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = viewState.subjects.map { it.name },
            selectedValue = viewState.selectedSubject?.name?: "",
            onValueChange = { newSubject ->
                onUserIntent(AdminSubjectSearchIntent.SubjectChanged(viewState.subjects.find { it.name == newSubject }!!))
            },
            label = stringResource(id = R.string.label_subject)
        )

        if (viewState.selectedSubject != null) {
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(id = R.string.show_passed_only))
                Switch(
                    checked = viewState.passedOnlyFilter,
                    onCheckedChange = {
                        onUserIntent(AdminSubjectSearchIntent.PassedOnlyFilterChanged(it))
                    }
                )
            }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                value = viewState.minMarkFilterValue,
                onValueChange = {
                    onUserIntent(AdminSubjectSearchIntent.MarkFilterChanged(it))
                },
                label = { Text(stringResource(id = R.string.label_min_mark)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                )
            )

            if (viewState.students.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    items(viewState.students.size) { index ->
                        val student = viewState.students[index]
                        Column(
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Ime: ")
                                    }
                                    append("${student.student.name} ${student.student.surname}")
                                }
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Ocena: ")
                                    }
                                    append(student.mark.toString())
                                }
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Status: ")
                                    }
                                    val statusColor = if (student.passed) Color.Green else Color.Red
                                    withStyle(style = SpanStyle(color = statusColor)) {
                                        append(if (student.passed) "Položeno" else "Nije položeno")
                                    }
                                }
                            )
                            Divider()
                        }
                    }
                }
            } else {
                Text(
                    modifier = Modifier
                        .padding(vertical = 32.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.label_no_search_result),
                    fontSize = 22.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AdminSubjectSearchContentPreview() {
    EIndexTheme {
        AdminSubjectSearchContent(
            viewState = AdminSubjectSearchViewState(),
            onUserIntent = {}
        )
    }
}