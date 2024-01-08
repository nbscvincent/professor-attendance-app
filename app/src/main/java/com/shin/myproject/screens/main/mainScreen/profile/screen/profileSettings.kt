package com.shin.myproject.screens.main.mainScreen.profile.screen

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel
import com.shin.myproject.ViewModel.profile.SettingsViewModel
import com.shin.myproject.navigation.routes.Routes
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

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
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
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onConfirm = {
                // User confirmed, perform logout
                settingsViewModel.logout()
                navController.navigate(Routes.LOGOUT.name)
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
                    // Navigate to the appropriate screen after deactivation
                    navController.navigate(Routes.LOGOUT.name)
                }
                // Dismiss the dialog
                showDeactivateDialog = false
            },
            onCancel = {
                // User canceled, dismiss the dialog
                showDeactivateDialog = false
            }
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
            .height(50.dp)
            .padding(horizontal = 25.dp),
        colors = ButtonDefaults.buttonColors(Color.Red),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text)
            Icon(imageVector = icon, contentDescription = null)
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
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
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel")
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
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel
            ) {
                Text(text = "Cancel")
            }
        }
    )
}