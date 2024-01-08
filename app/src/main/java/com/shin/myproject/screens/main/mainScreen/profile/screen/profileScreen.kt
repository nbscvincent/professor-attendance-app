package com.shin.myproject.screens.main.mainScreen.profile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.shin.myproject.R
import com.shin.myproject.data.authModel.LoggedInUserHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController:NavController
) {
    val loggedInUser = LoggedInUserHolder.getLoggedInUser()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            loggedInUser?.let {
                Image(
                    painter = painterResource(id = R.drawable.nbslogo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )

                Text(
                    text = "Welcome, ${it.firstname} ${it.lastname}!",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "Email: ${it.email}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "Phone: ${it.phone}",
                    style = TextStyle(fontSize = 16.sp),
                    modifier = Modifier.padding(8.dp)
                )
            } ?: run {
                Text(
                    text = "User not logged in",
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.Red
                )
            }
        }
    }
}