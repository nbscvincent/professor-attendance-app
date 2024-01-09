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
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SubjectInfoItem
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun StudentCard(
    student: Student,
    onClick: () -> Unit,
    onPresent: (Student) -> Unit,
    onAbsent: (Student) -> Unit,
    onCancel : (Student) -> Unit
) {
    val logo = Icons.Default.Person
    val present = SwipeAction(
        onSwipe = {
            onPresent(student)
            Log.d("SwipeAction", "Present student: ${student.firstname} ${student.lastname}")
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
            onAbsent(student)
            Log.d("SwipeAction", "Absent student: ${student.firstname} ${student.lastname}")
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
            onCancel(student)
            Log.d("SwipeAction", "Attendance canceled for: ${student.firstname} ${student.lastname}")
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
    SwipeableActionsBox(startActions = listOf(present), endActions = listOf(absent)) {
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
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Student ID:", content = "${student.studentCode}")
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Name:", content = "${student.firstname} ${student.lastname}")
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Course:", content = student.course)
                    SubjectInfoItem(icon = Icons.Default.Info, tag = "Year:", content = student.year)
                }
            }
        }
    }
}