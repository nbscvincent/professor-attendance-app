package com.attendanceapp2.screenuniversalcomponents.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.attendanceapp2.approutes.AppRoutes
import com.attendanceapp2.approutes.AuthRoute
import com.attendanceapp2.authentication.SignInScreen
import com.attendanceapp2.authentication.SignUpScreen
import com.attendanceapp2.screenuniversalcomponents.navigation.faculty.FacultyNavigation
import com.attendanceapp2.screenuniversalcomponents.navigation.student.StudentNavigation
import com.attendanceapp2.viewmodel.AppViewModelProvider
import com.shin.myproject.ViewModel.ScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()
    val screenViewModel: ScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(navController = navController, startDestination = AppRoutes.AUTH.name) {
        navigation(startDestination = AuthRoute.SignIn.name, route = AppRoutes.AUTH.name) {
            composable(route = AuthRoute.SignIn.name) {
                SignInScreen(navController)
            }
            composable(route = AuthRoute.SignUp.name) {
                SignUpScreen(navController)
            }
        }
        composable(route = AppRoutes.STUDENT.name ) {
            StudentNavigation()
        }
        composable(route = AppRoutes.FACULTY.name ) {
            FacultyNavigation()
        }
        composable(route = AppRoutes.ADMIN.name ) {
            FacultyNavigation()
        }
    }
}