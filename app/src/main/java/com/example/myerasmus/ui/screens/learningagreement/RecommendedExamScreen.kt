@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myerasmus.utils.HostUniversityExam
import com.example.myerasmus.utils.HomeUniversityExam
import com.example.myerasmus.utils.hasOverlappingHours

@Composable
fun RecommendedExamScreen(
    hostExam: HostUniversityExam,
    laId: String,
    onViewExam: () -> Unit,
    onChooseAnother: (String) -> Unit,
    onBack: () -> Unit
){
    val hasOverlappingHours = remember { mutableStateOf(hasOverlappingHours(laId, hostExam)) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Recommended Exam", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF003399)
                )
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "We recommend this course:",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    color = Color(0xFF003399)
                )
            )

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFD9EEFF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(hostExam.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(hostExam.courseCode, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("${hostExam.ects} ECTS", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Professor: ${hostExam.professorFullName}", style = MaterialTheme.typography.bodySmall)
                    Text(hostExam.professorEmail, style = MaterialTheme.typography.bodySmall)
                }
            }

            if (hasOverlappingHours.value) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Attention: this exam has schedule conflicts with exams already added in your learning agreement.",
                        color = Color.Red
                    )
                }
            }

            Button(
                onClick = { onViewExam() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
            ) {
                Text("View Exam")
            }

            Text(
                text = "Do you want to choose another exam?",
                color = Color(0xFF003399),
                modifier = Modifier.clickable { onChooseAnother(laId) },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
