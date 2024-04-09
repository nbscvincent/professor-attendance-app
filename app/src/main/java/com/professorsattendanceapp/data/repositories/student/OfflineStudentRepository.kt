package com.professorsattendanceapp.data.repositories.student

import com.professorsattendanceapp.data.interfaces.StudentDao
import com.professorsattendanceapp.data.model.Student

class OfflineStudentRepository(private val studentDao: StudentDao) : StudentRepository {

    override suspend fun insertStudent(student: Student) = studentDao.insert(student)

    override suspend fun updateStudent(student: Student) = studentDao.update(student)

    override suspend fun deleteStudent(student: Student) = studentDao.delete(student)

}