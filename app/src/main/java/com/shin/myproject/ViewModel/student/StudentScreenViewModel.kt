package com.shin.myproject.ViewModel.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shin.myproject.data.mainscreenModel.studentModel.Student
import com.shin.myproject.user.repository.student.StudentRepository
import kotlinx.coroutines.launch

class StudentListViewModel (
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _studentsForSelectedSubject = MutableLiveData<List<Student>>()
    val studentsForSelectedSubject: LiveData<List<Student>> get() = _studentsForSelectedSubject

    fun loadStudentsForSelectedSubject(subjectId: Long) {
        viewModelScope.launch {
            studentRepository.getStudentsForSubject(subjectId).collect { students ->
                _studentsForSelectedSubject.value = students
            }
        }
    }
}