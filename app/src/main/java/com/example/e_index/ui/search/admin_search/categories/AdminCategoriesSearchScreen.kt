package com.example.e_index.ui.search.admin_search.categories

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu

@Composable
fun AdminCategoriesSearchScreen(
    adminCategoriesSearchViewModel: AdminCategoriesSearchViewModel = hiltViewModel()
) {
    val viewState by adminCategoriesSearchViewModel.adminCategoriesSearchViewState.collectAsState()

    AdminCategoriesSearchContent(
        viewState = viewState,
        onUserIntent = {
            adminCategoriesSearchViewModel.processIntent(it)
        }
    )
}

@Composable
private fun AdminCategoriesSearchContent(
    viewState: AdminCategoriesSearchViewState,
    onUserIntent: (AdminCategoriesSearchIntent) -> Unit
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
            selectedValue = viewState.selectedSchoolYear?.name ?: "",
            onValueChange = { newSchoolYear ->
                onUserIntent(AdminCategoriesSearchIntent.SchoolYearChanged(viewState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year)
        )

        DropdownSelectionMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            options = viewState.subjects.map { it.name },
            selectedValue = viewState.selectedSubject?.name ?: "",
            onValueChange = { newSubject ->
                onUserIntent(AdminCategoriesSearchIntent.SubjectChanged(viewState.subjects.find { it.name == newSubject }!!))
            },
            label = stringResource(id = R.string.label_subject)
        )

        LazyColumn(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items(viewState.categories.size) { index ->
                val category = viewState.categories[index]
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )) {
                            append("${category.name}: ")
                            append("${category.minPoints}-${category.maxPoints}")
                        }
                        withStyle(style = SpanStyle(
                            fontStyle = FontStyle.Italic,
                            fontSize = 14.sp
                        )) {
                            append(" (min-max)")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminCategoriesSearchContentPreview() {
    EIndexTheme {
        AdminCategoriesSearchContent(
            viewState = AdminCategoriesSearchViewState(),
            onUserIntent = {}
        )
    }
}