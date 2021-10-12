package com.example.applebasket

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.applebasket.adapter.BasketAdapter
import com.example.applebasket.databinding.ActivityMainBinding
import com.example.applebasket.func.Extensions.addAndUpdate
import com.example.applebasket.func.Extensions.clearAndUpdate
import com.example.applebasket.model.Basket
import com.example.applebasket.model.RowType
import com.example.applebasket.touchhelper.SimpleItemTouchHelperCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var basketCounter = 1

    private var rowsLiveData = MutableLiveData<ArrayList<RowType>>()
    private val adapter = BasketAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ItemTouchHelper
        val callback = SimpleItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rvBaskets)

        binding.rvBaskets.adapter = adapter

        if(savedInstanceState == null) {
            rowsLiveData.value = arrayListOf()
        } else {
            rowsLiveData.value = savedInstanceState.get(VALUES_KEY) as ArrayList<RowType>
            basketCounter = savedInstanceState.getInt(COUNTER_KEY, 1)
        }

        rowsLiveData.observe(this) {
            adapter.setBasketList(it)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(VALUES_KEY, rowsLiveData.value)
        outState.putInt(COUNTER_KEY, basketCounter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_basket -> {
                val name = String.format(resources.getString(R.string.basket_name), basketCounter++)
                rowsLiveData.value?.let {
                    rowsLiveData.addAndUpdate(Basket(name = name))
                    adapter.notifyItemInserted(it.size - 1)
                }
            }
            R.id.delete_basket -> {
                rowsLiveData.value?.let {
                    adapter.notifyItemRangeRemoved(0, it.size)
                    rowsLiveData.clearAndUpdate()
                    basketCounter = 1
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val VALUES_KEY = "VALUES"
        const val COUNTER_KEY = "BASKET_COUNTER"
    }
}
