package com.example.budgettracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateAccountFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var reppasswordEditText: EditText
    private lateinit var setbudgetEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var createaccButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_createaccount, container, false)

        // Initialize views
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        reppasswordEditText = view.findViewById(R.id.reppasswordEditText)
        setbudgetEditText = view.findViewById(R.id.setbudgetEditText)
        createaccButton = view.findViewById(R.id.createaccButton)
        nameEditText = view.findViewById(R.id.nameEditText)


        // Set click listener for create account button
        createaccButton.setOnClickListener {
            createUserAccount()
        }

        return view
    }

    private fun createUserAccount() {

        val application = requireActivity().application as MyApp
        val db = application.db

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val repPassword = reppasswordEditText.text.toString()
        val name = nameEditText.text.toString()
        val budget = setbudgetEditText.text.toString().toDoubleOrNull()

        // Check if passwords match
        if (password != repPassword) {
            // Show error message for passwords not matching
            Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isValidEmail(email)) {
            // Show error message for invalid email format
            Toast.makeText(context, "Please input a valid email", Toast.LENGTH_SHORT).show()
            return
        }

        else{
            val user = User(name = name, email = email, password = password, categories = "", budget = budget ?: 0.0)
            db.userDao().insertUser(user)
        }

        // Validate other fields if needed (e.g., email format)

        // Create user object with validated data

        // Save user to database (use your database instance here)
        // For example, assuming you have a viewModel to handle database operations:
        // viewModel.insertUser(user)

        // After saving, navigate to the main screen or perform necessary actions
        // For example, navigate to the main fragment with bottom navigation view
        // (You'll need to implement your navigation logic here)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

}
