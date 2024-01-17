package com.shin.myproject.ViewModel.student

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shin.myproject.user.repository.student.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalTime

class ResetMarkedStatusWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val studentRepository: StudentRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        // Check if the current time is after a specific time, for example, 23:59:59
        val currentTime = LocalTime.now()
        val endTime = LocalTime.of(23, 59, 59)

        if (currentTime.isAfter(endTime)) {
            // Reset marked status for all students
            studentRepository.resetMarkedStatusForAllStudents()
        }

        Result.success()
    }
}