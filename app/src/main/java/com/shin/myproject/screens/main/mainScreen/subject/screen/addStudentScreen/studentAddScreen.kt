package com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.student.StudentAddResult
import com.shin.myproject.ViewModel.student.StudentAddViewModel
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfo
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.navigation.routes.SubjectRoute
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.RadioButtonOption
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.SelectedSubjectCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun StudentAddScreen(
    navController : NavController,
    studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory),
    studentAddViewModel: StudentAddViewModel = viewModel(factory = AppViewModelProvider.Factory),
    subjectInfo: SubjectInfo? = SubjectInfoHolder.subjectInfo.value
) {
    val coroutineScope = rememberCoroutineScope()
    var studentCode by remember { mutableIntStateOf(0) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var isWorking by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("") }
    var selectedYearLevel by remember { mutableStateOf("") }
    var failureMessage by remember { mutableStateOf<String?>(null) }
    val subjectInfo by SubjectInfoHolder.subjectInfo.observeAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            // Use the SubjectCard composable to display the subject information
            SelectedSubjectCard(subjectInfo = subjectInfo)
        }

        item{
            OutlinedTextField(
                value = studentCode.toString(),
                onValueChange = {
                    studentCode = it.toIntOrNull() ?: 0
                },
                label = { Text("Student Number") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item{
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = autocapitalizeFirstLetter(it) },
                label = { Text("Firstname") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item{
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = autocapitalizeFirstLetter(it) },
                label = { Text("Lastname") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            Text(
                text = "Working Student or NOT?",
                style = TextStyle(fontSize = 17.sp),
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .width(200.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isWorking,
                        onClick = {
                            isWorking = true
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "YES",
                        color = if (isWorking) Color.Red else Color.Gray
                    )
                }

                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = !isWorking,
                        onClick = {
                            isWorking = false
                        },
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = "NO",
                        color = if (!isWorking) Color.Red else Color.Gray
                    )
                }
            }
        }

        item {
            Text(
                text = "Select Course:",
                style = TextStyle(fontSize = 17.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButtonOption(
                    text = "BSCS",
                    selectedOption = selectedCourse,
                    onOptionSelected = { selectedCourse = it }
                )

                RadioButtonOption(
                    text = "BSA",
                    selectedOption = selectedCourse,
                    onOptionSelected = { selectedCourse = it }
                )

                RadioButtonOption(
                    text = "BSAIS",
                    selectedOption = selectedCourse,
                    onOptionSelected = { selectedCourse = it }
                )

                RadioButtonOption(
                    text = "BSTM",
                    selectedOption = selectedCourse,
                    onOptionSelected = { selectedCourse = it }
                )

                RadioButtonOption(
                    text = "BSENTREP",
                    selectedOption = selectedCourse,
                    onOptionSelected = { selectedCourse = it }
                )
            }
        }

        item {
            Text(
                text = "Select Year Level:",
                style = TextStyle(fontSize = 17.sp),
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButtonOption(
                    text = "1",
                    selectedOption = selectedYearLevel,
                    onOptionSelected = { selectedYearLevel = it }
                )

                RadioButtonOption(
                    text = "2",
                    selectedOption = selectedYearLevel,
                    onOptionSelected = { selectedYearLevel = it }
                )

                RadioButtonOption(
                    text = "3",
                    selectedOption = selectedYearLevel,
                    onOptionSelected = { selectedYearLevel = it }
                )

                RadioButtonOption(
                    text = "4",
                    selectedOption = selectedYearLevel,
                    onOptionSelected = { selectedYearLevel = it }
                )
            }
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // Reset all variables to their initial state
                        studentCode = 0
                        firstName = ""
                        lastName = ""
                        isWorking = false
                        selectedCourse = ""
                        selectedYearLevel = ""

                        // Navigate back to the StudentsScreen
                        navController.navigate(SubjectRoute.StudentsScreen.name)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Cancel", color = Color.White)
                    Icon(
                        imageVector = Icons.Default.Cancel,
                        contentDescription = "cancel button",
                        tint = Color.White
                    )
                }

                Button(
                    onClick = {
                        // Use coroutineScope to launch a coroutine for the student insertion
                        coroutineScope.launch {
                            val currentSubjectId = subjectInfo?.subjectId ?: 0
                            val result = studentAddViewModel.insertStudent(
                                subjectId = currentSubjectId,
                                studentCode = studentCode,
                                firstName = firstName,
                                lastName = lastName,
                                isWorking = isWorking,
                                course = selectedCourse,
                                year = selectedYearLevel
                            )

                            // Check the result and update the failure message
                            if (result is StudentAddResult.Failure) {
                                failureMessage = result.message
                                // Log the failure message to the Logcat
                                Log.e("StudentAddScreen", "Student insertion failed: $failureMessage")
                            } else {
                                // Navigate back to the StudentsScreen
                                navController.navigate(SubjectRoute.StudentsScreen.name)
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save", color = Color.White)
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "save button",
                        tint = Color.White
                    )
                }
            }
        }

        item{
            failureMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}



fun autocapitalizeFirstLetter(text: String): String {
    return text.split(" ").joinToString(" ") { it.lowercase().capitalize() }
}