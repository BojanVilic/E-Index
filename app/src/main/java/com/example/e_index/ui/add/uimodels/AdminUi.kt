package com.example.e_index.ui.add.uimodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.e_index.data.models.entities.Admin

data class AdminUi(
    var username: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf("")
)

fun AdminUi.asEntity(): Admin {
    return Admin(
        username = username.value,
        password = password.value
    )
}

fun AdminUi.fieldsValid(): Boolean {
    return username.value.isNotBlank() && password.value.isNotBlank()
}
