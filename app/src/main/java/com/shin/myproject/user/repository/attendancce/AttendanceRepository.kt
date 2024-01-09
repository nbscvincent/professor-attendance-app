package com.shin.myproject.user.repository.attendancce

import com.shin.myproject.data.mainscreenModel.attendance.Attendance

interface AttendanceRepository {

    /**
     * Insert attendance data into the data source.
     */
    suspend fun insertAttendance(attendance: Attendance)
}