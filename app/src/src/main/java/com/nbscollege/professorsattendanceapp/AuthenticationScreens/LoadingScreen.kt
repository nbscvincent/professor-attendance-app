package com.nbscollege.professorsattendanceapp.AuthenticationScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nbscollege.professorsattendanceapp.R
import kotlinx.coroutines.delay


@Composable
fun LoadingScreen() {
    LaunchedEffect(Unit) {
        delay(5000) // Wait for 5 seconds
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.nbslogo),
                contentDescription = "Android Logo",
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.height(25.dp))
            CircularProgressIndicator(modifier = Modifier
                .size(40.dp),color = Color.Red)
        }
    }
}
