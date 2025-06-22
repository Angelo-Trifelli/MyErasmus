package com.example.myerasmus.utils

import com.example.myerasmus.R

fun examDescriptionRes(examName: String): Int {
    return when (examName) {
        "Mathematics" -> R.string.math_description
        "Business Economics" -> R.string.business_economics_description
        "Statistics" -> R.string.statistics_description
        "Accounting" -> R.string.accounting_description
        "Corporate Finance" -> R.string.corporate_finance_description
        "Marketing" -> R.string.marketing_description
        "Organizational Behavior" -> R.string.organizational_behavior_description
        "Public Law" -> R.string.public_law_description
        "European Institutions" -> R.string.european_institutions_description
        "Microeconomics" -> R.string.microeconomics_description

        "Macroeconomics I" -> R.string.macroeconomics_description
        "Regional and Local Finance" -> R.string.regiona_and_local_finance_description
        "International Marketing" -> R.string.international_marketing_description
        "Development Economics" -> R.string.development_economics_description
        "Behavioral Finance" -> R.string.behavioral_finance_description
        "Data Analysis" -> R.string.data_analysis_description
        "Human Resource Management" -> R.string.hrm_description
        "International Relations" -> R.string.international_relations_description
        "Strategic Management" -> R.string.strategic_management_description
        "Entrepreneurship" -> R.string.entrepreneurship_description

        else -> R.string.business_economics_description
    }
}
