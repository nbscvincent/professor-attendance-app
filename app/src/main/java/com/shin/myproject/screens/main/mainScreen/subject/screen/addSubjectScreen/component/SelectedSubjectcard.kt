package com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfo

@Composable
fun SelectedSubjectCard(subjectInfo: SubjectInfo?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display subject code and name with icons
            SubjectInfoItem(icon = Icons.Default.Subject, tag = "Subject Code:", content = "${subjectInfo?.subjectCode ?: ""}")
            SubjectInfoItem(icon = Icons.Default.Subject, tag = "Subject Name:", content = "${subjectInfo?.subjectName ?: ""}")

            // Display additional subject information
            SubjectInfoItem(icon = Icons.Default.Today, tag = "Day:", content = "${subjectInfo?.subjectDay ?: ""}")
            SubjectInfoItem(icon = Icons.Default.AccessTime, tag = "Time:", content = "${subjectInfo?.startTime ?: ""} - ${subjectInfo?.endTime ?: ""}")
            SubjectInfoItem(icon = Icons.Default.Subject, tag = "Description:", content = "${subjectInfo?.subjectDescription ?: ""}")
        }
    }
}

@Composable
fun SubjectInfoItem(icon: ImageVector, tag: String, content: String) {
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
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = content,
            modifier = Modifier.weight(2f)
        )
    }
}