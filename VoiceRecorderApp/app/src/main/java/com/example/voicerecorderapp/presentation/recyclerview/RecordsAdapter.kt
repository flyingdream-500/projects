package com.example.voicerecorderapp.presentation.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.model.RecordItem

/**
 * Адаптер для отображения списка аудиозаписей
 */
class RecordsAdapter(private val play: (RecordItem, Context) -> Unit) :
    RecyclerView.Adapter<RecordViewHolder>() {
    private var listOfRecords: List<RecordItem> = arrayListOf()

    fun setRecordItems(newItems: List<RecordItem>) {
        this.listOfRecords = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent, play)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(listOfRecords.get(position))
    }

    override fun getItemCount(): Int {
        return listOfRecords.size
    }
}