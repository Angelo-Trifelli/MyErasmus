package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myerasmus.utils.getAllHostExams
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredExamResultsScreen(
    queryParams: Map<String, String>,
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val allExams = getAllHostExams()

    val filteredExams = allExams.filter { exam ->
        (queryParams["university"].isNullOrBlank() || exam.university == queryParams["university"]) &&
                (queryParams["faculty"].isNullOrBlank() || exam.faculty == queryParams["faculty"]) &&
                (queryParams["department"].isNullOrBlank() || exam.department == queryParams["department"]) &&
                (queryParams["course"].isNullOrBlank() || exam.course == queryParams["course"]) &&
                (queryParams["year"].isNullOrBlank() || exam.year == queryParams["year"]) &&
                (queryParams["semester"].isNullOrBlank() || exam.semester == queryParams["semester"]) &&
                (queryParams["credits"].isNullOrBlank() || queryParams["credits"]?.toIntOrNull() == exam.ects) &&
                (queryParams["language"].isNullOrBlank() || exam.language == queryParams["language"])
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Find Exam", color = Color.White, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF003399))
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredExams) { exam ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigate("examPage/${exam.name}?from=filtered")
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(exam.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("${exam.ects} ECTS", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}


