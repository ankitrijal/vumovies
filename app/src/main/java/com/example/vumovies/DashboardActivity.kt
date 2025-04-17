package com.example.vumovies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vumovies.adaptor.MyAdaptor
import com.example.vumovies.data.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class DashboardActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val TAG = "DashboardActivity"

    // Inject ApiService using Koin
    private val apiService: ApiService by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadDashboardData()
    }

    private fun loadDashboardData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val keypass = intent.getStringExtra("KEYPASS") ?: ""
                Log.d(TAG, "Keypass: $keypass")

                val response = apiService.getDashboard(keypass)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val data = response.body()!!
                        recyclerView.adapter = MyAdaptor(data.entities)
                    } else {
                        Log.e(TAG, "Error code: ${response.code()} ${response.message()}")
                        Toast.makeText(this@DashboardActivity, "Failed: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@DashboardActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
