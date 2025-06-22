package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myerasmus.ui.classes.ExamFilterState
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.CustomTopBar
import com.example.myerasmus.ui.components.ExamFilterWindow
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindExamPage(onNavigate: (String) -> Unit) {
    val examFilterState = remember { mutableStateOf(ExamFilterState()) }
    val filter = examFilterState.value

    val isSearchEnabled =
        filter.university.isNotBlank() &&
                filter.faculty.isNotBlank() &&
                filter.department.isNotBlank() &&
                filter.course.isNotBlank()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Find Exam",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                navigationIcon = {},
                actions = {}
            )
        },
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.FindExamPage,
                onItemSelected = { destination -> onNavigate(destination.route) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                ExamFilterWindow(state = examFilterState, findExactMatch = false)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val query = listOf(
                            "university=${URLEncoder.encode(filter.university, "UTF-8")}",
                            "faculty=${URLEncoder.encode(filter.faculty, "UTF-8")}",
                            "department=${URLEncoder.encode(filter.department, "UTF-8")}",
                            "course=${URLEncoder.encode(filter.course, "UTF-8")}",
                            "year=${URLEncoder.encode(filter.year, "UTF-8")}",
                            "semester=${URLEncoder.encode(filter.semester, "UTF-8")}",
                            "credits=${URLEncoder.encode(filter.credits, "UTF-8")}",
                            "language=${URLEncoder.encode(filter.language, "UTF-8")}"
                        ).joinToString("&")

                        onNavigate("filteredExamResults?$query")
                    },
                    enabled = isSearchEnabled,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSearchEnabled) Color(0xFF003399) else Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text("Search Exams")
                }
            }
        }
    }
}
