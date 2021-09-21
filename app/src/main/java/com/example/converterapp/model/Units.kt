package com.example.converterapp.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class ConverterValue(val converterUnit: ConverterUnit, var value: Double)

@Parcelize
data class ConverterUnit(
    @StringRes
    val label: Int,
    @StringRes
    val descriptionLabel: Int,
    val toBaseRate: Double
) : Parcelable {
    @IgnoredOnParcel
    var fromBaseToRate: Double = 1.0 / toBaseRate
}

@Parcelize
data class Quantity(
    @StringRes
    val label: Int,
    @DrawableRes
    val iconLabel: Int,
    val converterUnits: List<ConverterUnit>
) : Parcelable




