package com.shin.myproject.user.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("SELECT * FROM Students WHERE subject_id = :subjectId")
    fun getStudentsForSubject(subjectId: Long): Flow<List<Student>>

    @Query("SELECT COUNT(*) FROM Students WHERE subject_id = :subjectId AND studentCode = :studentCode")
    suspend fun checkIfStudentExistsInSubject(subjectId: Long, studentCode: Int): Int
}