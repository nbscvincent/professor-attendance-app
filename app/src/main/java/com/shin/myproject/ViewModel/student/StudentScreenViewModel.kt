package com.shin.myproject.ViewModel.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component.SelectedStudent
import com.shin.myproject.user.repository.attendancce.AttendanceRepository
import com.shin.myproject.user.repository.student.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class StudentListViewModel (
    private val studentRepository: StudentRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _studentsForSelectedSubject = MutableLiveData<List<Student>>()
    val studentsForSelectedSubject: LiveData<List<Student>> get() = _studentsForSelectedSubject

    fun loadStudentsForSelectedSubject(subjectId: Long) {
        viewModelScope.launch {
            studentRepository.getStudentsForSubject(subjectId).collect { students ->
                _studentsForSelectedSubject.value = students
            }
        }
    }

    private val _selectedStudent = MutableLiveData<SelectedStudent?>()
    val selectedStudent: LiveData<SelectedStudent?> get() = _selectedStudent

    fun setSelectedStudent(selectedStudent: SelectedStudent?) {
        _selectedStudent.value = selectedStudent
    }

    suspend fun onPresent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, true)
            attendanceRepository.insertAttendance(attendance)
        }
    }

    suspend fun onAbsent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, false)
            attendanceRepository.insertAttendance(attendance)
        }
    }

    private fun createAttendanceFromStudent(student: Student, isPresent: Boolean): Attendance {
        val currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Manila"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        return Attendance(
            studentId = student.studentId,
            studentCode = student.studentCode,
            firstname = student.firstname,
            lastname = student.lastname,
            attendanceStatus = isPresent,
            date = currentDateTime.split(" ")[0], // Extracting date part
            time = currentDateTime.split(" ")[1] // Extracting time part
        )
    }
}