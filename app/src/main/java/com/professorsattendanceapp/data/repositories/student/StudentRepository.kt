package com.professorsattendanceapp.data.repositories.student

import com.professorsattendanceapp.data.model.Student

interface StudentRepository {

    suspend fun insertStudent(student : Student)

    suspend fun updateStudent(student : Student)

    suspend fun deleteStudent(student : Student)

}