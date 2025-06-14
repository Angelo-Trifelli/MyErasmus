package com.example.myerasmus.ui.classes

object CommonHelper {

    fun getTargetExamsList(): List<Exam> {
        return listOf(
            Exam(
                name = "Business Economics",
                university = "Universitat de Barcelona",
                faculty = "Faculty of Economics and Business",
                department = "Department of Economics",
                course = "Economics",
                year = "1°",
                semester = "1°",
                credits = 6,
                language = "ENG",
                professorFullName = "Alejandro Morales",
                professorEmail = "alejandro.morales@ub.edu"
            ),
            Exam(
                name = "Macroeconomics I",
                university = "Universitat de Barcelona",
                faculty = "Faculty of Economics and Business",
                department = "Department of Economics",
                course = "Economics",
                year = "2°",
                semester = "1°",
                credits = 6,
                language = "ESP",
                professorFullName = "Javier Ruiz",
                professorEmail = "javier.ruiz@ub.edu"
            ),
            Exam(
                name = "Regional and Local Finance",
                university = "Universitat de Barcelona",
                faculty = "Faculty of Economics and Business",
                department = "Department of Economics",
                course = "Economics",
                year = "3°",
                semester = "2°",
                credits = 6,
                language = "ENG",
                professorFullName = "Carmen Salgado Ortega",
                professorEmail = "carmen.ortega@ub.edu"
            )
        )
    }

    fun findExamByName(examName: String) : Exam {
        val exam = getTargetExamsList().find { it.name == examName }

        if (exam != null) {
            return exam
        } else {
            return getDefaultExam()
        }
    }

    fun getDefaultExam() : Exam {
        return getTargetExamsList()[0]
    }

    fun getReviewersName(examName: String) : List<String> {
        val reviewList =
            when(examName) {
                "Business Economics" -> listOf("Carolina Monterini")
                else -> listOf()
            }

        return reviewList
    }


}