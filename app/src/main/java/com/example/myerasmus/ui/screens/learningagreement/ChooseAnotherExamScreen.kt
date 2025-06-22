package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myerasmus.ui.classes.ExamFilterState
import com.example.myerasmus.ui.components.ExamFilterWindow
import com.example.myerasmus.utils.HostUniversityExam
import com.example.myerasmus.utils.getAllHostExams
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.foundation.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAnotherExamScreen(
    onBack: () -> Unit,
    onExamSelected: (HostUniversityExam) -> Unit
) {
    val examFilterState = remember { mutableStateOf(ExamFilterState()) }
    val allExams = getAllHostExams()

    val filter = examFilterState.value
    val filteredExams = allExams.filter { exam ->
        (filter.university.isBlank() || exam.university == filter.university) &&
                (filter.faculty.isBlank() || exam.faculty == filter.faculty) &&
                (filter.department.isBlank() || exam.department == filter.department) &&
                (filter.course.isBlank() || exam.course == filter.course) &&
                (filter.year.isBlank() || exam.year == filter.year) &&
                (filter.semester.isBlank() || exam.semester == filter.semester) &&
                (filter.credits.isBlank() || filter.credits.toIntOrNull() == exam.ects) &&
                (filter.language.isBlank() || exam.language == filter.language)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Choose another exam") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            ExamFilterWindow(state = examFilterState, findExactMatch = false)

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredExams) { exam ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExamSelected(exam) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = exam.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${exam.ects} ECTS", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

