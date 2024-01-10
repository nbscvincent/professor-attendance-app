package com.shin.myproject.user.repository.attendancce

import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.user.dao.AttendanceDao


class OfflineAttendanceRepository(private val attendanceDao: AttendanceDao) : AttendanceRepository {
    override suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)


    override suspend fun getAttendanceForStudentAndDate(studentId: Long, date: String): Attendance? {
        return attendanceDao.getAttendanceForStudentAndDate(studentId, date)
    }

    override suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String) {
        attendanceDao.deleteAttendanceForStudentAndDate(studentId, date)
    }
}