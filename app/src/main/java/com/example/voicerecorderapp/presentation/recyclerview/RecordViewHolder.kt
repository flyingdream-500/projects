package com.example.voicerecorderapp.presentation.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.RecordItem
import com.example.voicerecorderapp.databinding.RecordItemBinding
import kotlin.reflect.KFunction2

class RecordViewHolder(
    private val recordItemBinding: RecordItemBinding,
    private val playing: KFunction2<RecordItem, Context, Unit>
) :
    RecyclerView.ViewHolder(recordItemBinding.root) {

    fun bind(item: RecordItem) {
        recordItemBinding.name.text = item.name
        itemView.setOnClickListener {
            playing.invoke(item, itemView.context)
            item.isPlaying = true
            //recordItemBinding.playingAnimation.visibility = View.VISIBLE
        }
        if (item.isPlaying) recordItemBinding.playingAnimation.visibility = View.VISIBLE else recordItemBinding.playingAnimation.visibility = View.GONE
    }

    companion object {
        fun create(viewGroup: ViewGroup, playing: KFunction2<RecordItem, Context, Unit>): RecordViewHolder {
            val binding = RecordItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
            return RecordViewHolder(binding, playing)
        }
    }
}