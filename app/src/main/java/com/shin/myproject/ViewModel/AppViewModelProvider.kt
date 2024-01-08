package com.shin.myproject.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shin.myproject.NBSAttendanceApp
import com.shin.myproject.ViewModel.profile.SettingsViewModel
import com.shin.myproject.ViewModel.student.StudentAddViewModel
import com.shin.myproject.ViewModel.student.StudentListViewModel
import com.shin.myproject.ViewModel.subject.ArchivedSubjectListViewModel
import com.shin.myproject.ViewModel.subject.SubjectAddViewModel
import com.shin.myproject.ViewModel.subject.SubjectListViewModel
import com.shin.myproject.ViewModel.user.LoginViewModel
import com.shin.myproject.ViewModel.user.RegisterViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {

        // Initializer for RegistrationViewModel
        initializer {
            RegisterViewModel(
                  nbsAttendanceApplication().container.userRepository
            )
        }

        // Initializer for LoginViewModel
        initializer {
            LoginViewModel(
                nbsAttendanceApplication().container.userRepository,
            )
        }

        // Initializer for SubjectViewModel
        initializer {
            SubjectAddViewModel (
                nbsAttendanceApplication().container.subjectRepository,
            )
        }

        // Initializer for SubjectListViewModel
        initializer {
            SubjectListViewModel (
                nbsAttendanceApplication().container.subjectRepository
            )
        }

        // Initializer for ArchivedSubjectListViewModel
        initializer {
            ArchivedSubjectListViewModel (
                nbsAttendanceApplication().container.subjectRepository
            )
        }

        // Initializer for StudentAddViewModel
        initializer {
            StudentAddViewModel (
                nbsAttendanceApplication().container.studentRepository,
                StudentListViewModel(
                    nbsAttendanceApplication().container.studentRepository
                )
            )
        }

        // Initializer for StudentListViewModel
        initializer {
            StudentListViewModel (
                nbsAttendanceApplication().container.studentRepository
            )
        }

        // Initializer for SettingsViewModel
        initializer {
            SettingsViewModel(
                nbsAttendanceApplication().container.userRepository
            )
        }

        // ScreenViewModel
        initializer {
            ScreenViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [NBSApplication].
 */
fun CreationExtras.nbsAttendanceApplication(): NBSAttendanceApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as NBSAttendanceApp)