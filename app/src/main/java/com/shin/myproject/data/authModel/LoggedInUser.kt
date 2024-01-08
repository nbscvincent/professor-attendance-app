package com.shin.myproject.data.authModel

data class LoggedInUser(
    val userId: Long,
    val firstname: String,
    val lastname: String,
    val email: String,
    val phone: String
)

object LoggedInUserHolder {
    private var loggedInUser: LoggedInUser? = null

    fun setLoggedInUser(user: LoggedInUser) {
        loggedInUser = user
    }

    fun getLoggedInUser(): LoggedInUser? {
        return loggedInUser
    }

    fun clearLoggedInUser() {
        loggedInUser = null
    }

    fun isLoggedIn(): Boolean {
        return loggedInUser != null
    }
}