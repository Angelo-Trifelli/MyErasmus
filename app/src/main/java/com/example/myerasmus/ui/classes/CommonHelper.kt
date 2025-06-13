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
                language = "ENG"
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
                language = "ESP"
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
                language = "ENG"
            )
        )
    }


}