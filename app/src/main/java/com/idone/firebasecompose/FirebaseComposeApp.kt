package com.idone.firebasecompose

import android.app.Application
import android.content.Context

class FirebaseComposeApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}