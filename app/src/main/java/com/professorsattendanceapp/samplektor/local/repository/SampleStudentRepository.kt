package com.professorsattendanceapp.samplektor.local.repository

import com.professorsattendanceapp.samplektor.local.model.SampleStudent

interface SampleStudentRepository {

    suspend fun insertStudent(sampleStudent: SampleStudent)

    suspend fun updateStudent(sampleStudent: SampleStudent)

    suspend fun deleteStudent(sampleStudent: SampleStudent)

}