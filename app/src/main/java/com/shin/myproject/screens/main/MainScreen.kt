
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.shin.myproject.ViewModel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel
import com.shin.myproject.ViewModel.splash.LogoutSplashScreen
import com.shin.myproject.ViewModel.splash.SubjectRegisterSplashScreen
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.navigation.bottomNavBar.BottomNavBar
import com.shin.myproject.navigation.routes.MainRoute
import com.shin.myproject.navigation.routes.ProfileRoute
import com.shin.myproject.navigation.routes.Routes
import com.shin.myproject.navigation.routes.SubjectRoute
import com.shin.myproject.screens.main.mainScreen.analyticDashboard.screen.DashboardScreen
import com.shin.myproject.screens.main.mainScreen.home.screen.HomeScreen
import com.shin.myproject.screens.main.mainScreen.notification.screen.NotificationScreen
import com.shin.myproject.screens.main.mainScreen.profile.screen.ProfileScreen
import com.shin.myproject.screens.main.mainScreen.profile.screen.ProfileSettings
import com.shin.myproject.screens.main.mainScreen.subject.screen.ArchivedSubjectScreen
import com.shin.myproject.screens.main.mainScreen.subject.screen.StudentScreen
import com.shin.myproject.screens.main.mainScreen.subject.screen.SubjectScreen
import com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.StudentAddScreen
import com.shin.myproject.screens.main.mainScreen.subject.screen.addSubjectScreen.SubjectAddScreen

// Data class for top bar information
data class TopBarInfo(val title: String, val actionIcon: ImageVector?, val actionRoute: String?)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController
) {
    val navController: NavHostController = rememberNavController()
    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val studentListViewModel: StudentListViewModel = viewModel(factory = AppViewModelProvider.Factory)

    // Separate boolean variables for topAppBar and bottomAppBar
    var isTopAppBarVisible by remember { mutableStateOf(true) }
    var isBottomAppBarVisible by remember { mutableStateOf(true) }

    // State variable to hold the current top bar information
    var currentTopBarInfo by remember {
        mutableStateOf(TopBarInfo("", null, null))
    }

    Scaffold(
        topBar = {
            // Conditionally render the topAppBar based on isTopAppBarVisible
            if (isTopAppBarVisible) {
                Surface(
                    color = Color.LightGray
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentTopBarInfo.title
                            )
                        },
                        actions = {
                            currentTopBarInfo.actionIcon?.let { icon ->
                                IconButton(
                                    onClick = {
                                        // Navigate to the specified route when the action button is clicked
                                        currentTopBarInfo.actionRoute?.let { route ->
                                            navController.navigate(route)
                                        }
                                    }
                                ) {
                                    Icon(imageVector = icon, contentDescription = null)
                                }
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            // Conditionally render the bottomAppBar based on isBottomAppBarVisible
            if (isBottomAppBarVisible) {
                BottomNavBar(navController = navController)
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            val currentRoute = navBackStackEntry?.destination?.route ?: MainRoute.HomeScreen.name

            // Extract the top bar information based on the current route
            currentTopBarInfo = when (currentRoute) {
                MainRoute.HomeScreen.name -> TopBarInfo("Home", null, null)
                MainRoute.Dashboard.name -> TopBarInfo("Dashboard", null, null)
                MainRoute.Subjects.name -> TopBarInfo("Subjects", Icons.Default.CreateNewFolder, SubjectRoute.AddSubjectScreen.name)
                MainRoute.Notifications.name -> TopBarInfo("Notifications", null, null)
                MainRoute.Profile.name -> TopBarInfo("Profile", Icons.Default.Settings, ProfileRoute.ProfileSettings.name)

                SubjectRoute.AddSubjectScreen.name -> TopBarInfo("Create New Subject", null, null)
                SubjectRoute.StudentsScreen.name -> TopBarInfo("Students", Icons.Default.PersonAdd, SubjectRoute.AddStudentScreen.name)
                SubjectRoute.ArchivedSubjectsScreen.name -> TopBarInfo("ArchivedSubjects", null, null)
                SubjectRoute.AddStudentScreen.name -> TopBarInfo("Add New Student", null, null)

                ProfileRoute.ProfileSettings.name -> TopBarInfo("Account Settings", null, null)

                else -> TopBarInfo("", null, null)
            }

            NavHost(
                navController = navController,
                startDestination = MainRoute.HomeScreen.name
            ) {
                composable(route = MainRoute.HomeScreen.name) {
                    isTopAppBarVisible = true
                    isBottomAppBarVisible = true
                    HomeScreen(navController)
                }
                composable(route = MainRoute.Dashboard.name) {
                    isTopAppBarVisible = true
                    isBottomAppBarVisible = true
                    DashboardScreen(navController)
                }
                composable(route = MainRoute.Subjects.name) {
                    isTopAppBarVisible = true
                    isBottomAppBarVisible = true
                    SubjectScreen(navController)
                }
                navigation(startDestination = MainRoute.Subjects.name, route = Routes.SUBJECTS.name) {
                    composable(route = MainRoute.Subjects.name) {
                        isTopAppBarVisible = true
                        isBottomAppBarVisible = true
                        SubjectScreen(navController)
                    }
                    composable(route = SubjectRoute.ArchivedSubjectsScreen.name) {
                        isBottomAppBarVisible = false
                        ArchivedSubjectScreen(navController)
                    }
                    composable(route = SubjectRoute.StudentsScreen.name) {
                        isBottomAppBarVisible = false
                        StudentScreen()
                    }
                    composable(route = SubjectRoute.AddSubjectScreen.name) {
                        isBottomAppBarVisible = false
                        SubjectAddScreen(navController)
                    }
                    composable(route = SubjectRoute.AddStudentScreen.name) {
                        isBottomAppBarVisible = false
                        StudentAddScreen(navController)
                    }

                    composable(route = SubjectRoute.SubjectRegisterSplash.name) {
                        isTopAppBarVisible = false
                        isBottomAppBarVisible = false
                        SubjectRegisterSplashScreen(navController, screenViewModel)
                    }
                }
                composable(route = MainRoute.Notifications.name) {
                    isTopAppBarVisible = true
                    isBottomAppBarVisible = true
                    NotificationScreen(navController)
                }
                composable(route = MainRoute.Profile.name) {
                    isTopAppBarVisible = true
                    isBottomAppBarVisible = true
                    ProfileScreen(navController)
                }
                navigation(startDestination = MainRoute.Profile.name, route = Routes.PROFILE.name) {
                    composable(route = MainRoute.Profile.name) {
                        isTopAppBarVisible = true
                        isBottomAppBarVisible = true
                        ProfileScreen(navController)
                    }
                    composable(route = ProfileRoute.ProfileSettings.name) {
                        isBottomAppBarVisible = false
                        ProfileSettings(navController)
                    }
                }
                composable(route = Routes.APP.name ) {
                    isTopAppBarVisible = false
                    isBottomAppBarVisible = false
                    NBSApp()
                }
                composable(route = Routes.LOGOUT.name ) {
                    isTopAppBarVisible = false
                    isBottomAppBarVisible = false
                    LogoutSplashScreen(navController, screenViewModel)
                }
            }
        }
    }
}