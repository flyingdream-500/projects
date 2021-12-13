package com.example.voicerecorderapp.presentation.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.model.RecordItem
import com.example.voicerecorderapp.databinding.RecordItemBinding
import kotlin.reflect.KFunction2


/**
 * View Holder для аудиозаписи
 * @param recordItemBinding - вью биндинг для лэйаута
 * @param play - метод обновляет данные о текущем проигрываемом треке в листе [com.example.voicerecorderapp.presentation.fragments.AllRecordsFragment.playTrack]
 */
class RecordViewHolder(
    private val recordItemBinding: RecordItemBinding?,
    private val play: (RecordItem, Context) -> Unit
) :
    RecyclerView.ViewHolder(recordItemBinding!!.root) {

    fun bind(item: RecordItem) {
        itemView.setOnClickListener {
            play.invoke(item, it.context)
            item.isPlaying = true
        }
        recordItemBinding?.let {
            it.name.text = item.name
            it.size.text = item.size
            if (item.isPlaying)
                it.listeningAnimation.visibility = View.VISIBLE
            else
                it.listeningAnimation.visibility = View.GONE
        }
    }

    companion object {
        fun create(
            viewGroup: ViewGroup,
            play: (RecordItem, Context) -> Unit
        ): RecordViewHolder {
            val binding = RecordItemBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
            return RecordViewHolder(binding, play)
        }
    }
}