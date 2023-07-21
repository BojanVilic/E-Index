package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val surname: String,
    val indexNumber: String,
    val jmbg: String,
    val username: String,
    val password: String
)
