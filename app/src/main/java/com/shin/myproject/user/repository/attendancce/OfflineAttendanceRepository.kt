package com.shin.myproject.user.repository.attendancce

import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.user.dao.AttendanceDao
import kotlinx.coroutines.flow.Flow


class OfflineAttendanceRepository(private val attendanceDao: AttendanceDao) : AttendanceRepository {
    override suspend fun insertAttendance(attendance: Attendance) = attendanceDao.insert(attendance)

    override suspend fun updateAttendanceStatus(attendanceId: Long, attendanceStatus: Boolean) {
        attendanceDao.updateAttendanceStatus(attendanceId, attendanceStatus)
    }

    override suspend fun getAttendanceForStudentAndDate(subjectId: Long, studentId: Long, date: String): Attendance? {
        return attendanceDao.getAttendanceForStudentAndDate(subjectId, studentId, date)
    }

    override suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String) {
        attendanceDao.deleteAttendanceForStudentAndDate(studentId, date)
    }

    override fun getStudentsWithAttendanceStatus(subjectId: Long, attendanceStatus: Boolean, date: String): Flow<List<Attendance>> {
        return attendanceDao.getStudentsWithAttendanceStatus(subjectId, attendanceStatus, date)
    }

    override fun getStudentsForDate(subjectId: Long, date: String): Flow<List<Attendance>> {
        return attendanceDao.getStudentsForDate(subjectId, date)
    }
}