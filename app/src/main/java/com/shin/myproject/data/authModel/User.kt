package com.shin.myproject.data.authModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    val userId: Long,
    val firstname: String,
    val lastname: String,
    val email: String,
    val password: String,
    val phone: String
)