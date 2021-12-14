package com.example.applebasket.touchhelper

import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.viewholders.AppleViewHolder

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int, viewHolder: AppleViewHolder)
    fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, direction: Int)
    fun onItemSelected(viewHolder: RecyclerView.ViewHolder?, actionState: Int)
    fun returnItemChanges(viewHolder: RecyclerView.ViewHolder)
}