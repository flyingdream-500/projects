package com.example.converterapp.func

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.func.Const.QUANTITY_KEY
import com.example.converterapp.model.ConverterValue
import com.example.converterapp.model.Quantity


object Const {
    const val QUANTITY_KEY = "units"
    const val SCALE = 15
}

object Extensions {

    fun Activity.getQuantityFromIntent(): Quantity {
        return intent.getParcelableExtra<Quantity>(QUANTITY_KEY) as Quantity
    }

    fun ArrayList<ConverterValue>.replaceValue(
        i: Int
    ) {
        val unit = this.get(i)
        this.remove(unit)
        this.add(0, unit)
    }

    fun Quantity.toConverterValues(): ArrayList<ConverterValue> {
        return this.converterUnits
            .map { ConverterValue(it, it.fromBaseToRate) }
            .toCollection(ArrayList())
    }

}