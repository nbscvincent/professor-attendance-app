package com.shin.myproject.screens.authenticationScreens.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.user.LoginViewModel
import com.shin.myproject.ViewModel.user.RecoveryResult
import com.shin.myproject.navigation.routes.AuthRoute
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordRecoveryScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var recoveryMessage by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text(
                            text = "Enter your email",
                            color = Color.Red
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Red,
                        unfocusedIndicatorColor = Color.Gray
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            // Navigate back when the back button is clicked
                            navController.navigate(AuthRoute.LoginScreen.name)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 5.dp)
                    ) {
                        Text(text = "Back", color = Color.White, fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                // Handle password recovery
                                val recoveryResult = loginViewModel.recoverPassword(email)

                                when (recoveryResult) {
                                    is RecoveryResult.Success -> {
                                        // Password recovery successful, show the recovered password
                                        recoveryMessage = "Your password: ${recoveryResult.password}"
                                    }
                                    is RecoveryResult.Failure -> {
                                        // Password recovery failed, show the error message
                                        recoveryMessage = recoveryResult.errorMessage
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 5.dp)
                    ) {
                        Text(text = "Recover Password", color = Color.White, fontSize = 14.sp)
                    }
                }
            }

            // Display recovery message
            item {
                if (recoveryMessage.isNotEmpty()) {
                    Text(
                        text = recoveryMessage,
                        color = Color.Red,
                    )
                }
            }
        }
    }
}