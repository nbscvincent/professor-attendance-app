package com.shin.myproject.data.mainscreenModel.subjectModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Data class to hold subject information.
 */
data class SubjectInfo(
    val subjectId : Long,
    val subjectCode: String,
    val subjectName: String,
    val subjectDay: String,
    val startTime: String,
    val endTime: String,
    val subjectDescription: String
)

object SubjectInfoHolder {
    private val _subjectInfo = MutableLiveData<SubjectInfo>()
    val subjectInfo: LiveData<SubjectInfo> get() = _subjectInfo

    fun setSubjectInfo(info: SubjectInfo) {
        _subjectInfo.value = info
    }
}