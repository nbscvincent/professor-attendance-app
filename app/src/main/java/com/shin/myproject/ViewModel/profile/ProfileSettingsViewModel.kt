package com.shin.myproject.ViewModel.profile

import androidx.lifecycle.ViewModel
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.user.repository.user.UserRepository

class SettingsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
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

