package com.example.myerasmus.utils

import com.example.myerasmus.R

fun profileImageRes(name: String): Int {
    return when (name) {
        "Carolina Monterini" -> R.drawable.carolina_profile
        "Lucía Fernández" -> R.drawable.lucia_profile
        "Luca Agnellini" -> R.drawable.luca_profile
        "Martina Monelli" -> R.drawable.martina_profile
        "Giulia Casaldi" -> R.drawable.giulia_profile
        "Oliver Bennett" -> R.drawable.oliver_profile
        "Lukas Schneider" -> R.drawable.lukas_profile
        "Alejandro Morales" -> R.drawable.business_economics_professor
        "Javier Ruiz" -> R.drawable.macroeconomics_professor
        "Carmen Salgado Ortega" -> R.drawable.regional_and_local_finance_professor
        else -> R.drawable.user_profile
    }
}