package com.shin.myproject.screens.main.mainScreen.subject.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component.StudentCard
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SelectedSubjectCard

@Composable
fun StudentScreen(
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectInfo: SubjectInfoHolder = SubjectInfoHolder
) {
    val selectedSubjectInfo = subjectInfo.subjectInfo.value

    LaunchedEffect(selectedSubjectInfo) {
        selectedSubjectInfo?.let { subjectInfo ->
            studentListViewModel.loadStudentsForSelectedSubject(subjectInfo.subjectId)
        }
    }

    val students by studentListViewModel.studentsForSelectedSubject.observeAsState(emptyList())

    LazyColumn {
        item {
            SelectedSubjectCard(subjectInfo = selectedSubjectInfo)
        }

        if (students.isEmpty()) {
            item {
                EmptyStudentListMessage()
            }
        } else {
            items(students) { student ->
                StudentCard(student = student)
            }
        }
    }
}

@Composable
fun EmptyStudentListMessage() {
    Text(
        text = "Add a student",
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}