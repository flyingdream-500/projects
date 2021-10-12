package com.example.applebasket.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.R
import com.example.applebasket.func.Const
import com.example.applebasket.model.*
import com.example.applebasket.touchhelper.ItemTouchHelperAdapter
import com.example.applebasket.viewholders.AppleViewHolder
import com.example.applebasket.viewholders.BasketViewHolder
import com.example.applebasket.viewholders.SumViewHolder
import java.util.*

class BasketAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    private var rowTypes: ArrayList<RowType> = arrayListOf()

    // Условие показа запрещающего текста при попытке переместить яблоко в заполненную корзину
    private var showToast = true


    fun setBasketList(rowTypes: ArrayList<RowType>) {
        this.rowTypes = rowTypes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BASKET_ROW_TYPE -> BasketViewHolder.create(parent)
            APPLE_ROW_TYPE -> AppleViewHolder.create(parent)
            SUM_ROW_TYPE -> SumViewHolder.create(parent)
            else -> throw IllegalArgumentException("error")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (rowTypes.get(position).rowType) {
            BASKET_ROW_TYPE -> (holder as BasketViewHolder).bind(
                rowTypes.get(position) as Basket,
                ::addApple,
                ::removeBasket
            )
            APPLE_ROW_TYPE -> (holder as AppleViewHolder).bind(
                rowTypes.get(position) as Apple,
                ::eatApple
            )
            SUM_ROW_TYPE -> (holder as SumViewHolder).bind(rowTypes.get(position) as ApplesSum)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return rowTypes.get(position).rowType
    }

    override fun getItemCount(): Int {
        return rowTypes.size
    }

    // Перемещение яблока внутри и между корзинами с последующим обновление данных
    override fun onItemMove(fromPosition: Int, toPosition: Int, viewHolder: AppleViewHolder) {

        val fromItem = rowTypes.get(fromPosition)
        val toItem = rowTypes.get(toPosition)

        if (fromItem is Apple && toItem is Apple) {
            if (toItem.owner.apples.size != 3 || toItem.owner == fromItem.owner) {

                if (fromPosition < toPosition) {
                    for (i in fromPosition until toPosition) {
                        Collections.swap(rowTypes, i, i + 1)
                        i.inc()
                    }
                } else {
                    for (i in fromPosition downTo toPosition + 1) {
                        Collections.swap(rowTypes, i, i - 1)
                        i.dec()
                    }
                }

                notifyItemMoved(fromPosition, toPosition)

                //добавление и удаление яблок в соответсвующих корзинах
                toItem.owner.apples.add(fromItem)
                fromItem.owner.apples.removeAt(0)


                //поиск позиции экземпляра суммы корзины
                val fromSumPosition = getSumPosition(fromPosition)
                val toSumPosition = getSumPosition(toPosition)

                // замена суммы яблок целевой корзины
                rowTypes.set(toSumPosition, ApplesSum(toItem.owner.apples.size))
                notifyItemChanged(toSumPosition)

                // замена суммы яблок начальной корзины
                if (fromItem.owner.apples.size == 0) {
                    notifyItemRemoved(fromSumPosition)
                    rowTypes.removeAt(fromSumPosition)
                } else {
                    rowTypes.set(fromSumPosition, ApplesSum(fromItem.owner.apples.size))
                    notifyItemChanged(fromSumPosition)
                }

                // замена корзины у яблока
                fromItem.owner = toItem.owner

            } else {
                if (showToast) {
                    viewHolder.itemView.context.run {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.forbidden_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    showToast = false
                }
            }
        }
    }

    // Метод для нахождения экземпляра ApplesSum выбранного яблока
    private fun getSumPosition(currentPosition: Int): Int {
        var position = currentPosition
        while (rowTypes.get(position) !is ApplesSum) {
            ++position
        }
        return position
    }

    //Обрабатываем свайп для удаления элемента в зависимости от его типа
    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val item = rowTypes.get(position)
        when (item) {
            is Apple -> {
                eatApple(position, item.owner)
            }
            is Basket -> {
                removeBasket(item, position)
            }
            else -> notifyItemChanged(position)
        }
    }

    /**
     * ItemTouchHelper.ACTION_STATE_IDLE возвращает viewHolder null,
     * поэтому обрабатываем отпускание элемента в методе:
     * @see returnItemChanges
     */
    override fun onItemSelected(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            //showToast = true
            viewHolder?.itemView?.let {
                it.scaleX = 1.1f
                it.scaleY = 1.1f
                it.alpha = 0.9f
            }
        }
    }

    // Возврат кондиций яблока к нормальной форме
    override fun returnItemChanges(viewHolder: RecyclerView.ViewHolder) {
        showToast = true
        viewHolder.itemView.apply {
            scaleX = 1f
            scaleY = 1f
            alpha = 1f
        }
    }

    // Метод добавления яблока
    private fun addApple(position: Int, basket: Basket, context: Context) {

        if (basket.apples.size == Const.MAX_APPLES_IN_BASKET) {
            Toast.makeText(
                context,
                context.resources.getString(R.string.enough_eat), Toast.LENGTH_SHORT
            ).show()
        } else {
            val newAppleCount = basket.apples.size + 1
            val nameOfApple =
                String.format(context.resources.getString(R.string.apple_name), newAppleCount)
            val apple = Apple(nameOfApple, basket)

            basket.apples.add(apple)

            val applePosition = position + basket.apples.size
            val appleSumPosition = applePosition.inc()

            val applesSum = ApplesSum(basket.apples.size)

            if (basket.apples.size <= 1) {
                rowTypes.add(applePosition, apple)
                rowTypes.add(appleSumPosition, applesSum)
                notifyItemRangeInserted(applePosition, 2)
            } else {
                rowTypes.set(applePosition, apple)
                rowTypes.add(appleSumPosition, applesSum)
                notifyItemInserted(applePosition)
                notifyItemMoved(applePosition, appleSumPosition)
            }
        }

    }

    // Метод удаления яблока
    private fun eatApple(position: Int, basket: Basket) {

        val basketPosition = rowTypes.indexOf(basket)
        val appleSumPosition = basketPosition + basket.apples.size

        val appleInListPosition = position - basketPosition - 1
        basket.apples.removeAt(appleInListPosition)

        rowTypes.removeAt(position)
        notifyItemRemoved(position)

        if (basket.apples.size > 0) {
            val appleSum = ApplesSum(basket.apples.size)
            rowTypes.set(appleSumPosition, appleSum)
            notifyItemChanged(appleSumPosition)
        } else {
            rowTypes.removeAt(appleSumPosition)
            notifyItemRemoved(appleSumPosition)
        }
    }

    // Метод удаления корзины
    private fun removeBasket(basket: Basket, position: Int) {
        if (basket.apples.size == 0) {
            rowTypes.removeAt(position)
            notifyItemRemoved(position)
        } else {
            val endItemsCount = basket.apples.size + 1
            val endPosition = position + endItemsCount
            for (i in position..endPosition) {
                rowTypes.removeAt(position)
                notifyItemRemoved(position)
            }
            //notifyItemRangeRemoved(position, endItemsCount)
        }
    }

}