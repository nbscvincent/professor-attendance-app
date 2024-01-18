package com.shin.myproject.ViewModel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.data.authModel.User
import com.shin.myproject.user.repository.user.UserRepository

sealed class ChangePasswordResult {
    data class Success(val password: String) : ChangePasswordResult()
    data class Failure(val errorMessage: String) : ChangePasswordResult()
}

class SettingsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val changePasswordResult: ChangePasswordResult? = null

    // Function to handle password change
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmPassword: String) {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()

        if (loggedInUser != null) {
            // Check if old password matches the current password
            if (oldPassword != loggedInUser.password) {
                ChangePasswordResult.Failure("Old password didn't match current password")
                return
            }

            // Check if new password and confirm password match
            if (newPassword != confirmPassword) {
                ChangePasswordResult.Failure("New password and confirm password didn't match")
                return
            }

            // Update the user's password in the repositories
            val updatedUser = User(
                userId = loggedInUser.userId,
                firstname = loggedInUser.firstname,
                lastname = loggedInUser.lastname,
                email = loggedInUser.email,
                password = newPassword,
                phone = loggedInUser.phone
            )

            // Update the user's password in the repository
            userRepository.updateUser(updatedUser)
            ChangePasswordResult.Success("${loggedInUser.email}, ${newPassword}")

            Log.d("SettingsViewModel", "Password successfully changed. New password: $newPassword")
        }
    }

    fun logout() {
        // Clear the logged-in user data
        LoggedInUserHolder.clearLoggedInUser()
    }

    suspend fun deactivateAccount() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()

        if (loggedInUser != null) {
            // Delete the user from the repository
            userRepository.deleteUserById(loggedInUser.userId)

            // Clear the logged-in user data
            LoggedInUserHolder.clearLoggedInUser()
        }
    }
}
