package com.example.neworkrequestsproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.neworkrequestsproject.R
import com.example.neworkrequestsproject.databinding.ActivityMainBinding
import com.example.neworkrequestsproject.presentation.recycler.UserAdapter
import okhttp3.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getErrorData().observe(this) { errorMessage ->
            binding.run {
                rvUsers.visibility = View.GONE
                noDataMessage.visibility = View.VISIBLE
                noDataImage.visibility = View.VISIBLE
                noDataMessage.text = errorMessage
            }
        }

        viewModel.getUsersData().observe(this) { users ->
            if (users.isEmpty()) {
                binding.run {
                    noDataImage.visibility = View.GONE
                    rvUsers.visibility = View.GONE
                    noDataMessage.visibility = View.VISIBLE
                    noDataMessage.text = resources.getString(R.string.no_data)
                }
            } else {
                binding.run {
                    noDataMessage.visibility = View.GONE
                    noDataImage.visibility = View.GONE
                    rvUsers.visibility = View.VISIBLE
                    rvUsers.adapter = UserAdapter()
                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.get_users -> {

            }
            R.id.post_user -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun provideDependencies() {

    }
}