package com.professorsattendanceapp.samplektor.local.repository

import com.professorsattendanceapp.samplektor.local.model.SampleFaculty
import com.professorsattendanceapp.samplektor.local.repository.dao.SampleKtorInterfaceFaculty

class SampleFacultyOfflineRepository(private val sampleFacultyDao: SampleKtorInterfaceFaculty) : SampleFacultyRepository {

    override suspend fun insertFaculty(sampleFaculty: SampleFaculty) = sampleFacultyDao.insert(sampleFaculty)

    override suspend fun updateFaculty(sampleFaculty: SampleFaculty) = sampleFacultyDao.update(sampleFaculty)

    override suspend fun deleteFaculty(sampleFaculty: SampleFaculty) = sampleFacultyDao.delete(sampleFaculty)

}