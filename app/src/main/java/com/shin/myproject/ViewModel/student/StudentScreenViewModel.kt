package com.shin.myproject.ViewModel.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import com.shin.myproject.data.mainscreenModel.subjectModel.SelectedSubjectIdHolder
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfo
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
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

    val subjectInfo : SubjectInfo? = SubjectInfoHolder.subjectInfo.value
    private val _studentList = MutableLiveData<List<Student>>()
    val studentList: LiveData<List<Student>> get() = _studentList

    fun loadStudents() {
        viewModelScope.launch {
            val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return@launch
            studentRepository.getStudentsForSubject(subjectId).collect { students ->
                _studentList.value = students
            }
        }
    }

    private val _selectedStudent = MutableLiveData<SelectedStudent?>()
    val selectedStudent: LiveData<SelectedStudent?> get() = _selectedStudent

    fun setSelectedStudent(selectedStudent: SelectedStudent?) {
        _selectedStudent.value = selectedStudent
    }

    private val _attendanceStatusMap = mutableMapOf<Long, MutableLiveData<Boolean>>()

    fun getAttendanceStatusLiveData(studentId: Long): MutableLiveData<Boolean> {
        return _attendanceStatusMap.getOrPut(studentId) { MutableLiveData<Boolean>() }
    }

    suspend fun onPresent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, true)
            attendanceRepository.insertAttendance(attendance)
            getAttendanceStatusLiveData(student.studentId).postValue(true)
        }
    }

    suspend fun onAbsent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, false)
            attendanceRepository.insertAttendance(attendance)
            getAttendanceStatusLiveData(student.studentId).postValue(true)
        }
    }

    fun onCancel(studentId: Long) {
        viewModelScope.launch {
            val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            // Remove attendance records for the selected student and current date
            withContext(Dispatchers.IO) {
                attendanceRepository.deleteAttendanceForStudentAndDate(studentId, currentDate)
                getAttendanceStatusLiveData(studentId).postValue(false)
            }
        }
    }
    private fun createAttendanceFromStudent(student: Student, isPresent: Boolean): Attendance {
        val currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Manila"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val currentSubjectId = subjectInfo?.subjectId ?: 0

        return Attendance(
            subjectId = currentSubjectId,
            studentId = student.studentId,
            studentCode = student.studentCode,
            firstname = student.firstname,
            lastname = student.lastname,
            attendanceStatus = isPresent,
            date = currentDateTime.split(" ")[0], // Extracting date part
            time = currentDateTime.split(" ")[1] // Extracting time part
        )
    }

    // Check if the student has attendance for the current date
    suspend fun hasAttendanceForCurrentDate(studentId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val attendance = attendanceRepository.getAttendanceForStudentAndDate(studentId, currentDate)
            attendance != null
        }
    }
}