package com.shin.myproject.user.repository.student


import com.shin.myproject.data.mainscreenModel.studentModel.Student
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    /**
     * Insert a student into the data source.
     */
    suspend fun insertStudent(student: Student)

    /**
     * Delete a student from the data source.
     */
    suspend fun deleteStudent(student: Student)

    /**
     * Update a student in the data source.
     */
    suspend fun updateStudent(student: Student)

    /**
     * Update the marked status of a student.
     */
    suspend fun updateStudentMarkedStatus(studentId: Long, marked: Boolean)

    /**
     * Get all students for a specific subject.
     */
    fun getStudentsForSubject(subjectId: Long): Flow<List<Student>>

    suspend fun checkIfStudentExistsInSubject(subjectId: Long, studentCode: Int): Boolean
}