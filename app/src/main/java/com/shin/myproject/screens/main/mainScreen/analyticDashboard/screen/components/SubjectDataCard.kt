package com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SubjectInfoItem
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun SubjectDataCard(
    subject: Subject,
    onClick: () -> Unit,
){
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
                SubjectInfoItem(icon = Icons.Default.Subject, tag = "Code:", content = subject.subjectCode)
                SubjectInfoItem(icon = Icons.Default.Subject, tag = "Name:", content = subject.subjectName)
                SubjectInfoItem(icon = Icons.Default.Today, tag = "Day:", content = subject.subjectDay)

                Text(
                    text = "Attendances of : ",
                    fontWeight = FontWeight.SemiBold
                )

                // Replace startTime with current date and endTime with current time
                val currentDateTime = LocalDateTime.now()
                val formattedDate = currentDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                val formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
                SubjectInfoItem(icon = Icons.Default.CalendarMonth, tag = "Date:", content = formattedDate)
                SubjectInfoItem(icon = Icons.Default.AccessTime, tag = "Time:", content = formattedTime)
            }
        }
    }
}