package com.shin.myproject.screens.main.mainScreen.notification.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shin.myproject.screens.main.mainScreen.notification.model.Notification

@Composable
fun ClickableCard(notification: Notification, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = notification.subjectName
//                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = notification.notificationMessage
//                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Time: ${notification.time}"
//                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Date: ${notification.date}"
//                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Navigate forward"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClickableCardPreview() {
    val sampleNotification = Notification(
        subjectCode = "CSC 331",
        subjectName = "CS Elective I (Track-based)",
        notificationMessage = "Upcoming class: Machine Learning",
        time = "11:00 AM - 2:00 PM",
        date = "Monday",
        notificationTime = "2023-12-01 15:30:00" // Replace with the actual timestamp
    )
    ClickableCard(notification = sampleNotification) {}
}