package com.example.applebasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import com.example.applebasket.model.Basket
import com.example.applebasket.recycler.adapters.BasketAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var baskets: ArrayList<Basket> = arrayListOf()
    private var basketLiveData = MutableLiveData<ArrayList<Basket>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = BasketAdapter()
        rv_baskets.adapter = adapter

        //обновление UI при изменении списка
        basketLiveData.observe(this) {
            adapter.setBasketList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_basket ->  {
                val name = String.format(resources.getString(R.string.basket_name), baskets.size + 1)
                baskets.add(Basket(name = name))
                basketLiveData.value = baskets
            }
            R.id.delete_basket ->  {
                baskets.clear()
                basketLiveData.value = baskets
            }
        }
        return super.onOptionsItemSelected(item)
    }
}