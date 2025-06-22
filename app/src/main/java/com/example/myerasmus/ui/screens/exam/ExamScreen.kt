package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.classes.CommonHelper
import com.example.myerasmus.ui.components.CustomTopBar
import com.example.myerasmus.ui.components.ReviewCard
import com.example.myerasmus.utils.HomeUniversityExam
import com.example.myerasmus.utils.HostUniversityExam
import com.example.myerasmus.utils.examDescriptionRes

@Composable
fun ExamScreen(
    examName: String,
    onBack: () -> Unit
) {
    val examInfo = remember { CommonHelper.findExamByName(examName) }
    val reviewers = remember { CommonHelper.getReviewsForExam(examInfo.name) }
    val imageId = remember { CommonHelper.profileImageRes(examInfo.professorFullName) }
    val descriptionId = remember { examDescriptionRes(examInfo.name) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = examInfo.name,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Go Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    Spacer(modifier = Modifier.width(48.dp)) // per bilanciare la freccia
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                // --- Professor Info ---
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                            .border(width = 5.dp, color = Color(0xFFFFCC00), shape = CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = "Professor Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
                        )
                    }

                    Column {
                        Text(examInfo.professorFullName, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(examInfo.professorEmail, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(5.dp))
                        val credits = when (examInfo) {
                            is HomeUniversityExam -> examInfo.cfu
                            is HostUniversityExam -> examInfo.ects
                            else -> 0
                        }
                        Text("$credits Credits", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- Description ---
                Text(
                    text = "Description",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF003399)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = descriptionId),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }


                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Student Reviews",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF003399)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- Reviews scrollabili ---
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(reviewers) { (name, rating, textRes) ->
                    ReviewCard(
                        studentName = name,
                        rating = rating,
                        reviewText = stringResource(id = textRes),
                        studentImage = CommonHelper.reviewerImageRes(name)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
