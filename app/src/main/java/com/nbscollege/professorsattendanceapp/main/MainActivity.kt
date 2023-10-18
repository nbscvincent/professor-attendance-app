package com.nbscollege.professorsattendanceapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nbscollege.professorsattendanceapp.AuthenticationScreens.LoadingScreen
import com.nbscollege.professorsattendanceapp.AuthenticationScreens.LoginScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MyApp()
            }
        }
    }
}


@Composable
fun MyApp(){
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Simulating a loading delay
        delay(1500)
        isLoading = false
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        LoginScreen()
    }
    //HomeScreen()
    //OTPScreen()
    //LoadingScreen()
    //RegisterScreen()
    //LoginScreen()
}