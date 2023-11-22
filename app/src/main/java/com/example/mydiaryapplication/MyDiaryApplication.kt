package com.example.mydiaryapplication

import android.app.Application
import com.google.firebase.FirebaseApp

class MyDiaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Firebase 초기화
        FirebaseApp.initializeApp(this)
    }
}
