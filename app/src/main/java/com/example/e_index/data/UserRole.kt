package com.example.e_index.data

enum class UserRole(val value: String) {
    ADMIN("Admin"),
    STUDENT("Student")
}

fun stringToUserRole(input: String): UserRole {
    return when (input.lowercase()) {
        "admin" -> UserRole.ADMIN
        "student" -> UserRole.STUDENT
        else -> UserRole.STUDENT
    }
}