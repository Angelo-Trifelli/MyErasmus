package com.example.myerasmus.utils

import com.example.myerasmus.R

fun examDescriptionRes(examName: String) : Int {
    return when (examName) {
        "Business Economics" -> R.string.business_economics_description
        "Macroeconomics I" -> R.string.macroeconomics_description
        "Regional and Local Finance" -> R.string.regiona_and_local_finance_description
        else -> R.string.business_economics_description
    }
}