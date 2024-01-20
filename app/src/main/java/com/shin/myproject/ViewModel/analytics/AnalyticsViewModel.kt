package com.shin.myproject.ViewModel.analytics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.data.mainscreenModel.subjectModel.SelectedSubjectIdHolder
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfo
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.user.repository.attendancce.AttendanceRepository
import com.shin.myproject.user.repository.student.StudentRepository
import com.shin.myproject.user.repository.subject.SubjectRepository
import kotlinx.coroutines.launch


class DashboardViewModel(
    private val attendanceRepository: AttendanceRepository,
    private val subjectRepository: SubjectRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {
    val subjectInfo : SubjectInfo? = SubjectInfoHolder.subjectInfo.value

    private val _attendanceList = MutableLiveData<List<Attendance>>()
    val attendanceList: LiveData<List<Attendance>> get() = _attendanceList

    fun loadAttendances() {
        viewModelScope.launch {
            val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return@launch
            attendanceRepository.getAttendancesForSubject(subjectId).collect { attendance ->
                _attendanceList.value = attendance
            }
        }
    }

    private val loggedInUserId: Long = LoggedInUserHolder.getLoggedInUser()?.userId ?: -1L

    private val _subjectList = MutableLiveData<List<Subject>>()
    val subjectList: LiveData<List<Subject>> get() = _subjectList

    // Function to retrieve all subjects of the loggedInUser based on their userId
    fun loadSubjects() {
        if (loggedInUserId != -1L) {
            viewModelScope.launch {
                val subjects = subjectRepository.getAllSubjectsByUserId(loggedInUserId, includeArchived = false)
                _subjectList.postValue(subjects)
            }
        }
    }

    fun getAttendanceListForSubject(subjectId: Long): LiveData<List<Attendance>> {
        val liveData = MutableLiveData<List<Attendance>>()

        viewModelScope.launch {
            attendanceRepository.getAttendancesForSubject(subjectId).collect { attendance ->
                liveData.value = attendance
            }
        }

        return liveData
    }


    private val _studentCountForLoggedInUser = MutableLiveData<Int>()
    val allStudentsOfUser: LiveData<Int> get() = _studentCountForLoggedInUser

    fun allStudentsOfUser() {
        if (loggedInUserId != -1L) {
            viewModelScope.launch {
                val studentCount = studentRepository.getStudentCountForLoggedInUser(loggedInUserId)
                _studentCountForLoggedInUser.value = studentCount
            }
        }
    }
}