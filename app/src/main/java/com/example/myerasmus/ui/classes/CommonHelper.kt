package com.example.myerasmus.ui.classes

import com.example.myerasmus.R
import com.example.myerasmus.utils.getAllHostExams
import com.example.myerasmus.utils.HostUniversityExam

object CommonHelper {

    private const val currentUserName = "Anna Ruzzoli"

    // Trova esame per nome
    fun findExamByName(name: String): HostUniversityExam {
        return getAllHostExams().find { it.name == name }
            ?: throw IllegalArgumentException("Exam not found: $name")
    }

    // Immagine del professore
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

    // Immagine del recensore
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
            "Carolina Monterini" -> R.drawable.default_student
            "Anna Ruzzoli" -> R.drawable.user_profile
            else -> R.drawable.default_student
        }
    }

    // Recensioni statiche (iniziali)
    private val predefinedReviews = mapOf(
        "Macroeconomics I" to listOf(
            Triple("Sophie Dubois", 5, R.string.macroeconomics_sophie_review),
            Triple("Marek Nowak", 3, R.string.macroeconomics_marek_review),
            Triple("Luigi Conti", 4, R.string.macroeconomics_luigi_review)
        ),
        "Regional and Local Finance" to listOf(
            Triple("Elena Petrova", 4, R.string.finance_elena_review),
            Triple("Jonas Berg", 2, R.string.finance_jonas_review),
            Triple("Inés Muñoz", 5, R.string.finance_ines_review)
        ),
        "Business Economics" to listOf(
            Triple("Carolina Monterini", 5, R.string.business_economics_monterini_review)
        )
    )

    // Recensioni dinamiche (aggiunte/modificate da utenti)
    private val examReviews = mutableMapOf<String, MutableList<Triple<String, Int, String>>>()

    // ✅ Aggiungi/modifica recensione di Anna Ruzzoli
    fun addReview(examName: String, rating: Int, reviewText: String) {
        val list = examReviews.getOrPut(examName) { mutableListOf() }
        list.removeAll { it.first == currentUserName }
        list.add(Triple(currentUserName, rating, reviewText))
    }

    // ✅ Elimina recensione di Anna Ruzzoli
    fun deleteReview(examName: String) {
        examReviews[examName]?.removeIf { it.first == currentUserName }
    }

    // ✅ Controlla se esiste una recensione dell’utente attuale
    fun hasUserReview(examName: String): Boolean {
        return examReviews[examName]?.any { it.first == currentUserName } == true
    }

    // ✅ Recupera la recensione dell’utente attuale
    fun getUserReview(examName: String): Triple<String, Int, String>? {
        return examReviews[examName]?.find { it.first == currentUserName }
    }

    // ✅ Solo recensioni dinamiche
    fun getDynamicReviewsForExam(examName: String): List<Triple<String, Int, String>> {
        return examReviews[examName].orEmpty()
    }

    // ✅ Tutte le recensioni (statiche + dinamiche)
    fun getReviewsForExam(examName: String): List<Triple<String, Int, Any>> {
        val static = predefinedReviews[examName].orEmpty().map { Triple(it.first, it.second, it.third as Any) }
        val dynamic = getDynamicReviewsForExam(examName).map { Triple(it.first, it.second, it.third as Any) }
        return static + dynamic
    }
}
