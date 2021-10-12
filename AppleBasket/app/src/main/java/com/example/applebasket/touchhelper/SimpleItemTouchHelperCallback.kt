package com.example.applebasket.touchhelper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.adapter.BasketAdapter
import com.example.applebasket.viewholders.AppleViewHolder
import com.example.applebasket.viewholders.BasketViewHolder

class SimpleItemTouchHelperCallback(private val adapter: BasketAdapter) :
    ItemTouchHelper.Callback() {

    // Должен возвращать значение true,
    // чтобы поддерживалось перетаскивание после длительного нажатия на элемент RecyclerView
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    // Чтобы разрешить смахивание после касания где угодно в рамках view-компонента
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    // Разрешаем перетаскивание и смахивание в разных направлениях
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {

        val dragFlags: Int
        val swipeFlags: Int

        return when (viewHolder) {
            is AppleViewHolder -> {
                dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                swipeFlags = ItemTouchHelper.RIGHT
                makeMovementFlags(dragFlags, swipeFlags)
            }
            is BasketViewHolder -> {
                dragFlags = 0
                swipeFlags = ItemTouchHelper.LEFT
                makeMovementFlags(dragFlags, swipeFlags)
            }
            else -> makeMovementFlags(0, 0)
        }

    }


    // Перемещение яблока
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        if (viewHolder is AppleViewHolder && target is AppleViewHolder) {
            adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition, viewHolder)
        }

        return false
    }

    // Удаление по смахиванию яблока или корзины
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder, direction)
    }

    // Изменение параметров отображения яблока при перемещении
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        adapter.onItemSelected(viewHolder, actionState)
    }

    // Возврат начальных параметров отображения яблока после перемещения
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        adapter.returnItemChanges(viewHolder)
    }
}