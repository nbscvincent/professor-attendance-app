package com.shin.myproject.ViewModel.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shin.myproject.R
import com.shin.myproject.ViewModel.ScreenViewModel
import com.shin.myproject.navigation.routes.MainRoute
import kotlinx.coroutines.delay


@Composable
fun SubjectRegisterSplashScreen(
    navController: NavHostController,
    screenViewModel: ScreenViewModel,
) {
    val state = screenViewModel.subjectRegistered.collectAsState()
    screenViewModel.registerSubject()

    if (state.value) {
        navController.navigate(MainRoute.Subjects.name)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White.copy(alpha = 0.5f)) // Set the background color with 50% opacity
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS LOGO"
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Text(
                text = "Saving Subject ...",
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Box(Modifier.height(25.dp))
            LinearProgressIndicator(
                modifier = Modifier.width(150.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
            // Run the resetSubjectRegistered function after a delay
            LaunchedEffect(Unit) {
                delay(0) // Adjust the delay as needed
                screenViewModel.resetSubjectRegistered()
            }
        }
    }
}