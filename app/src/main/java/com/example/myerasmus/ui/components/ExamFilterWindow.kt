package com.example.myerasmus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myerasmus.ui.classes.CommonHelper
import com.example.myerasmus.ui.classes.ExamFilterState
import com.example.myerasmus.utils.getAllHostExams

@Composable
fun ExamFilterWindow (
    state: MutableState<ExamFilterState>,
    findExactMatch: Boolean,
    modifier: Modifier = Modifier
) {
    val targetExamList = getAllHostExams()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        SelectField(
            label = { Text("University", style = MaterialTheme.typography.bodySmall) },
            menuOptions = listOf("Universitat de Barcelona"),
            selectedOption = state.value.university,
            onOptionSelection = { state.value = state.value.copy(university = it) },
            modifier = Modifier.fillMaxWidth()
        )

        SelectField(
            label = { Text("Faculty", style = MaterialTheme.typography.bodySmall) },
            menuOptions = listOf("Faculty of Economics and Business"),
            selectedOption = state.value.faculty,
            onOptionSelection = { state.value = state.value.copy(faculty = it) },
            enabled = state.value.isFacultySelectable,
            modifier = Modifier.fillMaxWidth()
        )

        SelectField(
            label = { Text("Department", style = MaterialTheme.typography.bodySmall) },
            menuOptions = listOf("Department of Economics"),
            selectedOption = state.value.department,
            onOptionSelection = { state.value = state.value.copy(department = it) },
            enabled = state.value.isDepartmentSelectable,
            modifier = Modifier.fillMaxWidth()
        )

        SelectField(
            label = { Text("Course", style = MaterialTheme.typography.bodySmall) },
            menuOptions = listOf("Economics", "Business Administration and Management"),
            selectedOption = state.value.course,
            onOptionSelection = { state.value = state.value.copy(course = it) },
            enabled = state.value.isCourseSelectable,
            modifier = Modifier.fillMaxWidth()
        )

        if (!findExactMatch) {
            Spacer(modifier = Modifier.height(16.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                SelectField(
                    label = { Text("Year", style = MaterialTheme.typography.bodySmall) },
                    menuOptions = listOf("1°", "2°", "3°"),
                    selectedOption = state.value.year,
                    onOptionSelection = { state.value = state.value.copy(year = it) },
                    modifier = Modifier.fillMaxWidth(0.35f),
                    enabled = state.value.isExamSelectable
                )

                SelectField(
                    label = { Text("Semester", style = MaterialTheme.typography.bodySmall) },
                    menuOptions = listOf("1°", "2°"),
                    selectedOption = state.value.semester,
                    onOptionSelection = { state.value = state.value.copy(semester = it) },
                    modifier = Modifier.fillMaxWidth(0.65f),
                    enabled = state.value.isExamSelectable
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.value.credits,
                    onValueChange = {
                        state.value = state.value.copy(credits = it)
                    },
                    label = { Text(text = "Credits", style = MaterialTheme.typography.bodySmall) },
                    modifier = Modifier.fillMaxWidth(0.35f),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    enabled = state.value.isExamSelectable
                )

                SelectField(
                    label = { Text("Language", style = MaterialTheme.typography.bodySmall) },
                    menuOptions = listOf("ESP", "ENG"),
                    selectedOption = state.value.language,
                    onOptionSelection = { state.value = state.value.copy(language = it) },
                    modifier = Modifier.fillMaxWidth(0.65f),
                    enabled = state.value.isExamSelectable
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}