package com.example.contentproviderapp.presentation.recyclerview.dictionary

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.contentproviderapp.domain.repository.model.DictionaryItemModel
import com.example.contentproviderapp.presentation.recyclerview.dictionary.diffutil.DictionaryItemDiffUtils


/**
 * Adapter for dictionary items
 */
class DictionaryAdapter(
    private val deleteDictionary: (Long) -> Unit
) :
    ListAdapter<DictionaryItemModel, DictionaryViewHolder>(DictionaryItemDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder.create(parent, deleteDictionary)
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<DictionaryItemModel>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}
