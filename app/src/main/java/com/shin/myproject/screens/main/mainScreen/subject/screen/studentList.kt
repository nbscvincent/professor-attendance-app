package com.shin.myproject.screens.main.mainScreen.subject.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component.SelectedStudent
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component.StudentCard
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SelectedSubjectCard
import kotlinx.coroutines.launch

@Composable
fun StudentScreen(
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectInfo: SubjectInfoHolder = SubjectInfoHolder
) {
    LaunchedEffect(studentListViewModel) {
        studentListViewModel.loadStudents()
    }

    val coroutineScope = rememberCoroutineScope()
    val selectedSubjectInfo = subjectInfo.subjectInfo.value
    val selectedStudent by studentListViewModel.selectedStudent.observeAsState(null)
    val students by studentListViewModel.studentList.observeAsState(emptyList())



    DisposableEffect(selectedStudent) {
        onDispose {
            selectedStudent?.let { student ->
                Log.d("StudentScreen", "Selected Student: ${student.firstname} ${student.lastname}")
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            SelectedSubjectCard(subjectInfo = selectedSubjectInfo)
        }

        if (students.isEmpty()) {
            item {
                EmptyStudentListMessage()
            }
        } else {
            items(students) { student ->
                StudentCard(
                    student = student,
                    onClick = {
                        coroutineScope.launch {
                            studentListViewModel.setSelectedStudent(
                                SelectedStudent(
                                    studentId = student.studentId,
                                    subjectId = selectedSubjectInfo?.subjectId ?: 0,
                                    studentCode = student.studentCode,
                                    firstname = student.firstname,
                                    lastname = student.lastname,
                                    course = student.course,
                                    year = student.year,
                                    isWorkingStudent = student.isWorkingStudent
                                )
                            )
                        }
                    },
                    onPresent = {
                        coroutineScope.launch {
                            studentListViewModel.onPresent(student)
                        }
                    },
                    onAbsent = {
                        coroutineScope.launch {
                            studentListViewModel.onAbsent(student)
                        }
                    },
                    onCancel = {
                        coroutineScope.launch {
                            studentListViewModel.onCancel(student.studentId)
                        }
                    }
                )
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