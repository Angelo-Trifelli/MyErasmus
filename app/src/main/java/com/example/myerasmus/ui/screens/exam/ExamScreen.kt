package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.example.myerasmus.utils.examDescriptionRes
import com.example.myerasmus.utils.profileImageRes
import com.example.myerasmus.utils.reviewTextRes

@Composable
fun ExamScreen(
    examName: String,
    onBack: () -> Unit
) {

    val descriptionBoxScrollState = rememberScrollState()
    val examInfo = remember { CommonHelper.findExamByName(examName) }

    val reviewersNames = remember { CommonHelper.getReviewersName(examInfo.name) }

    val imageId = remember { profileImageRes(examInfo.professorFullName) }
    val descriptionId = remember { examDescriptionRes(examInfo.name) }

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            return if (
                (available.y < 0 && descriptionBoxScrollState.canScrollForward) ||
                (available.y > 0 && descriptionBoxScrollState.canScrollBackward)
            ) {
                // consume nothing, let the scroll pass to inner box
                Offset.Zero
            } else {
                // pass through
                Offset.Zero
            }
        }
    }


    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Text(
                        text = examInfo.name,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
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
                actions = {}
            )
        }
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

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
                            alignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxSize() // Fill the clipped and bordered box
                                .clip(CircleShape) // Still clip the image to ensure it matches the container shape
                        )
                    }

                    Column {
                        Text(examInfo.professorFullName, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(examInfo.professorEmail, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("${examInfo.credits} Credits", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF003399)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                        .nestedScroll(nestedScrollConnection)
                        .verticalScroll(descriptionBoxScrollState)
                ) {
                    Text(
                        text = stringResource(id = descriptionId),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(48.dp))
                HorizontalDivider(thickness = 2.dp, color = Color(0xFF003399))
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Student Reviews",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF003399)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                reviewersNames.forEach { reviewerName ->
                    ReviewCard(
                        studentName = reviewerName,
                        rating = 5 ,
                        reviewText = stringResource(reviewTextRes(reviewerName, examInfo.name))
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

        }
    }



}