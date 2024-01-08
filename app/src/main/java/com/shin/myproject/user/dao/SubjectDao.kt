package com.shin.myproject.user.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Delete
    suspend fun delete(subject: Subject)

    @Query("SELECT * FROM subjects WHERE subjectCode = :code")
    suspend fun getSubjectByCode(code: String): Subject?

    @Query("SELECT * FROM subjects WHERE user_id = :userId AND subjectDay = :day")
    suspend fun getSubjectsByUserIdAndDay(userId: Long, day: String): List<Subject>

    @Query("SELECT * FROM subjects WHERE user_id = :userId")
    suspend fun getAllSubjectsByUserId(userId: Long): List<Subject>

    @Query("SELECT * FROM subjects WHERE subject_id = :subjectId")
    suspend fun getSubjectById(subjectId: Long): Subject?

    @Query("SELECT * FROM subjects WHERE user_id = :userId AND archived = :includeArchived")
    suspend fun getAllSubjectsByUserId(userId: Long, includeArchived: Boolean): List<Subject>
}