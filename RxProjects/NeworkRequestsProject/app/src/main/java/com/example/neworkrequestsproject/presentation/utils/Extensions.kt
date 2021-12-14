package com.example.neworkrequestsproject.presentation.utils

import android.view.View
import com.example.neworkrequestsproject.R
import com.example.neworkrequestsproject.databinding.ActivityMainBinding
import com.example.neworkrequestsproject.domain.model.User
import com.example.neworkrequestsproject.presentation.recycler.UserAdapter

object Extensions {

    fun ActivityMainBinding.observeError(message: String) {
            rvUsers.visibility = View.GONE
            noDataImage.visibility = View.VISIBLE
            noDataMessage.visibility = View.VISIBLE
            noDataMessage.text = message
    }

    fun ActivityMainBinding.observePost(message: String) {
        rvUsers.visibility = View.GONE
        noDataImage.visibility = View.GONE
        noDataMessage.visibility = View.VISIBLE
        noDataMessage.text = String.format(root.resources.getString(R.string.created), message)
    }

    fun ActivityMainBinding.observeUsers(users: List<User>) {
        if (users.isEmpty()) {
            noDataImage.visibility = View.GONE
            rvUsers.visibility = View.GONE
            noDataMessage.visibility = View.VISIBLE
            noDataMessage.text = root.resources.getString(R.string.no_data)
        } else {
            noDataMessage.visibility = View.GONE
            noDataImage.visibility = View.GONE
            rvUsers.visibility = View.VISIBLE
            rvUsers.adapter = UserAdapter(users)
        }
    }

}