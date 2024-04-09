package com.professorsattendanceapp.data.repositories.subject

import com.professorsattendanceapp.data.model.Subject


interface SubjectRepository {

    suspend fun insertSubject(subject : Subject)

    suspend fun updateSubject(subject : Subject)

    suspend fun deleteSubject(subject : Subject)

}