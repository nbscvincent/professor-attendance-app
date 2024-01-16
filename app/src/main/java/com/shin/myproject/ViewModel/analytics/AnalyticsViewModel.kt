package com.shin.myproject.ViewModel.analytics

import androidx.lifecycle.ViewModel
import com.shin.myproject.user.repository.attendancce.AttendanceRepository
import com.shin.myproject.user.repository.student.StudentRepository
import com.shin.myproject.user.repository.subject.SubjectRepository


class DashboardViewModel(
    private val subjectRepository: SubjectRepository,
    private val studentRepository: StudentRepository,
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

}