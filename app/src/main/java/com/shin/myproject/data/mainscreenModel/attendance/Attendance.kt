package com.shin.myproject.data.mainscreenModel.attendance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.shin.myproject.data.mainscreenModel.studentModel.Student

@Entity(tableName = "Attendance",
    foreignKeys = [ForeignKey(entity = Student::class,
        parentColumns = ["student_id"],
        childColumns = ["student_id"],
        onDelete = ForeignKey.CASCADE)])
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attendance_id")
    val attendanceId: Long = 0,
    @ColumnInfo(name = "student_id")
    val studentId: Long,
    val studentCode: Int,
    val firstname: String,
    val lastname: String,
    val attendanceStatus: Boolean, // True = Present, False = Absent
    val date: String,
    val time: String,
)