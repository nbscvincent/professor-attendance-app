package com.shin.myproject.ViewModel.subject

import androidx.lifecycle.ViewModel
import com.shin.myproject.data.authModel.LoggedInUserHolder
import com.shin.myproject.data.mainscreenModel.subjectModel.Subject
import com.shin.myproject.user.repository.subject.SubjectRepository

//Dataclass that will hold the data from input screen
data class SubjectAddInputs(
    val subjectCode: String,
    val subjectName: String,
    val subjectDay: String,
    val startTime: String,
    val endTime: String,
    val subjectDescription: String
)

/**
 * ViewModel to validate and insert subjects in the User's Account database.
 */
class SubjectAddViewModel(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val loggedInUserId: Long = LoggedInUserHolder.getLoggedInUser()?.userId ?: -1L

    private suspend fun isSubjectCodeUnique(subjectCode: String): Boolean {
        return subjectRepository.getSubjectByCode(subjectCode) == null
    }

    suspend fun insertSubject(subjectAddInputs: SubjectAddInputs): SubjectAddResult {
        val (subjectCode, subjectName, subjectDay, startTime, endTime, subjectDescription) = subjectAddInputs

        if (!isSubjectCodeUnique(subjectCode)) {
            return SubjectAddResult.Failure("Subject code already exists")
        }

        // No need to check for schedule overlap

        val subject = Subject(
            userId = loggedInUserId,
            subjectCode = subjectCode,
            subjectName = subjectName,
            subjectDay = subjectDay,
            startTime = startTime,
            endTime = endTime,
            subjectDescription = subjectDescription,
            archived = false
        )

        subjectRepository.insertSubject(subject)
        return SubjectAddResult.Success("Subject added successfully")
    }
}

sealed class SubjectAddResult {
    data class Success(val message: String) : SubjectAddResult()
    data class Failure(val errorMessage: String) : SubjectAddResult()
}