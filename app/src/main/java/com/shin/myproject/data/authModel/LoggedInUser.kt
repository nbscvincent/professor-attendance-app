package com.shin.myproject.data.authModel

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class LoggedInUser(
    val userId: Long,
    val firstname: String,
    val lastname: String,
    val email: String,
    var password: String,
    val phone: String
)

object LoggedInUserHolder {
    private val _loggedInUser = MutableStateFlow<LoggedInUser?>(null)
    val loggedInUser = _loggedInUser.asStateFlow()

    fun setLoggedInUser(user: LoggedInUser) {
        _loggedInUser.value = user
        Log.d("LoggedInUserHolder", "User set as logged in: $user")
    }

    fun clearLoggedInUser() {
        _loggedInUser.value = null
        Log.d("LoggedInUserHolder", "Logged-in user cleared.")
    }

    fun getLoggedInUser(): LoggedInUser? {
        return _loggedInUser.value
    }

    fun isLoggedIn(): Boolean {
        return _loggedInUser.value != null
    }
}