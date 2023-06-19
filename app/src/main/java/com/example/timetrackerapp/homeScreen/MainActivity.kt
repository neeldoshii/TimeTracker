package com.example.timetrackerapp.homeScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.timetrackerapp.R
import com.example.timetrackerapp.database.Database

class MainActivity : AppCompatActivity() {
    lateinit var taskDB: Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

    }
}







