package com.example.neworkrequestsproject.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.neworkrequestsproject.R
import com.example.neworkrequestsproject.data.BaseUserRepository
import com.example.neworkrequestsproject.data.OkHttpUserRepository
import com.example.neworkrequestsproject.data.converter.UserConverter
import com.example.neworkrequestsproject.databinding.ActivityMainBinding
import com.example.neworkrequestsproject.domain.UserInteract
import com.example.neworkrequestsproject.domain.model.Person
import com.example.neworkrequestsproject.presentation.utils.Extensions.observeError
import com.example.neworkrequestsproject.presentation.utils.Extensions.observePost
import com.example.neworkrequestsproject.presentation.utils.Extensions.observeUsers

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
            binding.observeError(errorMessage)
        }

        viewModel.getPostData().observe(this) { postedMessage ->
            binding.observePost(postedMessage)
        }

        viewModel.getUsersData().observe(this) { users ->
            binding.observeUsers(users)
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
                viewModel.postPerson(Person("Ivan", "dev"))
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