package com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectLogos
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox


@Composable
fun ArchivedSubjectCard(
    subject: Subject,
    onClick: () -> Unit,
    onUnarchive: (Subject) -> Unit,
    onDelete: (Subject) -> Unit
) {
    val logo = SubjectLogos.getLogoForSubject(subject.subjectName)
    val unarchive = SwipeAction(
        onSwipe = {
            onUnarchive(subject)
            Log.d("SwipeAction", "Unarchived subject: ${subject.subjectName}")
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Archive,
                contentDescription = "archive",
                tint = Color.White
            )
        },
        background = Color.LightGray
    )
    val delete = SwipeAction(
        onSwipe = {
            onDelete(subject)
            Log.d("SwipeAction", "Deleted subject: ${subject.subjectName}")
        },
        icon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "delete",
                tint = Color.White
            )
        },
        background = Color.Red
    )
    SwipeableActionsBox(startActions = listOf(unarchive), endActions = listOf(delete)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
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
                    // Display icons for Code, Name, Day, Time, and Description
                    SubjectInfoItem(icon = Icons.Default.Subject, tag = "Code:", content = "${subject.subjectCode}")
                    SubjectInfoItem(icon = Icons.Default.Subject, tag = "Name:", content = "${subject.subjectName}")
                    SubjectInfoItem(icon = Icons.Default.Today, tag = "Day:", content = "${subject.subjectDay}")
                    SubjectInfoItem(icon = Icons.Default.AccessTime, tag = "Time:", content = "${subject.startTime} - ${subject.endTime}")
                    SubjectInfoItem(icon = Icons.Default.Subject, tag = "Description:", content = "${subject.subjectDescription}")
                }
            }
        }

        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(horizontal = 8.dp),
            imageVector = Icons.Default.Archive,
            contentDescription = "Archive",
            tint = Color.Gray
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .padding(horizontal = 8.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            tint = Color.Gray
        )
    }
}

