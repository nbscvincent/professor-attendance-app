package com.professorsattendanceapp.samplektor.local.repository

import com.professorsattendanceapp.data.model.Attendance
import com.professorsattendanceapp.samplektor.local.model.SampleFaculty

interface SampleFacultyRepository {

    suspend fun insertFaculty(sampleFaculty: SampleFaculty)

    suspend fun updateFaculty(sampleFaculty: SampleFaculty)

    suspend fun deleteFaculty(sampleFaculty: SampleFaculty)

}