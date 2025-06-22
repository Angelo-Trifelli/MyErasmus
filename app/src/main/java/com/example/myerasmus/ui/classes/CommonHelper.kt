package com.example.myerasmus.ui.classes

import com.example.myerasmus.R
import com.example.myerasmus.utils.getAllHostExams
import com.example.myerasmus.utils.HostUniversityExam

object CommonHelper {

    // ✅ Trova l'esame tra quelli della host university in base al nome
    fun findExamByName(name: String): HostUniversityExam {
        return getAllHostExams().find { it.name == name }
            ?: throw IllegalArgumentException("Exam not found: $name")
    }

    // ✅ Associa immagine al professore (per profilo circolare in ExamScreen)
    fun profileImageRes(profName: String): Int {
        return when (profName) {
            "Javier Ruiz" -> R.drawable.macroeconomics_professor
            "Carmen Ortega" -> R.drawable.regional_and_local_finance_professor
            "Laura Díaz" -> R.drawable.prof_laura
            "Miguel Torres" -> R.drawable.prof_miguel
            "Elena Gómez" -> R.drawable.prof_elena
            "Antonio Ricci" -> R.drawable.prof_antonio
            "Lucía Moreno" -> R.drawable.prof_lucia
            "Carlos Vidal" -> R.drawable.prof_carlos
            "Marta Escribà" -> R.drawable.prof_marta
            "Andrés Blanco" -> R.drawable.prof_andres
            "Alejandro Morales" -> R.drawable.prof_alejandro
            else -> R.drawable.default_professor
        }
    }
    fun reviewerImageRes(studentName: String): Int {
        return when (studentName) {
            "Sophie Dubois" -> R.drawable.reviewer_sophie
            "Marek Nowak" -> R.drawable.reviewer_marek
            "Luigi Conti" -> R.drawable.reviewer_luigi
            "Elena Petrova" -> R.drawable.reviewer_elena
            "Jonas Berg" -> R.drawable.reviewer_jonas
            "Inés Muñoz" -> R.drawable.reviewer_ines
            "Claire Dupont" -> R.drawable.reviewer_claire
            "Alexander Ivanov" -> R.drawable.reviewer_alexander
            "Theresa Schmidt" -> R.drawable.reviewer_theresa
            "Daniel Johansson" -> R.drawable.reviewer_daniel
            "Yasmin Ben Saïd" -> R.drawable.reviewer_yasmin
            "Kim Lee" -> R.drawable.reviewer_kim
            "Eduardo Silva" -> R.drawable.reviewer_eduardo
            "Sofia Rossi" -> R.drawable.reviewer_sofia
            "Sven Müller" -> R.drawable.reviewer_sven
            "Amélie Laurent" -> R.drawable.reviewer_amelie
            "Nina Bălan" -> R.drawable.reviewer_nina
            "Omar Haddad" -> R.drawable.reviewer_omar
            "Julia Nowicka" -> R.drawable.reviewer_julia
            "Mohammed Al-Farsi" -> R.drawable.reviewer_mohammed
            "Felipe García" -> R.drawable.reviewer_felipe
            "Karin Schneider" -> R.drawable.reviewer_karin
            "Linda Svensson" -> R.drawable.reviewer_linda
            "Ricardo Costa" -> R.drawable.reviewer_ricardo
            else -> R.drawable.default_student
        }
    }


    // ✅ Nuovo: restituisce una lista di recensioni con nome, voto, testo
    fun getReviewsForExam(examName: String): List<Triple<String, Int, Int>> {
        return when (examName) {
            "Macroeconomics I" -> listOf(
                Triple("Sophie Dubois", 5, R.string.macroeconomics_sophie_review),
                Triple("Marek Nowak", 3, R.string.macroeconomics_marek_review),
                Triple("Luigi Conti", 4, R.string.macroeconomics_luigi_review)
            )
            "Regional and Local Finance" -> listOf(
                Triple("Elena Petrova", 4, R.string.finance_elena_review),
                Triple("Jonas Berg", 2, R.string.finance_jonas_review),
                Triple("Inés Muñoz", 5, R.string.finance_ines_review)
            )
            "International Marketing" -> listOf(
                Triple("Claire Dupont", 5, R.string.marketing_claire_review),
                Triple("Alexander Ivanov", 3, R.string.marketing_alexander_review),
                Triple("Theresa Schmidt", 4, R.string.marketing_theresa_review)
            )
            "Development Economics" -> listOf(
                Triple("Daniel Johansson", 5, R.string.development_daniel_review),
                Triple("Yasmin Ben Saïd", 4, R.string.development_yasmin_review)
            )
            "Behavioral Finance" -> listOf(
                Triple("Kim Lee", 5, R.string.behavioral_kim_review),
                Triple("Eduardo Silva", 3, R.string.behavioral_eduardo_review),
                Triple("Sofia Rossi", 5, R.string.behavioral_sofia_review)
            )
            "Data Analysis" -> listOf(
                Triple("Sven Müller", 4, R.string.data_sven_review),
                Triple("Amélie Laurent", 5, R.string.data_amelie_review)
            )
            "Human Resource Management" -> listOf(
                Triple("Nina Bălan", 5, R.string.hrm_nina_review),
                Triple("Omar Haddad", 3, R.string.hrm_omar_review)
            )
            "International Relations" -> listOf(
                Triple("Julia Nowicka", 5, R.string.ir_julia_review),
                Triple("Mohammed Al-Farsi", 3, R.string.ir_mohammed_review)
            )
            "Strategic Management" -> listOf(
                Triple("Felipe García", 4, R.string.strategy_felipe_review),
                Triple("Karin Schneider", 5, R.string.strategy_karin_review)
            )
            "Entrepreneurship" -> listOf(
                Triple("Linda Svensson", 5, R.string.entrepreneurship_linda_review),
                Triple("Ricardo Costa", 5, R.string.entrepreneurship_ricardo_review)
            )
            "Business Economics" -> listOf(
                Triple("Carolina Monterini", 5, R.string.business_economics_monterini_review)
            )
            else -> listOf(
                Triple("Student A", 5, R.string.default_review)
            )
        }
    }
}
