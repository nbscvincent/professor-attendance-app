package com.attendanceapp2.users.studentapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.attendanceapp2.authentication.LoggedInUserHolder
import com.attendanceapp2.data.repositories.usersubjectcossref.UserSubjectCrossRefRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class SubjectViewModel(private val userSubjectCrossRefRepository: UserSubjectCrossRefRepository) : ViewModel() {

    private val _subjectIds = MutableStateFlow<List<Long>>(emptyList())
    val subjectIds = _subjectIds.asStateFlow()

    fun fetchSubjectIdsForLoggedInUser() {
        val loggedInUser = LoggedInUserHolder.getLoggedInUser()
        loggedInUser?.let { user ->
            viewModelScope.launch {
                val userId = user.userId
                val subjectIds = userSubjectCrossRefRepository.getSubjectIdsForUser(userId)
                _subjectIds.value = subjectIds ?: emptyList()
                Log.d("Subject View Model", "Subject IDs: $subjectIds")
            }
        }
    }
}