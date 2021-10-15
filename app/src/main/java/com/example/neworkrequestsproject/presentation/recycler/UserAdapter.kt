package com.example.neworkrequestsproject.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neworkrequestsproject.domain.model.User

class UserAdapter(private val users: List<User>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users.get(position))
    }

    override fun getItemCount(): Int {
        return users.size
    }
}