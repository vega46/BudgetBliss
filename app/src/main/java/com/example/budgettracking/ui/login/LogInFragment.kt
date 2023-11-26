package com.example.budgettracking.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.budgettracking.MyApp
import com.example.budgettracking.R

class LogInFragment : Fragment() {
    private lateinit var emailLoginEditText: EditText
    private lateinit var passwordLoginEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize views
        emailLoginEditText = view.findViewById(R.id.emailloginEditText)
        passwordLoginEditText = view.findViewById(R.id.passwordloginEditText)
        loginButton = view.findViewById(R.id.loginButton)

        // Set click listener for login button
        loginButton.setOnClickListener {
            login()
        }

        return view
    }

    private fun login() {
        val email = emailLoginEditText.text.toString()
        val password = passwordLoginEditText.text.toString()

        val application = requireActivity().application as MyApp
        val db = application.db

        // Access UserDao to check if the user with provided credentials exists
        val user = db.userDao().getUserByEmail(email)

        if (user != null && user.password == password) {
            // Valid credentials, navigate to Home fragment
            // Use Navigation Component to navigate to the Home fragment
            findNavController().navigate(R.id.navigation_home)
        } else {
            // Invalid credentials, show an error message
            Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }

}
