package com.shin.myproject.user.repository.subject


import com.shin.myproject.data.mainscreenModel.subjectModel.Subject

interface SubjectRepository {

    /**
     * Insert a student into the data source.
     */
    suspend fun insertSubject(subject: Subject)

    /**
     * Delete a student from the data source.
     */
    suspend fun deleteSubject(subject: Subject)

    /**
     * Update a student in the data source.
     */
    suspend fun updateSubject(subject: Subject)

    suspend fun getSubjectByCode(code: String): Subject?

    suspend fun getSubjectsByUserIdAndDay(userId: Long, day: String): List<Subject>

    suspend fun getAllSubjectsByUserId(userId: Long): List<Subject>

    suspend fun getSubjectById(subjectId: Long): Subject?

    suspend fun getAllSubjectsByUserId(userId: Long, includeArchived: Boolean): List<Subject>
}