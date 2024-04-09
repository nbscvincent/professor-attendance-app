package com.professorsattendanceapp.data

import android.content.Context
import com.professorsattendanceapp.data.repositories.attendancce.AttendanceRepository
import com.professorsattendanceapp.data.repositories.attendancce.OfflineAttendanceRepository
import com.professorsattendanceapp.data.repositories.faculty.FacultyRepository
import com.professorsattendanceapp.data.repositories.faculty.OfflineFacultyRepository
import com.professorsattendanceapp.data.repositories.student.OfflineStudentRepository
import com.professorsattendanceapp.data.repositories.student.StudentRepository
import com.professorsattendanceapp.data.repositories.subject.OfflineSubjectRepository
import com.professorsattendanceapp.data.repositories.subject.SubjectRepository
import com.professorsattendanceapp.posts.repository.OnlinePostRepository

interface AppContainer {
    val onlinePostRepository: OnlinePostRepository
    val facultyRepository: FacultyRepository
    val subjectRepository: SubjectRepository
    val studentRepository: StudentRepository
    val attendanceRepository: AttendanceRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineUserRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val onlinePostRepository: OnlinePostRepository by lazy {
        OnlinePostRepository()
    }


    /**
     * Implementation for [userRepository]
     */
    override val facultyRepository: FacultyRepository by lazy {
        OfflineFacultyRepository(AttendanceAppDatabase.getDatabase(context).facultyDao())
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

    /**
     * Implementation for [attendanceRepository]
     */
    override val attendanceRepository: AttendanceRepository by lazy {
        OfflineAttendanceRepository(AttendanceAppDatabase.getDatabase(context).attendanceDao())
    }
}
