package com.shin.myproject.ViewModel.analytics

import androidx.lifecycle.ViewModel
import com.shin.myproject.user.repository.attendancce.AttendanceRepository


class DashboardViewModel(
    private val attendanceRepository: AttendanceRepository
) : ViewModel() {

}