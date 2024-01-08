package com.shin.myproject.ViewModel.subject

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.data.mainscreenModel.subjectModel.SelectedSubjectIdHolder
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfo
import com.shin.myproject.data.mainscreenModel.subjectModel.SubjectInfoHolder
import com.shin.myproject.user.repository.subject.SubjectRepository
import kotlinx.coroutines.launch

class SubjectListViewModel(
    private val subjectRepository: SubjectRepository
) : ViewModel() {
    // userId of the loggedInUser
    private val loggedInUserId: Long = LoggedInUserHolder.getLoggedInUser()?.userId ?: -1L

    // Create a LiveData to hold the list of subjects
    private val _subjectList = MutableLiveData<List<Subject>>()
    val subjectList: LiveData<List<Subject>> get() = _subjectList

    // Function to retrieve all subjects of the loggedInUser based on their userId
    fun loadSubjects() {
        if (loggedInUserId != -1L) {
            viewModelScope.launch {
                // Use the SubjectRepository to get non-archived subjects
                val subjects = subjectRepository.getAllSubjectsByUserId(loggedInUserId, includeArchived = false)
                _subjectList.postValue(subjects)
            }
        }
    }

    // Function to handle subject click and update the selectedSubjectId
    fun onSubjectClicked(subjectId: Long) {
        SelectedSubjectIdHolder.setSelectedSubjectId(subjectId)
        Log.d("SubjectListViewModel", "Subject clicked: $subjectId")
        loadSubjectInfo()
    }

    fun onDelete(subjectId: Long) {
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                subjectRepository.deleteSubject(subject)
                loadSubjects()
            } ?: run {
                Log.d("SubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }

    fun onArchive(subjectId: Long) {
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                val archivedSubject = subject.copy(archived = true)
                subjectRepository.updateSubject(archivedSubject)
                loadSubjects()
            } ?: run {
                Log.d("SubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }

    // Function to retrieve subject code and name
    private fun loadSubjectInfo() {
        val subjectId = SelectedSubjectIdHolder.selectedSubjectId.value ?: return

        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                val subjectInfo = SubjectInfo(
                    subjectId = subjectId,
                    subjectCode = subject.subjectCode,
                    subjectName = subject.subjectName,
                    subjectDay = subject.subjectDay,
                    startTime = subject.startTime,
                    endTime = subject.endTime,
                    subjectDescription = subject.subjectDescription
                )

                SubjectInfoHolder.setSubjectInfo(subjectInfo)

                // Add debug logs
                // Update log to display all subject information
                Log.d("SubjectListViewModel", "Loaded subject info: $subjectInfo")
            } ?: run {
                // Add debug logs if subject is null
                Log.d("SubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }
}