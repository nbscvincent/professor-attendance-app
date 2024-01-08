package com.shin.myproject.screens.main.mainScreen.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(navController: NavController) {
    var currentTime by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(1000)
            currentTime = LocalDateTime.now()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DateTime(currentTime = currentTime)
    }
}


@Composable
fun DateTime(currentTime: LocalDateTime) {
    val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
    val pstTime = currentTime.atZone(ZoneId.of("Asia/Manila")).format(formatter)

    val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    val pstDate = currentTime.atZone(ZoneId.of("Asia/Manila")).format(dateFormatter)

    Column(
        modifier = Modifier
            .size(300.dp, 150.dp)
            .background(color = Color.Gray)
            .padding(16.dp)
    ) {
        Text(
            text = pstTime,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(8.dp),
        )
        Text(
            text = pstDate,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(8.dp),
        )
    }
}