package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindExamPage(
    onNavigate: (String) -> Unit
) {

    val examFilterState = remember { mutableStateOf(ExamFilterState()) }


    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Text(
                        text = "Find Exam",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {},
                actions = {}
            )
        },
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.FindExamPage,
                onItemSelected = { destination ->
                    onNavigate(destination.route)
                }
            )
        }
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExamFilterWindow(state = examFilterState, findExactMatch = true)
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = { onNavigate("examPage/${examFilterState.value.exam}?from=findExam") },
                    enabled = examFilterState.value.isExamSelectable && examFilterState.value.exam.isNotBlank(),
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = if (examFilterState.value.isExamSelectable && examFilterState.value.exam.isNotBlank()) Color(0xFF003399) else Color.Gray,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ManageSearch,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }

    }


}