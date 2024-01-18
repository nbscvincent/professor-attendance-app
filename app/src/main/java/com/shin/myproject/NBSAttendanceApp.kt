package com.shin.myproject

import android.app.Application
import com.shin.myproject.data.AppContainer
import com.shin.myproject.data.AppDataContainer
import com.shin.myproject.data.authModel.LoggedInUserHolder

class NBSAttendanceApp : Application() {
    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        LoggedInUserHolder.init(this)
        container = AppDataContainer(this)
    }
}