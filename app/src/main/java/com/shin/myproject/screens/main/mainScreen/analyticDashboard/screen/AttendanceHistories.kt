package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.analytics.DashboardViewModel
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.ViewModel.subject.SubjectListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components.AttendanceHistoryCard

@Composable
fun AttendanceHistory (
    navController: NavController,
    subjectListViewModel: SubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dashboardListViewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(subjectListViewModel) {
        dashboardListViewModel.loadAttendances()
        studentListViewModel.loadUnmarkedStudents()
        studentListViewModel.loadPresentAttendances()
        studentListViewModel.loadAbsentAttendances()
    }

    val subjectInfo: SubjectInfoHolder = SubjectInfoHolder
    val selectedSubjectInfo = subjectInfo.subjectInfo.value

    val attendances by dashboardListViewModel.attendanceList.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(attendances) { attendance ->
            AttendanceHistoryCard(
                attendance = attendance
            )
        }
    }
}