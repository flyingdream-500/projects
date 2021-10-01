package com.example.applebasket.recycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.applebasket.R
import com.example.applebasket.model.Apple
import com.example.applebasket.model.Basket
import com.example.applebasket.recycler.adapters.AppleAdapter
import com.example.applebasket.recycler.adapters.AppleSumAdapter
import com.example.applebasket.utils.Extensions.gone
import com.example.applebasket.utils.Extensions.visible
import kotlinx.android.synthetic.main.basket_layout.view.*

class BasketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val appleAdapter = AppleAdapter()
    private val appleSumAdapter = AppleSumAdapter()


    fun bind(basket: Basket?) {
        if (basket != null) {
            //Отображение списков при наличии объектов
            if (basket.appleList.size > 0) {
                itemView.rv_apples.visible()
                itemView.rv_apples_sum.visible()
            } else {
                itemView.rv_apples.gone()
                itemView.rv_apples_sum.gone()
            }

            //Имя корзины
            itemView.basket_name.text = basket.name

            //Инициализация адаптера списков
            itemView.rv_apples.adapter = appleAdapter
            itemView.rv_apples_sum.adapter = appleSumAdapter

            //Инициализация методов добавления и удаления яблок
            val removeApple: (Int) -> Unit = { i ->
                basket.appleList.removeAt(i)
                appleAdapter.setBasket(basket)
                appleSumAdapter.setBasket(basket)
            }
            val addApple: () -> Unit = {
                val name = String.format(itemView.resources.getString(R.string.apple_name), basket.appleList.size + 1)
                val apple = Apple(name)
                basket.appleList.add(apple)
                appleAdapter.setBasket(basket)
                appleSumAdapter.setBasket(basket)
            }

            //Добавление яблока в корзину
            itemView.put_apple.setOnClickListener {

                itemView.rv_apples.visible()
                itemView.rv_apples_sum.visible()

                appleAdapter.setDeleteFunc(removeApple)

                if (basket.appleList.size == 3) {
                    Toast.makeText(itemView.context,
                        itemView.resources.getString(R.string.enough_eat), Toast.LENGTH_SHORT).show()
                } else {
                    addApple()
                }

            }
        }
    }



    companion object {
        fun create(parent: ViewGroup): BasketViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.basket_layout, parent, false)
            return BasketViewHolder(view)
        }
    }
}