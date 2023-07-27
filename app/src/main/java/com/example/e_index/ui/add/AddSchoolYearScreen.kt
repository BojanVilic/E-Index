@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.e_index.ui.add

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_index.R
import com.example.e_index.data.models.SchoolYear
import com.example.e_index.ui.theme.EIndexTheme
import com.example.e_index.util.YearFormatValidator
import kotlinx.coroutines.launch


@Composable
fun AddSchoolYearScreen(
    addViewModel: AddViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    AddSchoolYearContent(
        onAddSchoolYear = { schoolYear ->
            coroutineScope.launch {
                addViewModel.insertSchoolYear(SchoolYear(name = schoolYear))
            }
        }
    )
}

@Composable
fun AddSchoolYearContent(
    onAddSchoolYear: (String) -> Unit
) {

    var inputText by remember { mutableStateOf("") }
    var isSchoolYearValid by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(id = R.string.add_school_year),
            fontSize = 22.sp
        )

        YearFormatTextField(
            text = inputText,
            onTextChanged = { text, isValid ->
                inputText = text
                isSchoolYearValid = isValid
            }
        )

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp),
            onClick = {
                if (isSchoolYearValid) {
                    onAddSchoolYear(inputText)

                    Toast.makeText(context, R.string.add_school_year_success, Toast.LENGTH_LONG).show()

                    inputText = ""
                    isSchoolYearValid = false
                }
            },
            enabled = isSchoolYearValid
        ) {
            Text(
                text = stringResource(id = R.string.add_label),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun YearFormatTextField(
    text: String,
    onTextChanged: (String, Boolean) -> Unit
) {

    var isValidFormat by remember { mutableStateOf(true) }
    var areYearsOneYearApart by remember { mutableStateOf(true) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = text,
        onValueChange = { input ->
            isValidFormat = YearFormatValidator.isValidFormat(input)
            areYearsOneYearApart = YearFormatValidator.areYearsOneYearApart(input)

            onTextChanged(input, isValidFormat && areYearsOneYearApart)
        },
        label = { Text("Enter year range (xxxx/xxxx)") },
        isError = !isValidFormat || !areYearsOneYearApart,
        supportingText = {
            if (!isValidFormat) {
                Text(
                    text = "Invalid year format. Please use xxxx/xxxx format.",
                    color = MaterialTheme.colorScheme.error
                )
            } else if (!areYearsOneYearApart) {
                Text(
                    text = "The years should be one year apart.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddSchoolYearContentPreview() {
    EIndexTheme {
        AddSchoolYearContent(
            onAddSchoolYear = {}
        )
    }
}