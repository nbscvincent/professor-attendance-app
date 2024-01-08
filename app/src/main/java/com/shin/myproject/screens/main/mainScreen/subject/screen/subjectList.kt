package com.shin.myproject.screens.main.mainScreen.subject.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.subject.SubjectListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.navigation.routes.SubjectRoute
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SubjectCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    navController: NavController,
    subjectListViewModel: SubjectListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    // Load subjects when the screen is created
    subjectListViewModel.loadSubjects()

    // Observe the subjectList LiveData
    val subjects = subjectListViewModel.subjectList.value ?: emptyList()
    var showArchiveDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var archivedSubject by remember { mutableStateOf<Subject?>(null) }
    var deletedSubject by remember { mutableStateOf<Subject?>(null) }

    LazyColumn {
        // If the subject list is empty, display a message
        if (subjects.isEmpty()) {
            item {
                EmptySubjectListMessage()
            }
        } else {
            // Display each subject using the SubjectCard
            items(subjects) { subject ->
                SubjectCard(
                    subject = subject,
                    onClick = {
                        // Call the onSubjectClicked function when the card is clicked
                        subjectListViewModel.onSubjectClicked(subject.subjectId)
                        navController.navigate(SubjectRoute.StudentsScreen.name)
                    },
                    onArchive = {
                        // Show the archive confirmation dialog
                        archivedSubject = it
                        showArchiveDialog = true
                    },
                    onDelete = {
                        // Show the delete confirmation dialog
                        deletedSubject = it
                        showDeleteDialog = true
                    }
                )
            }
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Archived Subjects",
                        color = Color.Gray,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TextButton(
                            onClick = {
                                      navController.navigate(SubjectRoute.ArchivedSubjectsScreen.name)
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Gray)
                        ) {
                            Text(text = "View All")
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showArchiveDialog) {
        ShowArchiveDialog(
            subject = archivedSubject!!,
            onConfirm = {
                // User confirmed, perform archive action
                subjectListViewModel.onArchive(archivedSubject!!.subjectId)
                showArchiveDialog = false
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showArchiveDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        ShowDeleteDialog(
            subject = deletedSubject!!,
            onConfirm = {
                // User confirmed, perform delete action
                subjectListViewModel.onDelete(deletedSubject!!.subjectId)
                showDeleteDialog = false
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showDeleteDialog = false
            }
        )
    }
}

@Composable
fun EmptySubjectListMessage() {
    Text(
        text = "Add a subject",
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}


@Composable
fun ShowArchiveDialog(
    subject: Subject,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Confirm Archive")
        },
        text = {
            Text(text = "Do you really want to Archive this ${subject.subjectCode} : ${subject.subjectName}")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun ShowDeleteDialog(
    subject: Subject,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Confirm Delete")
        },
        text = {
            Text(text = "Do you really want to Delete this ${subject.subjectCode} : ${subject.subjectName}")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}