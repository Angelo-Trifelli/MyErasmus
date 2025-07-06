package com.example.myerasmus.utils

import com.example.myerasmus.R

sealed class Exam(
    open val code: String,
    open val name: String,
    open val descriptionRes: Int
)

data class HomeUniversityExam(
    override val code: String,
    override val name: String,
    override val descriptionRes: Int,
    val cfu: Int,
    val courseCode: String
) : Exam(code, name, descriptionRes)

data class HostUniversityExam(
    override val code: String,
    override val name: String,
    override val descriptionRes: Int,
    val ects: Int,
    val schedule: List<TimeSlot>,
    val university: String,
    val faculty: String,
    val department: String,
    val course: String,
    val courseCode: String,
    val year: String,
    val semester: String,
    val language: String,
    val professorFullName: String,
    val professorEmail: String
) : Exam(code, name, descriptionRes)

data class LearningAgreement(
    val id: Int,
    var title: String,
    var status: Int = 0,
    var associations: List<Pair<HostUniversityExam, HomeUniversityExam>>
)

data class TimeSlot(
    val dayOfWeek: Int,
    val startHour: Int,
    val duration: Int
)

val allLearningAgreements = mutableListOf(
    LearningAgreement(
        id = 1,
        title = "Learning Agreement 1",
        associations = listOf(
            getAllHostExams()[0] to getAllHomeExams()[0]
        )
    ),
    LearningAgreement(
        id = 2,
        title = "Learning Agreement 2",
        status = 2,
        associations = listOf(
            getAllHostExams().first { it.code == "F06" } to getAllHomeExams().first { it.code == "H03" },
            getAllHostExams().first { it.code == "F07" } to getAllHomeExams().first { it.code == "H06" },
            getAllHostExams().first { it.code == "F08" } to getAllHomeExams().first { it.code == "H10" }
        )
    ),
    LearningAgreement(
        id = 3,
        title = "Learning Agreement 3",
        status = 4,
        associations = listOf(
            getAllHostExams().first { it.code == "F06" } to getAllHomeExams().first { it.code == "H03" },
            getAllHostExams().first { it.code == "F07" } to getAllHomeExams().first { it.code == "H06" }
        )
    )
)

var nextLearningAgreementId = 3
var newUnsavedAgreement: LearningAgreement? = null

fun getAllHomeExams(): List<HomeUniversityExam> = listOf(
    HomeUniversityExam("H01", "Mathematics", R.string.math_description, 6, "1044417"),
    HomeUniversityExam("H02", "Business Economics", R.string.business_economics_description, 6, "1044418"),
    HomeUniversityExam("H03", "Statistics", R.string.statistics_description, 6, "1044419"),
    HomeUniversityExam("H04", "Accounting", R.string.accounting_description, 9, "1044420"),
    HomeUniversityExam("H05", "Corporate Finance", R.string.corporate_finance_description, 6, "1044421"),
    HomeUniversityExam("H06", "Marketing", R.string.marketing_description, 6, "1044422"),
    HomeUniversityExam("H07", "Organizational Behavior", R.string.organizational_behavior_description, 6, "1044423"),
    HomeUniversityExam("H08", "Public Law", R.string.public_law_description, 6, "1044424"),
    HomeUniversityExam("H09", "European Institutions", R.string.european_institutions_description, 3, "1044425"),
    HomeUniversityExam("H10", "Microeconomics", R.string.microeconomics_description, 6, "1044426")
)

fun getAllHostExams(): List<HostUniversityExam> = listOf(
    HostUniversityExam("F01", "Macroeconomics I", R.string.macroeconomics_description, 6, listOf(
        TimeSlot(1, 10, 2), TimeSlot(3, 12, 2), TimeSlot(4, 8, 2)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Economics", "000001", "1°", "1°", "ESP", "Javier Ruiz", "javier.ruiz@ub.edu"),

    HostUniversityExam("F02", "Regional and Local Finance", R.string.regiona_and_local_finance_description, 6, listOf(
        TimeSlot(2, 10, 3), TimeSlot(5, 9, 3)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Economics", "000002", "2°", "2°", "ENG", "Carmen Ortega", "carmen.ortega@ub.edu"),

    HostUniversityExam("F03", "International Marketing", R.string.international_marketing_description, 6, listOf(
        TimeSlot(1, 8, 3), TimeSlot(4, 11, 3)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Economics", "000003", "3°", "1°", "ENG", "Laura Díaz", "laura.diaz@ub.edu"),

    HostUniversityExam("F04", "Development Economics", R.string.development_economics_description, 6, listOf(
        TimeSlot(2, 9, 2), TimeSlot(3, 14, 2), TimeSlot(5, 8, 2)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Economics", "000004", "2°", "1°", "ESP", "Miguel Torres", "miguel.torres@ub.edu"),

    HostUniversityExam("F05", "Behavioral Finance", R.string.behavioral_finance_description, 6, listOf(
        TimeSlot(1, 15, 3), TimeSlot(3, 10, 3)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Economics", "000005", "3°", "2°", "ENG", "Elena Gómez", "elena.gomez@ub.edu"),

    HostUniversityExam("F06", "Data Analysis", R.string.data_analysis_description, 6, listOf(
        TimeSlot(2, 12, 2), TimeSlot(4, 10, 2), TimeSlot(5, 9, 2)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Business Administration and Management", "000006", "1°", "1°", "ENG", "Antonio Ricci", "antonio.ricci@ub.edu"),

    HostUniversityExam("F07", "Human Resource Management", R.string.hrm_description, 6, listOf(
        TimeSlot(3, 11, 3), TimeSlot(4, 14, 3)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Business Administration and Management", "000007", "2°", "2°", "ESP", "Lucía Moreno", "lucia.moreno@ub.edu"),

    HostUniversityExam("F08", "International Relations", R.string.international_relations_description, 6, listOf(
        TimeSlot(1, 13, 2), TimeSlot(2, 10, 2), TimeSlot(5, 11, 2)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Business Administration and Management", "000008", "3°", "2°", "ENG", "Carlos Vidal", "carlos.vidal@ub.edu"),

    HostUniversityExam("F09", "Strategic Management", R.string.strategic_management_description, 6, listOf(
        TimeSlot(2, 14, 3), TimeSlot(4, 9, 3)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Business Administration and Management", "000009", "2°", "1°", "ENG", "Marta Escribà", "marta.escriba@ub.edu"),

    HostUniversityExam("F10", "Entrepreneurship", R.string.entrepreneurship_description, 6, listOf(
        TimeSlot(1, 11, 2), TimeSlot(3, 15, 2), TimeSlot(5, 10, 2)
    ), "Universitat de Barcelona", "Faculty of Economics and Business", "Department of Economics", "Business Administration and Management", "000010", "1°", "2°", "ESP", "Andrés Blanco", "andres.blanco@ub.edu")
)


fun hasOverlappingHours(learningAgreementId: String, selectedHostUniversityExam: HostUniversityExam) : Boolean {
    var learningAgreement : LearningAgreement?

    if (learningAgreementId == "new") {
        learningAgreement = newUnsavedAgreement
    } else {
        val id = learningAgreementId.toInt()
        learningAgreement = allLearningAgreements.find { it.id == id }
    }

    if (learningAgreement == null) {
        throw Exception("Couldn't load learning agreement")
    }

    for (insertedAssociation in learningAgreement.associations) {
        for (existingTimeSlot in insertedAssociation.first.schedule) {
            for (newTimeSlot in selectedHostUniversityExam.schedule) {
                if (existingTimeSlot.dayOfWeek == newTimeSlot.dayOfWeek) {
                    var existingTimeSlotStart = existingTimeSlot.startHour
                    var existingTimeSlotEnd = existingTimeSlot.startHour + existingTimeSlot.duration
                    var newTimeSlotStart = newTimeSlot.startHour
                    var newTimeSlotEnd = newTimeSlot.startHour + newTimeSlot.duration

                    if (existingTimeSlotStart < newTimeSlotEnd && newTimeSlotStart < existingTimeSlotEnd) {
                        return true
                    }
                }
            }
        }
    }

    return false
}
