package com.example.converterapp.func

import com.example.converterapp.R
import com.example.converterapp.model.ConverterUnit
import com.example.converterapp.model.Quantity


fun quantities(): ArrayList<Quantity> {
    return arrayListOf<Quantity>(
        createLengthQuantity(),
        createAreaQuantity(),
        createVolumeQuantity()
    )
}

fun createLengthQuantity(): Quantity {
    return Quantity(
        R.string.quantity_length,
        R.drawable.ic_length,
        listOf(
            ConverterUnit(R.string.unit_meter, R.string.unit_meter_description, 1.0),
            ConverterUnit(R.string.unit_millimeter, R.string.unit_millimeter_description,0.001),
            ConverterUnit(R.string.unit_centimeter, R.string.unit_centimeter_description,0.01),
            ConverterUnit(R.string.unit_kilometer, R.string.unit_kilometer_description,1000.0)
        )
    )
}

fun createAreaQuantity(): Quantity {
    return Quantity(
        R.string.quantity_area,
        R.drawable.ic_area,
        listOf(
            ConverterUnit(R.string.unit_sq_meter, R.string.unit_sq_meter_description, 1.0),
            ConverterUnit(R.string.unit_sq_millimeter, R.string.unit_sq_millimeter_description,1E-6),
            ConverterUnit(R.string.unit_sq_centimeter, R.string.unit_sq_centimeter_description,1E-4),
            ConverterUnit(R.string.unit_sq_kilometer, R.string.unit_sq_kilometer_description,1E6)
        )
    )
}

fun createVolumeQuantity(): Quantity {
    return Quantity(
        R.string.quantity_volume,
        R.drawable.ic_measure,
        listOf(
            ConverterUnit(R.string.unit_qb_meter, R.string.unit_qb_meter_description, 1.0),
            ConverterUnit(R.string.unit_qb_millimeter, R.string.unit_qb_millimeter_description, 1E-9),
            ConverterUnit(R.string.unit_qb_centimeter, R.string.unit_qb_centimeter_description,1E-6),
            ConverterUnit(R.string.unit_qb_kilometer, R.string.unit_qb_kilometer_description,1E9)
        )
    )
}