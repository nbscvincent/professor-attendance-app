package com.shin.myproject.user.repository.attendancce

import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import kotlinx.coroutines.flow.Flow

interface AttendanceRepository {

    /**
     * Insert attendance data into the data source.
     */
    suspend fun insertAttendance(attendance: Attendance)

    suspend fun getAttendancesForSubject(subjectId: Long): Flow<List<Attendance>>

    /**
     * Update the attendance status for a specific attendance ID.
     */
    suspend fun updateAttendanceStatus(attendanceId: Long, attendanceStatus: Boolean)

    /**
     * Get attendance data for a specific student and date.
     */
    suspend fun getAttendanceForStudentAndDate(subjectId: Long, studentId: Long, date: String): Attendance?

    suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String)

    fun getStudentsWithAttendanceStatus(subjectId: Long, attendanceStatus: Boolean, date: String): Flow<List<Attendance>>

    fun getStudentsForDate(subjectId: Long, date: String): Flow<List<Attendance>>
}