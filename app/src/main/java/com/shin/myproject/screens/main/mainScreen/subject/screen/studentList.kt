package com.shin.myproject.screens.main.mainScreen.subject.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component.AttendanceCard
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
        studentListViewModel.loadUnmarkedStudents()
        studentListViewModel.loadPresentAttendances()
        studentListViewModel.loadAbsentAttendances()
    }

    val coroutineScope = rememberCoroutineScope()
    val selectedSubjectInfo = subjectInfo.subjectInfo.value
    val selectedStudent by studentListViewModel.selectedStudent.observeAsState(null)

    val students by studentListViewModel.studentList.observeAsState(emptyList())
    val unmarked by studentListViewModel.unmarkedStudents.observeAsState(emptyList())
    val absent by studentListViewModel.absentAttendances.observeAsState(emptyList())
    val present by studentListViewModel.presentAttendances.observeAsState(emptyList())

    val tabs = listOf("Unmarked", "Present", "Absent")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

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
        item {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.Red // Set the color of the indicator to red
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier
                            .background(
                                color = Color.Transparent
                            ),
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }
        }
        when (selectedTabIndex) {
            0 -> {
                if (unmarked.isEmpty()) {
                    item {
                        EmptyStudentListMessage()
                    }
                } else {
                    items(unmarked) { student ->
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
            1 -> {
                items(present) { attendance ->
                    AttendanceCard(
                        attendance = attendance,
                        onClick = {

                        },
                        onPresent = {
                            coroutineScope.launch {
                                studentListViewModel.onPresentAttendance(attendance)
                            }
                        },
                        onAbsent = {
                            coroutineScope.launch {
                                studentListViewModel.onAbsentAttendance(attendance)
                            }
                        },
                        onCancel = {
                            coroutineScope.launch {
                                studentListViewModel.onCancel(attendance.studentId)
                            }
                        }
                    )
                }
            }
            2 -> {
                items(absent) { attendance ->
                    AttendanceCard(
                        attendance = attendance,
                        onClick = {

                        },
                        onPresent = {
                            coroutineScope.launch {
                                studentListViewModel.onPresentAttendance(attendance)
                            }
                        },
                        onAbsent = {
                            coroutineScope.launch {
                                studentListViewModel.onAbsentAttendance(attendance)
                            }
                        },
                        onCancel = {
                            coroutineScope.launch {
                                studentListViewModel.onCancel(attendance.studentId)
                            }
                        }
                    )
                }
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