package com.example.e_index.data

enum class UserRole(val value: String) {
    ADMIN("Admin"),
    STUDENT("Student");

    companion object {
        fun stringToUserRole(input: String): UserRole {
            return when (input.lowercase()) {
                "admin" -> ADMIN
                "student" -> STUDENT
                else -> STUDENT
            }
        }
    }
}