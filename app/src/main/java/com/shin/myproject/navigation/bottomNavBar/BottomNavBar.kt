package com.shin.myproject.navigation.bottomNavBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shin.myproject.navigation.routes.MainRoute

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Default.Analytics, Icons.Outlined.Analytics, MainRoute.Dashboard.name),
        BottomNavItem("Subjects", Icons.Filled.Folder, Icons.Outlined.Folder, MainRoute.Subjects.name),
        BottomNavItem("HomeScreen", Icons.Filled.Home, Icons.Outlined.Home, MainRoute.HomeScreen.name),
        BottomNavItem("Notifications", Icons.Filled.Notifications, Icons.Outlined.Notifications, MainRoute.Notifications.name) ,
        BottomNavItem("Profile", Icons.Filled.Person, Icons.Outlined.Person, MainRoute.Profile.name)
    )
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(

    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector = if (index == selectedItem) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}
