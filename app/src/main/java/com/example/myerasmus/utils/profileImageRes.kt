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
        else -> R.drawable.user_profile
    }
}