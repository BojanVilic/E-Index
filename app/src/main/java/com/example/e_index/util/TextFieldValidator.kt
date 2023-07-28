package com.example.e_index.util

object TextFieldValidator {
    private val fullPattern = Regex("^[0-9]{4}/[0-9]{4}$")

    fun isValidFormat(input: String): Boolean {
        return fullPattern.matches(input)
    }

    fun areYearsOneYearApart(input: String): Boolean {
        if (!isValidFormat(input)) return false
        val years = input.split("/")
        return years[1].toInt() - years[0].toInt() == 1
    }
}