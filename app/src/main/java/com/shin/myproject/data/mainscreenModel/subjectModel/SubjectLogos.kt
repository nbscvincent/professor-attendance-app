package com.shin.myproject.data.mainscreenModel.subjectModel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DesktopWindows
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.ui.graphics.vector.ImageVector

object SubjectLogos {
    private val logoMap = mapOf(
        "CS Elective I (Track-based)" to Icons.Default.Code,
        "Operating Systems" to Icons.Default.DesktopWindows,
        "Application Development and Emerging Technology" to Icons.Default.Code,
        "Structures of Programming Languages" to Icons.Default.Code,
        "Contextualized Communication in Filipino" to Icons.Default.Message,
        "Automata Theory and Formal Languages" to Icons.Default.Build,
        "Thesis Writing 1" to Icons.Default.Book,
        "Fundamentals of Human Computer Interaction" to Icons.Default.TouchApp,
        "Mobile Device Application Principles" to Icons.Default.PhoneAndroid
        // Add more mappings for other subjects as needed
    )

    fun getLogoForSubject(subjectName: String): ImageVector {
        return logoMap[subjectName] ?: Icons.Default.Book // Default logo if not found
    }
}