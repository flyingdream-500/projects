package com.example.neworkrequestsproject.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.neworkrequestsproject.R
import com.example.neworkrequestsproject.data.BaseUserRepository
import com.example.neworkrequestsproject.data.OkHttpUserRepository
import com.example.neworkrequestsproject.data.converter.UserConverter
import com.example.neworkrequestsproject.databinding.ActivityMainBinding
import com.example.neworkrequestsproject.domain.UserInteract
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.presentation.recycler.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        provideDependencies()

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        viewModel.getErrorData().observe(this) { errorMessage ->
            binding.run {
                rvUsers.visibility = View.GONE
                noDataImage.visibility = View.VISIBLE
                noDataMessage.visibility = View.VISIBLE
                noDataMessage.text = errorMessage
            }
        }

        viewModel.getPostData().observe(this) { posted ->
            binding.run {
                rvUsers.visibility = View.GONE
                noDataImage.visibility = View.GONE
                noDataMessage.visibility = View.VISIBLE
                noDataMessage.text = String.format(resources.getString(R.string.created), posted)
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
                    rvUsers.adapter = UserAdapter(users)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.get_users -> {
                viewModel.getUsers()
            }
            R.id.post_user -> {
                val person = Person("Ivan", "dev")
                viewModel.postPerson(person)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun provideDependencies() {
        val repository: BaseUserRepository = OkHttpUserRepository(UserConverter())
        val interact = UserInteract(repository)
        viewModelFactory = ViewModelFactory(interact)
    }


}