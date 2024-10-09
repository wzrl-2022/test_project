package com.example.testproject.ui.common.phonenumbertextfield.utils

import android.content.Context
import com.example.testproject.ui.common.phonenumbertextfield.data.CountryData
import com.example.testproject.ui.common.phonenumbertextfield.data.utils.getCountryName

fun List<CountryData>.searchCountry(key: String, context: Context): MutableList<CountryData> {
    val tempList = mutableListOf<CountryData>()
    this.forEach {
        if (context.resources.getString(getCountryName(it.countryCode)).lowercase().contains(key.lowercase())) {
            tempList.add(it)
        }else if (it.countryPhoneCode.contains(key.lowercase())) {
            tempList.add(it)
        }
    }
    return tempList
}