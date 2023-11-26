package com.example.budgettracking

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.budgettracking.databinding.ActivityMainBinding
import com.example.budgettracking.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (!isLoggedIn()) {
            // User is not logged in, navigate to CreateAccountFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CreateAccountFragment())
                .commit()
        } else {
            // User is logged in, proceed with your regular flow (e.g., navigate to main fragment)
            // Example: navigate to your main fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }
    }

    private fun isLoggedIn(): Boolean {
        val application = application as MyApp
        val db = application.db

        // Access the UserDao to check if a user exists in the database
        val user = db.userDao().getAnyUser() // Replace with an appropriate query

        // Return true if a user exists in the database (logged in), false otherwise
        return user != null
    }



}
