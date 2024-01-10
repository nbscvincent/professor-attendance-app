package com.shin.myproject.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shin.myproject.data.mainscreenModel.attendance.Attendance


@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attendance: Attendance)

    /**
     * Get attendance data for a specific student and date.
     */
    @Query("SELECT * FROM Attendance WHERE student_id = :studentId AND date = :date")
    suspend fun getAttendanceForStudentAndDate(studentId: Long, date: String): Attendance?


    /**
     * Delete attendance records for a specific student and date.
     */
    @Query("DELETE FROM Attendance WHERE student_id = :studentId AND date = :date")
    suspend fun deleteAttendanceForStudentAndDate(studentId: Long, date: String)
}