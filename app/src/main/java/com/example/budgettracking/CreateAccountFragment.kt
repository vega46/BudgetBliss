package com.example.budgettracking

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountFragment() : Fragment() {

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
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        (requireActivity() as MainActivity).toggleBottomNavigationViewVisibility(false)
        val actionBar = (activity as? AppCompatActivity)?.supportActionBar
        actionBar?.hide()
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

        else {
            CoroutineScope(Dispatchers.IO).launch {
                // Perform database operations in a background thread
                val application = requireActivity().application as MyApp
                val db = application.db
                val existingUser = db.userDao().getUserByEmail(email)
                if (existingUser != null) {

                    requireActivity().runOnUiThread {
                        Toast.makeText(context, "Email already exists. Please log in.", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                // ... (other parts of your code)
                val user = User(
                    name = name,
                    email = email,
                    password = password,
                    categories = "",
                    budget = budget ?: 0.0
                )
                    val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                db.userDao().insertUser(user)
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

}
