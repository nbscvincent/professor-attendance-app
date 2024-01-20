package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shin.myproject.data.mainscreenModel.attendance.Attendance
import java.time.format.DateTimeFormatter

@Composable
fun AttendanceHistoryCard(
    attendance : Attendance
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
                val formattedDate = attendance.date.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                val formattedTime = attendance.time.format(DateTimeFormatter.ofPattern("hh:mm a"))
                // Display icons for Code, Name, Day, Time, and Description
                SubjecthistoryCardItem(icon = Icons.Default.Subject, tag = "Student Name:", content = "${attendance.firstname} ${attendance.lastname}")
                SubjecthistoryCardItem(icon = Icons.Default.CalendarMonth, tag = "Attendance Date:", content = formattedDate)
                SubjecthistoryCardItem(icon = Icons.Default.AccessTime, tag = "Attendance Time:", content = formattedTime)

                // Display attendance status
                val statusText = if (attendance.attendanceStatus) "Present" else "Absent"
                SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Attendance Status:", content = statusText)
            }
        }
    }
}


@Composable
fun SubjecthistoryCardItem(icon: ImageVector, tag: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = tag,
            modifier = Modifier.weight(2f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = content,
            modifier = Modifier.weight(3f)
        )
    }
}