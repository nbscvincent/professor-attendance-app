package com.professorsattendanceapp.samplektor.local.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.professorsattendanceapp.samplektor.local.model.SampleStudent

@Dao
interface SampleKtorInterfaceStudent {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sampleStudent: SampleStudent)

    @Update
    suspend fun update(sampleStudent: SampleStudent)

    @Delete
    suspend fun delete(sampleStudent: SampleStudent)

}