package com.professorsattendanceapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.professorsattendanceapp.data.interfaces.AttendanceDao
import com.professorsattendanceapp.data.interfaces.FacultyDao
import com.professorsattendanceapp.data.interfaces.StudentDao
import com.professorsattendanceapp.data.model.Attendance
import com.professorsattendanceapp.data.model.Faculty
import com.professorsattendanceapp.data.model.Student
import com.professorsattendanceapp.data.model.Subject
import com.professorsattendanceapp.data.interfaces.SubjectDao

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Faculty::class, Subject::class, Student::class, Attendance::class], version = 3, exportSchema = false)
abstract class AttendanceAppDatabase : RoomDatabase() {
    abstract fun facultyDao(): FacultyDao
    abstract fun subjectDao(): SubjectDao
    abstract fun studentDao(): StudentDao
    abstract fun attendanceDao(): AttendanceDao


    companion object {
        @Volatile
        private var Instance: AttendanceAppDatabase? = null

        fun getDatabase(context: Context): AttendanceAppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AttendanceAppDatabase::class.java, "nbs_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}