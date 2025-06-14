package com.example.myerasmus.utils

import com.example.myerasmus.R

fun reviewTextRes(studentName: String, examName: String): Int {
    return when (Pair(studentName, examName)) {
        Pair("Carolina Monterini", "Business Economics") -> R.string.business_economics_monterini_review
        else -> R.string.app_name
    }
}