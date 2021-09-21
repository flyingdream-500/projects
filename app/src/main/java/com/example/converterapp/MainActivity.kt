package com.example.converterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.converterapp.adapters.QuantitiesAdapter
import com.example.converterapp.databinding.ActivityMainBinding
import com.example.converterapp.func.quantities


class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        /**
         * @see quantities
         * Передаем в адаптер список сгенерированных величин
         */
        binding?.rvQuantities?.adapter = QuantitiesAdapter(quantities())
    }

}