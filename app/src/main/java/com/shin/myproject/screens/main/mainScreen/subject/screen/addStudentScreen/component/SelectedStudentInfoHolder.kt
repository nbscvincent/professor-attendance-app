package com.shin.myproject.screens.main.mainScreen.subject.screen.addStudentScreen.component

data class SelectedStudent(
    val studentId: Long,
    val subjectId: Long,
    val studentCode: Int,
    val firstname: String,
    val lastname: String,
    val course: String,
    val year: String,
    val isWorkingStudent: Boolean
)