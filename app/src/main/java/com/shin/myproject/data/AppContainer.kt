package com.shin.myproject.data

import android.content.Context
import com.shin.myproject.user.repository.student.OfflineStudentRepository
import com.shin.myproject.user.repository.student.StudentRepository
import com.shin.myproject.user.repository.subject.OfflineSubjectRepository
import com.shin.myproject.user.repository.subject.SubjectRepository
import com.shin.myproject.user.repository.user.OfflineUserRepository
import com.shin.myproject.user.repository.user.UserRepository

interface AppContainer {
    val userRepository: UserRepository
    val subjectRepository: SubjectRepository
    val studentRepository: StudentRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [userRepository]
     */
    override val userRepository: UserRepository by lazy {
        OfflineUserRepository(AttendanceAppDatabase.getDatabase(context).userDao())
    }
    /**
     * Implementation for [subjectRepository]
     */
    override val subjectRepository: SubjectRepository by lazy {
        OfflineSubjectRepository(AttendanceAppDatabase.getDatabase(context).subjectDao())
    }
    /**
     * Implementation for [studentRepository]
     */
    override val studentRepository: StudentRepository by lazy {
        OfflineStudentRepository(AttendanceAppDatabase.getDatabase(context).studentDao())
    }
}
