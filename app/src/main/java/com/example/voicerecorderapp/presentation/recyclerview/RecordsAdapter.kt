package com.example.voicerecorderapp.presentation.recyclerview

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voicerecorderapp.data.RecordItem
import kotlin.reflect.KFunction2

class RecordsAdapter(private val playing: KFunction2<RecordItem, Context, Unit>) : RecyclerView.Adapter<RecordViewHolder>() {
    private var listOfRecords: List<RecordItem> = emptyList()

    fun setRecordItems(newItems: List<RecordItem>) {
        this.listOfRecords = newItems
        Log.d("TAGG", "rv size: ${listOfRecords.size}")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder.create(parent, playing)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(listOfRecords.get(position))
    }

    override fun getItemCount(): Int {
        return listOfRecords.size
    }
}