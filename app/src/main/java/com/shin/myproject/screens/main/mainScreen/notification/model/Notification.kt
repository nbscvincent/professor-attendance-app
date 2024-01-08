package com.shin.myproject.screens.main.mainScreen.notification.model


data class Notification(
    val subjectCode: String,
    val subjectName: String,
    val notificationMessage: String,
    val time: String,
    val date: String,
    val notificationTime: String // Add notification time information
)
