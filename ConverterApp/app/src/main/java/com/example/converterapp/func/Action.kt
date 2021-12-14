package com.example.converterapp.func

import com.example.converterapp.func.Const.SCALE
import com.example.converterapp.model.ConverterValue
import java.math.RoundingMode

fun converter(converterValue: List<ConverterValue>): List<ConverterValue> {
    val mutableValues = converterValue.toMutableList()
    val baseValue = mutableValues.removeFirst()
    for (i in mutableValues.indices) {
        mutableValues[i] =
            mutableValues[i].copy(
                value = (baseValue.value * baseValue.converterUnit.toBaseRate * mutableValues[i].converterUnit.fromBaseToRate)
                    .toBigDecimal()
                    .setScale(SCALE, RoundingMode.UP)
                    .toDouble()
            )
    }
    mutableValues.add(0, baseValue)
    return mutableValues
}