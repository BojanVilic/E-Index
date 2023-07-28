package com.example.e_index.ui.add

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_index.R
import com.example.e_index.ui.theme.EIndexTheme

@Composable
fun AddScreen(
    onAddSchoolYearClicked: () -> Unit,
    onAddStudentClicked: () -> Unit,
    onAddSubjectClicked: () -> Unit,
    onAddAdminClicked: () -> Unit
) {
    AddContent(
        onAddSchoolYearClicked = onAddSchoolYearClicked,
        onAddStudentClicked = onAddStudentClicked,
        onAddSubjectClicked = onAddSubjectClicked,
        onAddAdminClicked = onAddAdminClicked
    )
}

@Composable
fun AddContent(
    onAddSchoolYearClicked: () -> Unit,
    onAddStudentClicked: () -> Unit,
    onAddSubjectClicked: () -> Unit,
    onAddAdminClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AddDataOption(
            title = R.string.add_school_year,
            onRowClicked = onAddSchoolYearClicked
        )
        AddDataOption(
            title = R.string.add_student,
            onRowClicked = onAddStudentClicked
        )
        AddDataOption(
            title = R.string.add_subject,
            onRowClicked = onAddSubjectClicked
        )

        AddDataOption(
            title = R.string.add_admin,
            onRowClicked = onAddAdminClicked
        )
    }
}

@Composable
private fun AddDataOption(
    @StringRes title: Int,
    onRowClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onRowClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(title)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = null
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
            onAddAdminClicked = {}
        )
    }
}