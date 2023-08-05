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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.data.models.response_models.StudentPointsDetails
import com.example.e_index.ui.theme.EIndexTheme

@Composable
fun StudentPointsBySubjectScreen(
    studentSearchViewModel: StudentSearchViewModel = hiltViewModel()
) {
    val studentSearchViewState by studentSearchViewModel.studentSearchViewState.collectAsState()

    StudentPointsBySubjectContent(
        studentPointsDetailsState = studentSearchViewState.studentPointsDetails
    )
}

@Composable
private fun StudentPointsBySubjectContent(
    studentPointsDetailsState: List<StudentPointsDetails>
) {
    val groupedData = studentPointsDetailsState.groupBy { it.subjectId to it.schoolYearId }

    LazyColumn {
        groupedData.forEach { (_, _), data ->
            item {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "${data.first().subjectName}-${data.first().schoolYearName}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }

            items(data.size) {index ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "- ${data[index].categoryName}: ${data[index].categoryPoints} poena",
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentPointsBySubjectContentPreview() {
    EIndexTheme {
        StudentPointsBySubjectContent(
            studentPointsDetailsState = emptyList()
        )
    }
}