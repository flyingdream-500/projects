package com.example.neworkrequestsproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.example.neworkrequestsproject.R
import com.example.neworkrequestsproject.databinding.ActivityMainBinding
import okhttp3.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun run(): String {
        val request = Request.Builder()
            .url("https://reqres.in/api/users?page=2")
            .build()

        var body: String = ""
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("TAGG", "${Thread.currentThread()}")
                if (response.isSuccessful) {
                    Log.d("TAGG", response.body?.string() ?: "")
                    body =  response.body!!.string()
                   //binding.textView.setText(response.body!!.string())
                }

            }
        })

        return body
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}