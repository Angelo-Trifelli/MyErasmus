package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import com.example.myerasmus.utils.HostUniversityExam
import com.example.myerasmus.utils.TimeSlot

@Composable
fun CalendarScreen(
    hostExams: List<HostUniversityExam>,
    onBack: () -> Unit
) {
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri")
    val hours = (8..19).toList()

    var popupCourses by remember { mutableStateOf<List<String>>(emptyList()) }
    var popupColor by remember { mutableStateOf(Color(0xFFBBDEFB)) }

    val slotMap = remember {
        mutableMapOf<Pair<String, Int>, MutableList<HostUniversityExam>>().apply {
            hostExams.forEach { exam ->
                exam.schedule.forEach { slot: TimeSlot ->
                    val dayName = days[slot.dayOfWeek - 1]
                    for (h in slot.startHour until slot.startHour + slot.duration) {
                        val key = dayName to h
                        getOrPut(key) { mutableListOf() }.add(exam)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 8.dp)
    ) {
        // 🔙 Back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF003399),
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(32.dp)
                    .clickable { onBack() }
            )
        }

        // Header days
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(50.dp))
            days.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003399),
                        maxLines = 1
                    )
                }
            }
        }

        // Time grid
        hours.forEach { hour ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$hour:00", style = MaterialTheme.typography.bodySmall)
                }

                days.forEach { day ->
                    val exams = slotMap[day to hour].orEmpty()
                    val bgColor = when {
                        exams.size > 1 -> Color(0xFFFFCDD2)
                        exams.size == 1 -> Color(0xFFBBDEFB)
                        else -> Color(0xFFF5F5F5)
                    }

                    val label = when {
                        exams.size > 1 -> "${exams.first().name} +${exams.size - 1}"
                        exams.size == 1 -> exams.first().name
                        else -> ""
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .padding(2.dp)
                            .clickable(enabled = exams.isNotEmpty()) {
                                popupCourses = exams.map { it.name }
                                popupColor = if (exams.size > 1) Color(0xFFFFCDD2) else Color(0xFFBBDEFB)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(bgColor, RoundedCornerShape(4.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Popup dialog
        if (popupCourses.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = { popupCourses = emptyList() },
                confirmButton = {},
                containerColor = popupColor,
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        popupCourses.forEach { course ->
                            Text(
                                text = course,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}
