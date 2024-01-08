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

class ArchivedSubjectListViewModel (
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    // userId of the loggedInUser
    private val loggedInUserId: Long = LoggedInUserHolder.getLoggedInUser()?.userId ?: -1L

    // Create a LiveData to hold the list of subjects
    private val _archivedSubjectList = MutableLiveData<List<Subject>>()
    val archivedSubjectList: LiveData<List<Subject>> get() = _archivedSubjectList

    // Function to retrieve all subjects of the loggedInUser based on their userId
    fun loadArchivedSubjects() {
        if (loggedInUserId != -1L) {
            viewModelScope.launch {
                // Use the SubjectRepository to get non-archived subjects
                val subjects = subjectRepository.getAllSubjectsByUserId(loggedInUserId, includeArchived = true)
                _archivedSubjectList.postValue(subjects)
            }
        }
    }

    // Function to handle subject click and update the selectedSubjectId
    fun onArchivedSubjectClicked(subjectId: Long) {
        SelectedSubjectIdHolder.setSelectedSubjectId(subjectId)
        Log.d("ArchivedSubjectListViewModel", "Subject clicked: $subjectId")
        loadArchivedSubjectInfo()
    }

    fun onDelete(subjectId: Long) {
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                subjectRepository.deleteSubject(subject)
                loadArchivedSubjects()
            } ?: run {
                Log.d("ArchivedSubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }

    fun onUnarchive(subjectId: Long) {
        viewModelScope.launch {
            subjectRepository.getSubjectById(subjectId)?.let { subject ->
                val archivedSubject = subject.copy(archived = false)
                subjectRepository.updateSubject(archivedSubject)
                loadArchivedSubjects()
            } ?: run {
                Log.d("ArchivedSubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }

    // Function to retrieve subject code and name
    private fun loadArchivedSubjectInfo() {
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
                Log.d("ArchivedSubjectListViewModel", "Loaded subject info: $subjectInfo")
            } ?: run {
                // Add debug logs if subject is null
                Log.d("ArchivedSubjectListViewModel", "Subject is null for ID: $subjectId")
            }
        }
    }
}