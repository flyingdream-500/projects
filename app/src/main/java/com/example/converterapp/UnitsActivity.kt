package com.example.converterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.converterapp.adapters.UnitsAdapter
import com.example.converterapp.databinding.ActivityUnitsBinding
import com.example.converterapp.func.Extensions.getQuantityFromIntent
import com.example.converterapp.func.Extensions.toConverterValues
import com.example.converterapp.model.Quantity


class UnitsActivity : AppCompatActivity() {
    private var binding: ActivityUnitsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnitsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val quantity: Quantity = getQuantityFromIntent()
        val values = quantity.toConverterValues()
        binding?.rvUnits?.adapter = UnitsAdapter(values, ::itemRangeChanged)
    }

    /**
     * @see R.raw.kostyl
     * Активити крашится при вызове notifyItemRangeChanged внутри адаптера по причине:
     * "java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling"
     * Метод выполняет проверку recyclerview и позволяет обновляет список
     */
    private fun itemRangeChanged(size: Int) {
        binding?.let {
            if (!it.rvUnits.isComputingLayout) {
                it.rvUnits.adapter?.notifyItemRangeChanged(1, size)
            }
        }
    }
}