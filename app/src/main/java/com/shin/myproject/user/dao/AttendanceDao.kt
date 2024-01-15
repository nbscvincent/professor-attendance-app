package com.shin.myproject.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import kotlinx.coroutines.flow.Flow


@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    /**
     * Get attendance data for a specific student and date.
     */
    @Query("SELECT * FROM Attendance WHERE subjectId = :subjectId AND student_id = :studentId AND date = :date")
    suspend fun getAttendanceForStudentAndDate(subjectId: Long, studentId: Long, date: String): Attendance?


    /**
     * Delete attendance records for a specific student and date.
     */
    @Query("DELETE FROM Attendance WHERE student_id = :studentId AND date = :date")
    suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String)

    /**
     * Update the attendance status for a specific attendance ID.
     */
    @Query("UPDATE Attendance SET attendanceStatus = :attendanceStatus WHERE attendance_id = :attendanceId")
    suspend fun updateAttendanceStatus(attendanceId: Long, attendanceStatus: Boolean)

    @Query("SELECT * FROM Attendance WHERE subjectId = :subjectId AND attendanceStatus = :attendanceStatus AND date = :date")
    fun getStudentsWithAttendanceStatus(subjectId: Long, attendanceStatus: Boolean, date: String): Flow<List<Attendance>>

    /**
     * Get attendance data for a specific subject and date.
     */
    @Query("SELECT * FROM Attendance WHERE subjectId = :subjectId AND date = :date")
    fun getStudentsForDate(subjectId: Long, date: String): Flow<List<Attendance>>
}