package com.example.applebasket.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.R
import com.example.applebasket.databinding.AppleSumLayoutBinding
import com.example.applebasket.model.ApplesSum

class SumViewHolder(private val binding: AppleSumLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(applesSum: ApplesSum) {
        binding.applesSum.text =
            String.format(itemView.resources.getString(R.string.apple_sum), applesSum.sum)
    }

    companion object {
        fun create(parent: ViewGroup): SumViewHolder {
            val binding =
                AppleSumLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return SumViewHolder(binding)
        }
    }
}