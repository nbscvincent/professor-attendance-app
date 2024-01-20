package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SubjectInfoItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SubjectDataCard(
    subject: Subject,
    attendanceList: List<Attendance>,
    onClick: () -> Unit,
) {
    val subjectId = subject.subjectId

    // Filter attendanceList for the specific subjectId
    val subjectAttendanceList = attendanceList.filter { it.subjectId == subjectId }

    val totalAttendances = subjectAttendanceList.size
    val presentAttendances = subjectAttendanceList.count { it.attendanceStatus }
    val absentAttendances = totalAttendances - presentAttendances

    val presentPercentage: Float = if (totalAttendances > 0) {
        (presentAttendances.toFloat() / totalAttendances) * 100
    } else {
        0f
    }

    val absentPercentage: Float = if (totalAttendances > 0) {
        (absentAttendances.toFloat() / totalAttendances) * 100
    } else {
        0f
    }

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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(3f)
            ) {
                // Display icons for Code, Name, Day, Time, and Description
                SubjectInfoItem(icon = Icons.Default.Subject, tag = "Subject Code:", content = subject.subjectCode)
                SubjectInfoItem(icon = Icons.Default.Subject, tag = "Subject Name:", content = subject.subjectName)
                SubjectInfoItem(icon = Icons.Default.Today, tag = "Day:", content = "Every ${subject.subjectDay}")

                // Additional display for attendance count and percentage
                SubjectInfoItem(icon = Icons.Default.Today, tag = "Present Count:", content = "${presentAttendances.toString()} (${presentPercentage}%)")
                SubjectInfoItem(icon = Icons.Default.Today, tag = "Absent Count:", content = "${absentAttendances.toString()} (${absentPercentage}%)")

                val currentDateTime = LocalDateTime.now()
                val formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
            }
        }
    }
}