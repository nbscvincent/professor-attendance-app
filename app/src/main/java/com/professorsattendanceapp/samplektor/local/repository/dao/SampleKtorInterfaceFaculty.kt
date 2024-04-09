package com.professorsattendanceapp.samplektor.local.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.professorsattendanceapp.samplektor.local.model.SampleFaculty

@Dao
interface SampleKtorInterfaceFaculty {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sampleFaculty: SampleFaculty)

    @Update
    suspend fun update(sampleFaculty: SampleFaculty)

    @Delete
    suspend fun delete(sampleFaculty: SampleFaculty)

}