package com.example.networkrequestsproject.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.networkrequestsproject.R
import com.example.networkrequestsproject.data.BaseUserRepository
import com.example.networkrequestsproject.data.OkHttpUserRepository
import com.example.networkrequestsproject.data.converter.UserConverter
import com.example.networkrequestsproject.databinding.ActivityMainBinding
import com.example.networkrequestsproject.domain.UserInteract
import com.example.networkrequestsproject.domain.model.Person
import com.example.networkrequestsproject.presentation.utils.Extensions.observeError
import com.example.networkrequestsproject.presentation.utils.Extensions.observePost
import com.example.networkrequestsproject.presentation.utils.Extensions.observeUsers

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