package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myerasmus.utils.HomeUniversityExam
import com.example.myerasmus.utils.getAllHomeExams

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectHomeExamScreen(
    alreadyUsed: List<String>,
    onExamSelected: (HomeUniversityExam) -> Unit,
    onBack: () -> Unit
) {
    val exams = getAllHomeExams()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Courses") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(exams) { exam ->
                val isUsed = exam.code in alreadyUsed
                val cardColor = if (isUsed) Color.LightGray else Color.White
                val textColor = if (isUsed) Color.Gray else Color.Black

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !isUsed) {
                            onExamSelected(exam)
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = exam.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${exam.cfu} CFU",
                            style = MaterialTheme.typography.bodySmall,
                            color = textColor
                        )

                    }
                }
            }
        }
    }
}
