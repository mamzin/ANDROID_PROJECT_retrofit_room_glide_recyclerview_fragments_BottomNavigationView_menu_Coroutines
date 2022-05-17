package ru.mamzin.rentateamtesttask.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)