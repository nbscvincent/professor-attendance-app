package com.nbscollege.professorsattendanceapp.ContentScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .border(1.dp, Color.Red)
                    .background(Color.Gray),
                title = { Text("Teacher's Attendance App") },
                navigationIcon = { SideBar() },
                actions = {
                    Profile()
                }
            )
        },

        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .border(1.dp, Color.Red)
                    .background(Color.Red),
            ) {
                // Add your bottom app bar content here
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            // Content of the screen
        }
    }
}



@Composable
fun SideBar(){
    IconButton(
        onClick = { /* Add button click logic */ }
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu Button",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun Profile(){
    IconButton(
        onClick = { /* Add button click logic */ }
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Button",
            modifier = Modifier.size(30.dp)
        )
    }
}

