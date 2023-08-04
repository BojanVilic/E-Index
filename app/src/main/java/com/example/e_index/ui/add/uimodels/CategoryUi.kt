package com.example.e_index.ui.add.uimodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.e_index.data.models.entities.Category

data class CategoryUi(
    var name: MutableState<String> = mutableStateOf(""),
    var minPoints: MutableState<String> = mutableStateOf(""),
    var maxPoints: MutableState<String> = mutableStateOf("")
)

fun CategoryUi.asEntity(): Category {
    return Category(
        subjectId = 0,
        schoolYearId = 0,
        name = name.value,
        minPoints = if (minPoints.value.isBlank()) 0 else minPoints.value.toInt(),
        maxPoints = if (maxPoints.value.isBlank()) 0 else maxPoints.value.toInt()
    )
}
