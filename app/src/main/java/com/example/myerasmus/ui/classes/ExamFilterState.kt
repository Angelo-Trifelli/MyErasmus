package com.example.myerasmus.ui.classes

data class ExamFilterState(
    var university: String = "",
    var faculty: String = "",
    var department: String = "",
    var course: String = "",
    var year: String = "",
    var semester: String = "",
    var credits: String = "",
    var language: String = "",
    var exam: String = ""
) {
    val isFacultySelectable: Boolean
        get() = university.isNotBlank()

    val isDepartmentSelectable: Boolean
        get() = isFacultySelectable &&
                faculty.isNotBlank()

    val isCourseSelectable: Boolean
        get() = isDepartmentSelectable &&
                department.isNotBlank()

    val isExamSelectable: Boolean
        get() = isCourseSelectable &&
                course.isNotBlank()
}