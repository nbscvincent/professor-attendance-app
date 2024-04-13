package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class TopNavItem(
    val title: String?,
    val backIcon: ImageVector?,
    val backRoute: String?,
    val rightIcon: ImageVector?,
    val rightRoute: String?
)
