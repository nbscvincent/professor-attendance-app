package com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SubjectInfoItem
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun AttendanceCard(
    attendance: Attendance,
    onClick: () -> Unit,
    onPresent: (Attendance) -> Unit,
    onAbsent: (Attendance) -> Unit,
    onCancel: (Attendance) -> Unit,
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val logo = Icons.Default.Person
    val present = SwipeAction(
        onSwipe = {
            onPresent(attendance)
            Log.d("SwipeAction", "Present attendance: ${attendance.firstname} ${attendance.lastname}")
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Check,
                contentDescription = "present student",
                tint = Color.White
            )
        },
        background = Color.Green
    )
    val absent = SwipeAction(
        onSwipe = {
            onAbsent(attendance)
            Log.d("SwipeAction", "Absent attendance: ${attendance.firstname} ${attendance.lastname}")
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "absent student",
                tint = Color.White
            )
        },
        background = Color.Red
    )
    val cancel = SwipeAction(
        onSwipe = {
            onCancel(attendance)
            Log.d("SwipeAction", "Attendance canceled for: ${attendance.firstname} ${attendance.lastname}")
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Remove,
                contentDescription = "cancel student attendance",
                tint = Color.White
            )
        },
        background = Color.Gray
    )

    // ... (remaining code)

    val startActions = if (attendance.attendanceStatus) {
        listOf(absent)
    } else {
        listOf(present)
    }

    val endActions = if (attendance.attendanceStatus) {
        listOf(cancel)
    } else {
        listOf(cancel)
    }

    SwipeableActionsBox(startActions = startActions, endActions = endActions) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable {
                    onClick()
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(120.dp)
                            .padding(horizontal = 10.dp),
                        imageVector = logo,
                        contentDescription = "logo"
                    )
                }

                Column(
                    modifier = Modifier.weight(3f),
                ) {
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Student ID:", content = "${attendance.studentCode}")
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Name:", content = "${attendance.firstname} ${attendance.lastname}")
                    SubjectInfoItem(
                        icon = Icons.Default.Info,
                        tag = "Date:",
                        content = formatDate(attendance.date)
                    )
                    SubjectInfoItem(
                        icon = Icons.Default.Info,
                        tag = "Time:",
                        content = formatTimeIn12Hours(attendance.time)
                    )
                }
            }
        }
    }
}



@Composable
fun formatTimeIn12Hours(time: String): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    val parsedTime = LocalTime.parse(time, formatter)
    val formattedTime = DateTimeFormatter.ofPattern("hh:mm a").format(parsedTime)
    return formattedTime
}


@Composable
fun formatDate(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val parsedDate = LocalDate.parse(date, formatter)
    val formattedDate = DateTimeFormatter.ofPattern("MMMM d, yyyy").format(parsedDate)
    return formattedDate
}