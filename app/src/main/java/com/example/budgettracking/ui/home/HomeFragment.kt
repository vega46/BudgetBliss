package com.example.budgettracking.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.budgettracking.MainActivity
import com.example.budgettracking.R
import com.example.budgettracking.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Assuming you have a logout button in your layout with ID "logout"
        val logoutButton: Button = binding.logout

        logoutButton.text = ""
        logoutButton.setOnClickListener {
            // Perform logout functionality here
            clearUserSession()
            checkUserDataAfterLogout()

            // After logout, navigate to login screen or perform necessary actions
            // For example, if using navigation component:
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)

            // Finish the current activity to prevent going back to the logged-in state
            requireActivity().finish()        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearUserSession() {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false) // Replace "isLoggedIn" with your actual key
        editor.clear()
        editor.apply()

        // Add logging to verify data clearance
        Log.d("UserSession", "User session cleared")
    }

    // To check if user data still exists after logout
    private fun checkUserDataAfterLogout() {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userData = sharedPreferences.getString("user_data_key", null)

        // Log or check userData to see if it's null or empty
        Log.d("UserData", "User data after logout: $userData")
    }


}

