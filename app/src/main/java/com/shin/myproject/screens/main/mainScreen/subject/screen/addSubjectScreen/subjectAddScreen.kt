package com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.subject.SubjectAddInputs
import com.shin.myproject.ViewModel.subject.SubjectAddResult
import com.shin.myproject.ViewModel.subject.SubjectAddViewModel
import com.shin.myproject.navigation.routes.MainRoute
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.Clock
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.component.RadioButtonOption
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectAddScreen(
    navController: NavController,
    subjectAddViewModel: SubjectAddViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var subjectCode by remember { mutableStateOf("") }
    var subjectName by remember { mutableStateOf("") }
    var subjectDescription by remember { mutableStateOf("") }
    var selectedStartTime by remember { mutableStateOf("00:00 AM") }
    var selectedEndTime by remember { mutableStateOf("00:00 AM") }

    var subjectDay by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var saveButtonClicked by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = subjectCode,
                        onValueChange = { subjectCode = it },
                        label = { Text("Subject Code") },
                        singleLine = true
                    )

                    if (saveButtonClicked && subjectCode.isEmpty()) {
                        Text("Subject code empty", color = Color.Red)
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = autocapitalizeFirstLetter(subjectName),
                        onValueChange = { subjectName = it },
                        label = { Text("Subject Name") },
                        singleLine = true
                    )

                    if (saveButtonClicked && subjectName.isEmpty()) {
                        Text("Subject name empty", color = Color.Red)
                    }
                }
            }

            Text("Select Days", modifier = Modifier.padding(8.dp))

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    RadioButtonOption(
                        text = "MONDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }

                item {
                    RadioButtonOption(
                        text = "TUESDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }

                item {
                    RadioButtonOption(
                        text = "WEDNESDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }

                item {
                    RadioButtonOption(
                        text = "THURSDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }

                item {
                    RadioButtonOption(
                        text = "FRIDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }

                item {
                    RadioButtonOption(
                        text = "SATURDAY",
                        selectedOption = subjectDay,
                        onOptionSelected = { subjectDay = it }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = selectedStartTime,
                    onValueChange = { /* Nothing for now */ },
                    label = { Text("Selected Start Time") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    trailingIcon = {
                        Clock(
                            contentDescription = "Start Time",
                            onTimeSelected = { hours, minutes, amPm ->
                                val formattedTime =
                                    String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                selectedStartTime = formattedTime
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
                )

                OutlinedTextField(
                    value = selectedEndTime,
                    onValueChange = { /* Nothing for now */ },
                    label = { Text("End Time") },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    trailingIcon = {
                        Clock(
                            contentDescription = "End Time",
                            onTimeSelected = { hours, minutes, amPm ->
                                val formattedTime =
                                    String.format("%02d:%02d %s", hours, minutes, amPm.name)
                                selectedEndTime = formattedTime
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
                )
            }

            OutlinedTextField(
                value = subjectDescription,
                onValueChange = { subjectDescription = it },
                label = { Text("Subject Description") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        // TODO: Add action for Cancel button
                        // Set all variables back to their initial state
                        subjectCode = ""
                        subjectName = ""
                        subjectDescription = ""
                        selectedStartTime = "00:00 AM"
                        selectedEndTime = "00:00 AM"
                        subjectDay = ""
                        errorMessage = null
                        saveButtonClicked = false
                        navController.navigate(MainRoute.Subjects.name)
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
                        saveButtonClicked = true
                        coroutineScope.launch {
                            // Check Subject Code, Name, and Selected Day before saving
                            if (subjectCode.isNotEmpty() && subjectName.isNotEmpty() && subjectDay.isNotEmpty()) {

                                // Call insertSubject function of the ViewModel
                                val subjectAddInputs = SubjectAddInputs(
                                    subjectCode = subjectCode,
                                    subjectName = subjectName,
                                    subjectDay = subjectDay,
                                    startTime = selectedStartTime,
                                    endTime = selectedEndTime,
                                    subjectDescription = subjectDescription
                                )

                                val result = subjectAddViewModel.insertSubject(subjectAddInputs)

                                // Handle the result accordingly
                                when (result) {
                                    is SubjectAddResult.Success -> {
                                        // Clear all inputs after successful saving
                                        subjectCode = ""
                                        subjectName = ""
                                        subjectDescription = ""
                                        selectedStartTime = "00:00 AM"
                                        selectedEndTime = "00:00 AM"
                                        subjectDay = ""
                                        errorMessage = null
                                        saveButtonClicked = false

                                        //Navigate to SubjectList
                                        navController.navigate(MainRoute.Subjects.name)
                                    }
                                    is SubjectAddResult.Failure -> {
                                        // Set the error message
                                        errorMessage = result.errorMessage
                                    }
                                }
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

            // Display the error message below the buttons if it is not null
            errorMessage?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center)
                )
            }
        }
    }
}


@Composable
fun autocapitalizeFirstLetter(text: String): String {
    return text.split(" ").joinToString(" ") { it.lowercase().capitalize() }
}
