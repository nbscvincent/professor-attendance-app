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
import com.shin.myproject.navigation.routes.DashboardRoute
import com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components.AttendanceStatisticsCard
import com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components.SubjectDataCard

@Composable
fun Dashboard(
    navController: NavController,
    subjectListViewModel: SubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dashboardListViewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(subjectListViewModel) {
        subjectListViewModel.loadSubjects()
        studentListViewModel.loadStudents()
        studentListViewModel.loadUnmarkedStudents()
        studentListViewModel.loadPresentAttendances()
        studentListViewModel.loadAbsentAttendances()
    }

    LaunchedEffect(dashboardListViewModel) {
        dashboardListViewModel.loadSubjects()
        dashboardListViewModel.loadAttendances()
        dashboardListViewModel.allStudentsOfUser()
    }

    val subjectInfo: SubjectInfoHolder = SubjectInfoHolder
    val attendanceList by dashboardListViewModel.attendanceList.observeAsState(emptyList())
    val subjects by dashboardListViewModel.subjectList.observeAsState(emptyList())
    val students by studentListViewModel.studentList.observeAsState(emptyList())
    val allStudents by dashboardListViewModel.allStudentsOfUser.observeAsState(0)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            AttendanceStatisticsCard(
                totalStudents = allStudents,
                totalSubjects = subjects.size,
                presentCount = attendanceList.count { it.attendanceStatus },
                absentCount = attendanceList.count { !it.attendanceStatus }
            )
        }

        items(subjects) { subject ->
            // Observe live attendance data for the specific subject
            val attendanceListForSubject by dashboardListViewModel
                .getAttendanceListForSubject(subject.subjectId)
                .observeAsState(emptyList())

            SubjectDataCard(
                subject = subject,
                attendanceList = attendanceListForSubject,
                onClick = {
                    subjectListViewModel.onSubjectClicked(subject.subjectId)
                    navController.navigate(DashboardRoute.AttendanceHistory.name)
                }
            )
        }
    }
}