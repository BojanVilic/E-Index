package com.example.e_index.ui.add

import androidx.annotation.StringRes
import com.example.e_index.R
import com.example.e_index.navigation.ADD_ADMIN
import com.example.e_index.navigation.ADD_SCHOOL_YEAR
import com.example.e_index.navigation.ADD_STUDENT
import com.example.e_index.navigation.ADD_SUBJECT
import com.example.e_index.navigation.DELETE_STUDENT
import com.example.e_index.navigation.EDIT_STUDENT

sealed class AddMenuItem(
    @StringRes val titleRes: Int,
    val destination: String
) {
    object SchoolYear : AddMenuItem(
        titleRes = R.string.add_school_year,
        destination = ADD_SCHOOL_YEAR
    )

    object Student : AddMenuItem(
        titleRes = R.string.add_student,
        destination = ADD_STUDENT
    )

    object Subject : AddMenuItem(
        titleRes = R.string.add_subject,
        destination = ADD_SUBJECT
    )

    object Admin : AddMenuItem(
        titleRes = R.string.add_admin,
        destination = ADD_ADMIN
    )

    object DeleteStudent : AddMenuItem(
        titleRes = R.string.delete_student,
        destination = DELETE_STUDENT
    )

    object EditStudent : AddMenuItem(
        titleRes = R.string.edit_student_subjects,
        destination = EDIT_STUDENT
    )
}

val addMenuItems = listOf(
    AddMenuItem.SchoolYear,
    AddMenuItem.Student,
    AddMenuItem.Subject,
    AddMenuItem.Admin,
    AddMenuItem.DeleteStudent,
    AddMenuItem.EditStudent
)
