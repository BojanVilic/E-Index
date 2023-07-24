package com.example.e_index.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "school_years")
data class SchoolYear(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)
