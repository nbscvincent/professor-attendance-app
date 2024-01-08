package com.shin.myproject.data.mainscreenModel.studentModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject

@Entity(tableName = "Students",
    foreignKeys = [ForeignKey(entity = Subject::class,
        parentColumns = ["subject_id"],
        childColumns = ["subject_id"],
        onDelete = ForeignKey.CASCADE)])
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    val studentId: Long = 0,
    @ColumnInfo(name = "subject_id")
    val subjectId: Long,
    val studentCode: Int,
    val firstname: String,
    val lastname: String,
    val course: String,
    val year: String,
    @ColumnInfo(name = "is_working_student")
    val isWorkingStudent: Boolean
)