package com.example.budgettracking

import android.content.Context
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.budgettracking.databinding.ActivityMainBinding
import com.example.budgettracking.ui.home.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false) // Replace "isLoggedIn" with your actual key
    }


    fun toggleBottomNavigationViewVisibility(shouldShow: Boolean) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.nav_view) // Replace with your actual view ID

        if (shouldShow) {
            // Show the BottomNavigationView
            bottomNavigationView.visibility = View.VISIBLE
        } else {
            // Hide the BottomNavigationView
            bottomNavigationView.visibility = View.GONE
        }
    }


}
