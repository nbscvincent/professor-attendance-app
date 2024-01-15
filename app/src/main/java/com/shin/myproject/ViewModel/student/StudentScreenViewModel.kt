package com.shin.myproject.ViewModel.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    private val _unmarkedStudents = MutableLiveData<List<Student>>()
    val unmarkedStudents: LiveData<List<Student>> get() = _unmarkedStudents

    fun loadStudents() {
        viewModelScope.launch {
            val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return@launch
            studentRepository.getStudentsForSubject(subjectId).collect { students ->
                _studentList.value = students
                loadUnmarkedStudents()
            }
        }
    }

    fun loadUnmarkedStudents() {
        viewModelScope.launch {
            val unmarkedStudents = _studentList.value?.filter { it.marked == false } ?: emptyList()
            _unmarkedStudents.value = unmarkedStudents
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

    fun attendanceForCurrentDate(): LiveData<List<Attendance>> {
        val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila"))
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return MutableLiveData(emptyList())

        return attendanceRepository.getStudentsForDate(subjectId, currentDate)
            .asLiveData(viewModelScope.coroutineContext)
    }

    suspend fun onPresent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, true)
            attendanceRepository.insertAttendance(attendance)
            getAttendanceStatusLiveData(student.studentId).postValue(true)
            studentRepository.updateStudentMarkedStatus(student.studentId, true)

            // Reload the present attendances and unmarked students
            loadPresentAttendances()
            loadUnmarkedStudents()
        }
    }

    suspend fun onAbsent(student: Student) {
        withContext(Dispatchers.IO) {
            val attendance = createAttendanceFromStudent(student, false)
            attendanceRepository.insertAttendance(attendance)
            getAttendanceStatusLiveData(student.studentId).postValue(true)
            studentRepository.updateStudentMarkedStatus(student.studentId, true)

            // Reload the absent attendances and unmarked students
            loadAbsentAttendances()
            loadUnmarkedStudents()
        }
    }


    suspend fun onPresentAttendance(attendance: Attendance) {
        withContext(Dispatchers.IO) {
            attendanceRepository.updateAttendanceStatus(attendance.attendanceId, true)
            getAttendanceStatusLiveData(attendance.studentId).postValue(true)
            studentRepository.updateStudentMarkedStatus(attendance.studentId, true)

            // Reload the present attendances, absent attendances, and unmarked students
            loadPresentAttendances()
            loadAbsentAttendances()
            loadUnmarkedStudents()
        }
    }

    suspend fun onAbsentAttendance(attendance: Attendance) {
        withContext(Dispatchers.IO) {
            attendanceRepository.updateAttendanceStatus(attendance.attendanceId, false)
            getAttendanceStatusLiveData(attendance.studentId).postValue(true)
            studentRepository.updateStudentMarkedStatus(attendance.studentId, true)

            // Reload the present attendances, absent attendances, and unmarked students
            loadPresentAttendances()
            loadAbsentAttendances()
            loadUnmarkedStudents()
        }
    }

    fun onCancel(studentId: Long) {
        viewModelScope.launch {
            val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            // Remove attendance records for the selected student and current date
            withContext(Dispatchers.IO) {
                attendanceRepository.deleteAttendanceForStudentAndDate(studentId, currentDate)
                getAttendanceStatusLiveData(studentId).postValue(false)
                studentRepository.updateStudentMarkedStatus(studentId, false)
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

    private val _presentAttendances = MutableLiveData<List<Attendance>>()
    val presentAttendances: LiveData<List<Attendance>> get() = _presentAttendances

    private val _absentAttendances = MutableLiveData<List<Attendance>>()
    val absentAttendances: LiveData<List<Attendance>> get() = _absentAttendances

    fun loadPresentAttendances() {
        viewModelScope.launch {
            val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return@launch
            val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).split(" ")[0]

            attendanceRepository.getStudentsWithAttendanceStatus(subjectId, true, currentDate).collect{ presentAttendances ->
                _presentAttendances.value = presentAttendances
            }
        }
    }


    fun loadAbsentAttendances() {
        viewModelScope.launch {
            val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return@launch
            val currentDate = LocalDateTime.now(ZoneId.of("Asia/Manila"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).split(" ")[0]

            attendanceRepository.getStudentsWithAttendanceStatus(subjectId, false, currentDate).collect{ absentAttendances ->
                _absentAttendances.value = absentAttendances
            }
        }
    }
}