package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myerasmus.utils.getAllHostExams

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewHostExamsScreen(
    onBack: () -> Unit
) {
    val exams = getAllHostExams().take(5)
    val matchPercentages = listOf(92, 68, 60, 52, 38)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compatible Courses") },
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
            itemsIndexed(exams) { index, exam ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${exam.name} | ${exam.code}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${exam.ects} ECTS",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Text(
                            text = "${matchPercentages[index]}%",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color(0xFF003399)),
                        )
                    }
                }
            }

            // Scritta centrata in fondo
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Do you want to choose another exam?",
                        color = Color(0xFF003399),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
