package com.professorsattendanceapp

import android.app.Application
import com.professorsattendanceapp.data.AppContainer
import com.professorsattendanceapp.data.AppDataContainer

class NBSAttendanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
 