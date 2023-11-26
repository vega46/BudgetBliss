package com.example.budgettracking

import android.app.Application
import androidx.room.Room

class MyApp : Application() {

    lateinit var db: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        // Initialize the database here
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "budgetapp-database"
        ).build()
    }
}
