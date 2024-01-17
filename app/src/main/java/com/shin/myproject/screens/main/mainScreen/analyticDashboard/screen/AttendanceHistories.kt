package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SelectedSubjectCard

@Composable
fun AttendanceHistory (
    navController: NavController,
    subjectListViewModel: SubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    dashboardListViewModel: DashboardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(subjectListViewModel) {
        studentListViewModel.loadStudents()
        studentListViewModel.loadUnmarkedStudents()
        studentListViewModel.loadPresentAttendances()
        studentListViewModel.loadAbsentAttendances()
    }

    val subjectInfo: SubjectInfoHolder = SubjectInfoHolder
    val selectedSubjectInfo = subjectInfo.subjectInfo.value

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SelectedSubjectCard(subjectInfo = selectedSubjectInfo)
        }

    }
}