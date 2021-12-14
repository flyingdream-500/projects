package com.example.converterapp.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.converterapp.databinding.UnitItemBinding
import com.example.converterapp.model.ConverterValue
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

class UnitViewHolder(private val binding: UnitItemBinding) : RecyclerView.ViewHolder(binding.root) {

    /**
     * Отображение обозначения единицы измерения, его полного названия, и обработка поведения ввода
     */
    fun bind(
        unit: ConverterValue?,
        replaceUnit: KFunction1<Int, Unit>,
        unitRangeChange: KFunction2<CharSequence?, Int, Unit>,
    ) {
        if (unit != null) {
            binding.unitName.text = itemView.resources.getString(unit.converterUnit.label)
            binding.unitDescription.text =
                itemView.resources.getString(unit.converterUnit.descriptionLabel)
            binding.unitValue.setText(unit.value.toString())

            binding.unitValue.apply {
                setOnFocusChangeListener { _, _ ->
                    replaceUnit.invoke(adapterPosition)
                }
                doOnTextChanged { text, _, _, _ ->
                    unitRangeChange.invoke(text, adapterPosition)
                }

            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): UnitViewHolder {
            val unitItemBinding =
                UnitItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UnitViewHolder(unitItemBinding)
        }
    }
}