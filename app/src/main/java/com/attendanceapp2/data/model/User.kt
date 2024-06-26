package com.attendanceapp2.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val firstname : String,
    val lastname : String,
    val email : String,
    val password : String,
    val usertype : String,
    val department : String
)