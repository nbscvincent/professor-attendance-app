package com.shin.myproject.data.authModel

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
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
    private const val PREFS_NAME = "MyPrefs"
    private const val USER_KEY = "user"

    private val _loggedInUser = MutableStateFlow<LoggedInUser?>(null)
    val loggedInUser = _loggedInUser.asStateFlow()

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedUserString = sharedPreferences.getString(USER_KEY, null)
        savedUserString?.let {
            _loggedInUser.value = gson.fromJson(it, LoggedInUser::class.java)
        }
    }

    fun setLoggedInUser(user: LoggedInUser) {
        _loggedInUser.value = user
        saveUserToPrefs(user)
    }

    fun clearLoggedInUser() {
        _loggedInUser.value = null
        clearUserFromPrefs()
    }

    fun getLoggedInUser(): LoggedInUser? {
        return _loggedInUser.value
    }

    fun isLoggedIn(): Boolean {
        return _loggedInUser.value != null
    }

    private fun saveUserToPrefs(user: LoggedInUser) {
        val editor = sharedPreferences.edit()
        val userString = gson.toJson(user)
        editor.putString(USER_KEY, userString)
        editor.apply()
    }

    private fun clearUserFromPrefs() {
        val editor = sharedPreferences.edit()
        editor.remove(USER_KEY)
        editor.apply()
    }
}