package com.example.voicerecorderapp.presentation.recyclerview

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.RecordItem

class RecordsAdapter: RecyclerView.Adapter<RecordViewHolder>() {
    private var listOfRecords: List<RecordItem> = emptyList()

    fun setRecordItems(newItems: List<RecordItem>) {
        this.listOfRecords = newItems
        Log.d("TAGG", "rv size: ${listOfRecords.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(listOfRecords.get(position))
    }

    override fun getItemCount(): Int {
        return listOfRecords.size
    }
}