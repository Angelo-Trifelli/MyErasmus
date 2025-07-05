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
import androidx.compose.ui.text.style.TextAlign
import com.example.myerasmus.utils.hasOverlappingHours


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseAnotherExamScreen(
    learningAgreementId: String,
    onBack: () -> Unit,
    onExamSelected: (HostUniversityExam) -> Unit
) {
    val examFilterState = remember { mutableStateOf(ExamFilterState()) }
    var selectedExam: HostUniversityExam? by remember { mutableStateOf(null)}
    var showOverlappingHoursWarning = remember { mutableStateOf(false) }

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
                            .clickable {
                                if (hasOverlappingHours(learningAgreementId, exam)) {
                                    showOverlappingHoursWarning.value = true
                                    selectedExam = exam
                                } else {
                                    onExamSelected(exam)
                                }
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = exam.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("${exam.ects} ECTS", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            if (showOverlappingHoursWarning.value) {
                AlertDialog(
                    onDismissRequest = {
                        showOverlappingHoursWarning.value = false
                        selectedExam = null
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showOverlappingHoursWarning.value = false
                            selectedExam = null
                        }) {
                            Text("Cancel")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showOverlappingHoursWarning.value = false
                            selectedExam?.let { onExamSelected(it) }
                        }) {
                            Text("Continue")
                        }
                    },
                    title = { Text("Schedule Conflict", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    text = { Text("Attention: this exam has schedule conflicts with exams already added in your learning agreement. Do you want to continue anyway?") }
                )
            }
        }
    }
}

