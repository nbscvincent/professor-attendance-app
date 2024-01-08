package com.shin.myproject.data.mainscreenModel.subjectModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.shin.myproject.data.authModel.User


@Entity(tableName = "Subjects",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = ["user_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE)])
data class Subject(
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subject_id")
    val subjectId: Long = 0,
    val subjectCode: String,
    val subjectName: String,
    val subjectDay: String,
    val startTime: String,
    val endTime: String,
    val subjectDescription: String,
    val archived: Boolean
)
