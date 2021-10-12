package com.example.applebasket.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.databinding.AppleLayoutBinding
import com.example.applebasket.model.Apple
import com.example.applebasket.model.Basket

class AppleViewHolder(private val binding: AppleLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(apple: Apple, eatApple: (Int, Basket) -> Unit) {
        binding.appleName.text = apple.name
        binding.eatApple.setOnClickListener {
            eatApple.invoke(adapterPosition, apple.owner)
        }
    }

    companion object {
        fun create(parent: ViewGroup): AppleViewHolder {
            val binding =
                AppleLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AppleViewHolder(binding)
        }
    }
}