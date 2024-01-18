package com.shin.myproject.screens.main.mainScreen.profile.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel
import com.shin.myproject.ViewModel.profile.ChangePasswordResult
import com.shin.myproject.ViewModel.profile.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettings(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(factory = AppViewModelProvider.Factory),
    screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeactivateDialog by remember { mutableStateOf(false) }
    var showChangePasswordDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        item {
            SettingButtons(
                text = "Deactivate Account",
                icon = Icons.Default.Delete,
                onClick = {
                    showDeactivateDialog = true
                }
            )
        }

        item {
            SettingButtons(
                text = "Logout",
                icon = Icons.Default.ExitToApp,
                onClick = {
                    showLogoutDialog = true
                }
            )
        }

        item {
            SettingButtons(
                text = "Change Password",
                icon = Icons.Default.Lock,
                onClick = {
                    showChangePasswordDialog = true
                }
            )
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onConfirm = {
                // User confirmed, perform logout
                settingsViewModel.logout()
//                navController.navigate(Routes.LOGOUT.name)
                showLogoutDialog = false
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showLogoutDialog = false
            }
        )
    }

    if (showDeactivateDialog) {
        DeactivateAccountDialog(
            settingsViewModel = settingsViewModel,
            onConfirm = {
                // User confirmed, perform deactivate account
                coroutineScope.launch {
                    settingsViewModel.deactivateAccount()
                    settingsViewModel.logout()
                }
                // Dismiss the dialog
//                showDeactivateDialog = false
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showDeactivateDialog = false
            }
        )
    }

    if (showChangePasswordDialog) {
        ChangePasswordDialog(
            onChangePassword = { oldPassword, newPassword ->
                // Apply the change password logic
                coroutineScope.launch {
                    // Call the suspend function within a coroutine scope
                    settingsViewModel.changePassword(oldPassword, newPassword, newPassword)
                    showChangePasswordDialog = false
                    settingsViewModel.logout()
                }
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showChangePasswordDialog = false
            },
            changePasswordResult = settingsViewModel.changePasswordResult
        )
    }
}

@Composable
fun SettingButtons(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = Color.White
            )
            Icon(imageVector = icon, contentDescription = null, tint = Color.White)
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Confirm Logout")
        },
        text = {
            Text(text = "Are you sure you want to logout?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirm", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel", color = Color.White)
            }
        }
    )
}


@Composable
fun DeactivateAccountDialog(
    settingsViewModel: SettingsViewModel,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Confirm Deactivate Account")
        },
        text = {
            Text(text = "Are you sure you want to deactivate your account?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(text = "Confirm", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel", color = Color.White)
            }
        }
    )
}

@Composable
fun ChangePasswordDialog(
    onChangePassword: (oldPassword: String, newPassword: String) -> Unit,
    onCancel: () -> Unit,
    changePasswordResult: ChangePasswordResult?
) {
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember {mutableStateOf("")}
    var showPassword by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(text = "Change Password")
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Old Password
                OutlinedTextField(
                    value = oldPassword,
                    onValueChange = { oldPassword = it },
                    singleLine = true,
                    label = { Text("Old Password") },
                    visualTransformation = PasswordVisualTransformation(), // Corrected
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // New Password
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    singleLine = true,
                    label = { Text("New Password") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.Lock else Icons.Default.LockOpen,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                // Confirm New Password
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    singleLine = true,
                    label = { Text("Confirm New Password") },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.LockOpen else Icons.Default.Lock,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Display result or error message
                changePasswordResult?.let { result ->
                    Text(
                        text = when (result) {
                            is ChangePasswordResult.Success -> "Password successfully changed: ${result.password}"
                            is ChangePasswordResult.Failure -> "Error: ${result.errorMessage}"
                        },
                        color = if (result is ChangePasswordResult.Success) Color.Green else Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onChangePassword(oldPassword, newPassword)
                }
            ) {
                Text(text = "Confirm", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel", color = Color.White)
            }
        }
    )
}