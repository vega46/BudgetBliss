package com.example.budgettracking

import androidx.room.Entity
import androidx.room.PrimaryKey

// For example, let's create a User entity
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val email: String,
    val password: String,
    val categories: String,
    val budget: Double
)


