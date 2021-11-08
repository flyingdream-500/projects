package com.example.voicerecorderapp.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.RecordItem
import com.example.voicerecorderapp.databinding.RecordItemBinding

class RecordViewHolder(private val recordItemBinding: RecordItemBinding) :
    RecyclerView.ViewHolder(recordItemBinding.root) {

    fun bind(item: RecordItem) {
        recordItemBinding.name.text = item.name
    }

    companion object {
        fun create(viewGroup: ViewGroup): RecordViewHolder {
            val binding = RecordItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
            return RecordViewHolder(binding)
        }
    }
}