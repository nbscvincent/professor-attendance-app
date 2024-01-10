package com.shin.myproject.user.repository.attendancce

import com.shin.myproject.data.mainscreenModel.attendance.Attendance

interface AttendanceRepository {

    /**
     * Insert attendance data into the data source.
     */
    suspend fun insertAttendance(attendance: Attendance)

    /**
     * Get attendance data for a specific student and date.
     */
    suspend fun getAttendanceForStudentAndDate(studentId: Long, date: String): Attendance?

    suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String)
}