package com.professorsattendanceapp.data.repositories.attendancce

import com.professorsattendanceapp.data.model.Attendance

interface AttendanceRepository {

    suspend fun insertAttendance(attendance: Attendance)

    suspend fun updateAttendance(attendance : Attendance)

    suspend fun deleteAttendance(attendance : Attendance)

}