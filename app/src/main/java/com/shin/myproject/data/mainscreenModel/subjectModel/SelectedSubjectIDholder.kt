package com.shin.myproject.data.mainscreenModel.subjectModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object SelectedSubjectIdHolder {
    private val _selectedSubjectId = MutableLiveData<Long>()
    val selectedSubjectId: LiveData<Long> get() = _selectedSubjectId

    // Function to set the selected subject ID
    fun setSelectedSubjectId(subjectId: Long) {
        _selectedSubjectId.value = subjectId
    }
}