package com.professorsattendanceapp.data.repositories.subject

import com.professorsattendanceapp.data.interfaces.SubjectDao
import com.professorsattendanceapp.data.model.Subject

class OfflineSubjectRepository(private val subjectDao: SubjectDao) : SubjectRepository {

    override suspend fun insertSubject(subject : Subject) = subjectDao.insert(subject)

    override suspend fun updateSubject(subject : Subject) = subjectDao.update(subject)

    override suspend fun deleteSubject(subject : Subject) = subjectDao.delete(subject)

}