package com.professorsattendanceapp.data.repositories.faculty

import com.professorsattendanceapp.data.model.Faculty

interface FacultyRepository {

    suspend fun insertFaculty(attendance: Faculty)

    suspend fun updateFaculty(attendance : Faculty)

    suspend fun deleteFaculty(attendance : Faculty)

}