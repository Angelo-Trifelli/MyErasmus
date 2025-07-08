package com.example.myerasmus.utils

val suggestedExamMappings: Map<String, HostUniversityExam> by lazy {
    val hostExams = getAllHostExams().associateBy { it.name }

    mapOf(

        "Business Economics" to (hostExams["Macroeconomics I"] ?: getAllHostExams()[0]),
        "Mathematics" to (hostExams["Data Analysis"] ?: getAllHostExams()[1]),
        "Statistics" to (hostExams["Development Economics"] ?: getAllHostExams()[2]),
        "Accounting" to (hostExams["Behavioral Finance"] ?: getAllHostExams()[3])
    )
}

fun getSuggestedExam(homeExamName: String): HostUniversityExam {
    return suggestedExamMappings[homeExamName] ?: getAllHostExams().first()
}

