package com.example.networkrequestsproject.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.networkrequestsproject.R
import com.example.networkrequestsproject.databinding.UserItemBinding
import com.example.networkrequestsproject.domain.model.User

class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User?) {
        user?.let {
            binding.apply {
                userName.text = String.format(root.resources.getString(R.string.user_name), user.firstName, user.lastName)
                userEmail.text = user.email
                Glide.with(root)
                    .load(user.avatar)
                    .error(R.drawable.default_profile_image)
                    .into(userLogo)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder {
            val binding =
                UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(binding)
        }
    }
}