package com.example.fileexplorer.presentation.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fileexplorer.data.model.ExternalFileItem

class FilesListAdapter: RecyclerView.Adapter<FileViewHolder>() {
    private var itemExternals: List<ExternalFileItem> = arrayListOf()

    fun updateItems(itemExternals: List<ExternalFileItem>) {
        this.itemExternals = itemExternals
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val item = itemExternals[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return itemExternals.size
    }
}