package com.shin.myproject.ViewModel.user

import androidx.lifecycle.ViewModel
import com.shin.myproject.data.authModel.User
import com.shin.myproject.user.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    fun isPhoneExist(phone: String): Flow<Boolean> {
        return userRepository.isPhoneExist(phone)
    }

    fun isEmailExist(email: String): Flow<Boolean> {
        return userRepository.isEmailExist(email)
    }

    private fun validatePassword(password: String): RegistrationResult {
        // Password should be at least 8 characters with at least 1 or more numbers
        val passwordRegex = "^(?=.*[0-9]).{8,}$".toRegex()

        return if (!password.matches(passwordRegex)) {
            RegistrationResult.Failure("Password must be 8 characters with at least 1 number.")
        } else {
            RegistrationResult.Success()
        }
    }

    suspend fun validateAndRegisterUser(registrationInput: RegistrationInput): RegistrationResult {
        // Validate the input
        if (registrationInput.firstname.isBlank() ||
            registrationInput.lastname.isBlank() ||
            registrationInput.phone.isBlank() ||
            registrationInput.email.isBlank() ||
            registrationInput.password.isBlank()
        ) {
            return RegistrationResult.Failure("All fields must be filled.")
        }

        val passwordValidationResult = validatePassword(registrationInput.password)
        if (passwordValidationResult is RegistrationResult.Failure) {
            return passwordValidationResult
        }
        // Create RegistrationData with validated input
        val registrationData = RegistrationData(
            firstname = registrationInput.firstname,
            lastname = registrationInput.lastname,
            phone = registrationInput.phone,
            email = registrationInput.email,
            password = registrationInput.password
        )

        // Check if email and phone already exist
        val isEmailExist = isEmailExist(registrationData.email).first()
        val isPhoneExist = isPhoneExist(registrationData.phone).first()

        if (isEmailExist && isPhoneExist) {
            return RegistrationResult.Failure("Email and phone already exist.")
        } else if (isEmailExist) {
            return RegistrationResult.Failure("Email already exists.")
        } else if (isPhoneExist) {
            return RegistrationResult.Failure("Phone already exists.")
        }

        // Email and phone do not exist, proceed with registration
        userRepository.insertUser(User(
            userId = 0, // Auto-generated ID
            firstname = registrationData.firstname,
            lastname = registrationData.lastname,
            email = registrationData.email,
            password = registrationData.password,
            phone = registrationData.phone
        ))

        return RegistrationResult.Success("Registration successful.")
    }
}
data class RegistrationInput(
    var firstname: String = "",
    var lastname: String = "",
    var phone: String = "",
    var email: String = "",
    var password: String = ""
)

data class RegistrationData(
    val firstname: String,
    val lastname: String,
    val phone: String,
    val email: String,
    val password: String
)

sealed class RegistrationResult {
    data class Success(val message: String = "") : RegistrationResult()
    data class Failure(val message: String) : RegistrationResult()
}
