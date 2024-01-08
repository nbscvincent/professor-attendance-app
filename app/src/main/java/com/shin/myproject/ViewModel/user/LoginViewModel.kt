package com.shin.myproject.ViewModel.user

import androidx.lifecycle.ViewModel
import com.shin.myproject.data.authModel.LoggedInUser
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.user.repository.user.UserRepository


/**
 * Data class to hold login input.
 */
data class LoginInput(
    val email: String,
    val password: String
)

/**
 * ViewModel for the login screen.
 */
class LoginViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    suspend fun validateLoginInput(loginInput: LoginInput): LoginResult {
        // Validate the input
        if (loginInput.email.isBlank() || loginInput.password.isBlank()) {
            return LoginResult.Failure("Email and password must be filled.")
        }

        // Check if the user exists
        val user = userRepository.getUserByEmail(loginInput.email)

        if (user == null || user.password != loginInput.password) {
            return LoginResult.Failure("Invalid email or password.")
        }

        // Login successful
        val loggedInUser = LoggedInUser(
            userId = user.userId,
            firstname = user.firstname,
            lastname = user.lastname,
            email = user.email,
            phone = user.phone
        )

        // Set the loggedInUser in the LoggedInUserHolder
        LoggedInUserHolder.setLoggedInUser(loggedInUser)

        return LoginResult.Success(loggedInUser)
    }
}

sealed class LoginResult {
    data class Success(val message: LoggedInUser) : LoginResult()
    data class Failure(val errorMessage: String) : LoginResult()
}