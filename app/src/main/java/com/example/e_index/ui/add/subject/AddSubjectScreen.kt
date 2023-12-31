@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.example.e_index.ui.add.subject

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.ui.add.admin_and_school_year.asEntity
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.DropdownSelectionMenu
import kotlinx.coroutines.launch

@Composable
fun AddSubjectScreen(
    addViewModel: AddSubjectViewModel = hiltViewModel()
) {

    val addSubjectState by addViewModel.addSubjectState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    AddSubjectContent(
        addSubjectState = addSubjectState,
        onUserIntent = { userIntent ->
            coroutineScope.launch {
                addViewModel.processIntent(userIntent)
            }
        }
    )
}

@Composable
fun AddSubjectContent(
    addSubjectState: AddSubjectViewState,
    onUserIntent: (AddSubjectIntent) -> Unit
) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.add_subject),
            fontSize = 22.sp
        )

        DropdownSelectionMenu(
            modifier = Modifier.fillMaxWidth(),
            options = addSubjectState.schoolYears.map { it.name },
            selectedValue = addSubjectState.selectedSchoolYear?.name?: "",
            onValueChange = { newSchoolYear ->
                onUserIntent(AddSubjectIntent.SelectSchoolYear(addSubjectState.schoolYears.find { it.name == newSchoolYear }!!))
            },
            label = stringResource(id = R.string.label_school_year)
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addSubjectState.subjectName,
            onValueChange = {
                onUserIntent(AddSubjectIntent.EnterSubjectName(it))
            },
            label = { Text(stringResource(id = R.string.label_subject_name)) }
        )

        if (addSubjectState.categories.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(id = R.string.label_active_categories),
                fontSize = 18.sp
            )

            FlowRow {
                addSubjectState.categories.forEach {
                    CategoryChip(text = it.name)
                }
            }

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Ukupno poena: ${addSubjectState.categories.sumOf { it.maxPoints }}"
            )
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            value = addSubjectState.currentCategory.name.value,
            onValueChange = {
                addSubjectState.currentCategory.name.value = it
            },
            label = { Text(stringResource(id = R.string.label_category_name)) }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = addSubjectState.currentCategory.minPoints.value,
                onValueChange = {
                    addSubjectState.currentCategory.minPoints.value = it
                },
                label = { Text(stringResource(id = R.string.label_min)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                modifier = Modifier
                    .weight(1f),
                value = addSubjectState.currentCategory.maxPoints.value,
                onValueChange = {
                    addSubjectState.currentCategory.maxPoints.value = it
                },
                label = { Text(stringResource(id = R.string.label_max)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            onClick = {
                if (addSubjectState.currentCategory.name.value.isNotBlank()
                    && addSubjectState.currentCategory.maxPoints.value != "0"
                    && (addSubjectState.categories.sumOf { it.maxPoints } + addSubjectState.currentCategory.maxPoints.value.toInt()) <= 100) {
                    onUserIntent(AddSubjectIntent.AddCategory(addSubjectState.currentCategory.asEntity()))
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.add_category),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            onClick = {
                if (addSubjectState.categories.sumOf { it.maxPoints } == 100) {
                    onUserIntent(AddSubjectIntent.InsertSubject)
                    Toast.makeText(context, R.string.add_subject_success, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, R.string.error_category_points_sum, Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(
                text = stringResource(id = R.string.label_add_to_database),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun CategoryChip(
    id: Long = -1,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    borderWidth: Int = 1,
    onChipClicked: (Long) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .border(width = borderWidth.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .clickable {
                onChipClicked(id)
            }
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            text = text,
            fontWeight = fontWeight
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AddSubjectContentPreview() {
    EIndexTheme {
        AddSubjectContent(
            addSubjectState = AddSubjectViewState(),
            onUserIntent = {}
        )
    }
}