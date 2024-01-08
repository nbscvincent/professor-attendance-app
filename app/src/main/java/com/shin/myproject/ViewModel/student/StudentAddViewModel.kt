package com.shin.myproject.ViewModel.student

import androidx.lifecycle.ViewModel
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import com.shin.myproject.user.repository.student.StudentRepository

class StudentAddViewModel (
    private val studentRepository: StudentRepository,
    private val studentListViewModel: StudentListViewModel
) : ViewModel() {


    suspend fun insertStudent(
        subjectId: Long,
        studentCode: Int,
        firstName: String,
        lastName: String,
        isWorking: Boolean,
        course: String,
        year: String
    ): StudentAddResult {
        if (studentCode <= 0 || firstName.isBlank() || lastName.isBlank()) {
            return StudentAddResult.Failure("Student information cannot be blank")
        }

        if (studentRepository.checkIfStudentExistsInSubject(subjectId, studentCode)) {
            return StudentAddResult.Failure("Student with the same code already exists in this subject")
        }

        val newStudent = Student(
            subjectId = subjectId,
            studentCode = studentCode,
            firstname = firstName,
            lastname = lastName,
            isWorkingStudent = isWorking,
            course = course,
            year = year
        )

        studentRepository.insertStudent(newStudent)
        return StudentAddResult.Success("Student added successfully")
    }
}

sealed class StudentAddResult {
    data class Success(val message: String = "") : StudentAddResult()
    data class Failure(val message: String) : StudentAddResult()
}