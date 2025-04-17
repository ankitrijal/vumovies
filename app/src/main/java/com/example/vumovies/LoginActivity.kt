package com.example.vumovies

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vumovies.data.ApiService
import com.example.vumovies.api.LoginRequest
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //  Inject ApiService using Koin
    private val apiService: ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<TextInputEditText>(R.id.etEmail)
        val etPassword = findViewById<TextInputEditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val username = etUsername.text?.toString()?.trim() ?: ""
            val password = etPassword.text?.toString()?.trim() ?: ""

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(username, password)
            }
        }
    }

    private fun loginUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Attempting login with username: $username")
                val response = apiService.login(LoginRequest(username, password))

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val keypass = response.body()!!.keypass
                        Log.d(TAG, "Login successful. Received keypass: $keypass")

                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        intent.putExtra("KEYPASS", keypass)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e(TAG, "Login failed: ${response.code()} ${response.message()}")
                        Toast.makeText(this@LoginActivity, "Login failed: Please check your credentials", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Login Exception: ", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
