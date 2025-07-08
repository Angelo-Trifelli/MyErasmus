package com.example.myerasmus.utils

import com.example.myerasmus.R

fun reviewTextRes(studentName: String, examName: String): Int {
    return when (Pair(studentName, examName)) {


        Pair("Carolina Monterini", "Business Economics") -> R.string.business_economics_monterini_review


        Pair("Sophie Dubois", "Macroeconomics I") -> R.string.macroeconomics_sophie_review
        Pair("Marek Nowak", "Macroeconomics I") -> R.string.macroeconomics_marek_review
        Pair("Luigi Conti", "Macroeconomics I") -> R.string.macroeconomics_luigi_review


        Pair("Elena Petrova", "Regional and Local Finance") -> R.string.finance_elena_review
        Pair("Jonas Berg", "Regional and Local Finance") -> R.string.finance_jonas_review
        Pair("Inés Muñoz", "Regional and Local Finance") -> R.string.finance_ines_review


        Pair("Claire Dupont", "International Marketing") -> R.string.marketing_claire_review
        Pair("Alexander Ivanov", "International Marketing") -> R.string.marketing_alexander_review
        Pair("Theresa Schmidt", "International Marketing") -> R.string.marketing_theresa_review


        Pair("Daniel Johansson", "Development Economics") -> R.string.development_daniel_review
        Pair("Yasmin Ben Saïd", "Development Economics") -> R.string.development_yasmin_review


        Pair("Kim Lee", "Behavioral Finance") -> R.string.behavioral_kim_review
        Pair("Eduardo Silva", "Behavioral Finance") -> R.string.behavioral_eduardo_review
        Pair("Sofia Rossi", "Behavioral Finance") -> R.string.behavioral_sofia_review


        Pair("Sven Müller", "Data Analysis") -> R.string.data_sven_review
        Pair("Amélie Laurent", "Data Analysis") -> R.string.data_amelie_review


        Pair("Nina Bălan", "Human Resource Management") -> R.string.hrm_nina_review
        Pair("Omar Haddad", "Human Resource Management") -> R.string.hrm_omar_review


        Pair("Julia Nowicka", "International Relations") -> R.string.ir_julia_review
        Pair("Mohammed Al-Farsi", "International Relations") -> R.string.ir_mohammed_review


        Pair("Felipe García", "Strategic Management") -> R.string.strategy_felipe_review
        Pair("Karin Schneider", "Strategic Management") -> R.string.strategy_karin_review


        Pair("Linda Svensson", "Entrepreneurship") -> R.string.entrepreneurship_linda_review
        Pair("Ricardo Costa", "Entrepreneurship") -> R.string.entrepreneurship_ricardo_review


        else -> R.string.default_review
    }
}
