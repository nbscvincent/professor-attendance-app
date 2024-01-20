package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun AttendanceStatisticsCard(
    totalStudents: Int,
    totalSubjects: Int,
    presentCount: Int,
    absentCount: Int
) {
    val totalAttendance = presentCount + absentCount
    val presentPercentage = if (totalAttendance > 0) (presentCount.toDouble() / totalAttendance.toDouble()) * 100 else 0.0
    val absentPercentage = if (totalAttendance > 0) (absentCount.toDouble() / totalAttendance.toDouble()) * 100 else 0.0

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Attendance Statistics", modifier = Modifier.padding(bottom = 8.dp))
            SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Total Subjects:", content = "${totalSubjects}")
            SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Total Students:", content = "${totalStudents}")
            SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Total Attendance:", content = "${totalAttendance}")
            SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Present:", content = "$presentCount (${presentPercentage.roundToInt()}%)")
            SubjecthistoryCardItem(icon = Icons.Default.List, tag = "Absent:", content = "$absentCount  (${absentPercentage.roundToInt()}%)")
        }
    }
}