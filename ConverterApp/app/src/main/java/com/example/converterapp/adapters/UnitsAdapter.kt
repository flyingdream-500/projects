package com.example.converterapp.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.func.Extensions.replaceValue
import com.example.converterapp.func.converter
import com.example.converterapp.model.ConverterValue
import com.example.converterapp.viewholders.UnitViewHolder
import kotlin.collections.ArrayList
import kotlin.reflect.KFunction1

class UnitsAdapter(private var units: ArrayList<ConverterValue>, private val itemRangeChanged: KFunction1<Int, Unit>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UnitViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UnitViewHolder).bind(units.get(position), ::replaceUnit, ::changeUnitValue)
    }

    override fun getItemCount(): Int {
        return units.size
    }

    /**
     * Метод для перемещения юнита(при фокусе) в верхний стек
     */
    private fun replaceUnit(i: Int) {
        units.replaceValue(i)
        notifyItemMoved(i, 0)
    }

    /**
     * Метод для конвертации значения других юнитов в сравнении с базовым
     */
    private fun changeUnitValue(text: CharSequence?, adapterPosition: Int) {
        var number = text.toString().toDoubleOrNull()
        if (number == null) {
            number = 0.0
        }
        units[adapterPosition].value = number
        val newValues = converter(units).toCollection(ArrayList())

        units = newValues
        itemRangeChanged(units.size - 1)
    }

}