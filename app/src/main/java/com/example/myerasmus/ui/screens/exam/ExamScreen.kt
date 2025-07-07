package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
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
import com.example.myerasmus.utils.allLearningAgreements
import com.example.myerasmus.utils.examDescriptionRes
import com.example.myerasmus.utils.newUnsavedAgreement
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope




@Composable
fun ExamScreen(
    examName: String,
    homeUniversityExam: HomeUniversityExam?,
    showAddToLaButton: Boolean,
    learningAgreementId: String,
    onBack: () -> Unit,
    onExamAdded: () -> Unit,
    onAddReview: (prefillRating: Int?, prefillText: String?) -> Unit,
    onNavigateToProfile: (String) -> Unit
) {
    val examInfo = remember { CommonHelper.findExamByName(examName) }
    var reviews by remember { mutableStateOf(CommonHelper.getReviewsForExam(examInfo.name)) }
    var hasUserReview by remember { mutableStateOf(CommonHelper.hasUserReview(examInfo.name)) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showAddToLaConfirmDialog by remember { mutableStateOf(false) }

    val imageId = remember { CommonHelper.profileImageRes(examInfo.professorFullName) }
    val descriptionId = remember { examDescriptionRes(examInfo.name) }
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    val detailsOffset = remember { mutableStateOf(0) }
    val reviewsOffset = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Go Back", tint = Color.White)
                    }
                },
                actions = {}
            )
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 56.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
            ) {
                Spacer(Modifier.height(24.dp))

                Column(modifier = Modifier.onGloballyPositioned { coordinates ->
                    detailsOffset.value = coordinates.positionInParent().y.toInt()
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = "Professor",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .border(5.dp, Color(0xFFFFCC00), CircleShape)
                        )
                        Column {
                            Text(text = examInfo.professorFullName, style = MaterialTheme.typography.headlineSmall)
                            Spacer(Modifier.height(4.dp))
                            Text(text = examInfo.professorEmail, style = MaterialTheme.typography.bodyLarge)
                            Spacer(Modifier.height(12.dp))
                            Text("Code: ${examInfo.courseCode}", fontWeight = FontWeight.Medium)
                            val credits = when (examInfo) {
                                is HomeUniversityExam -> examInfo.cfu
                                is HostUniversityExam -> examInfo.ects
                                else -> 0
                            }
                            Text("$credits Credits", style = MaterialTheme.typography.bodyLarge)
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    Text("Description", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFF003399)))
                    Spacer(Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray).padding(8.dp)
                    ) {
                        Text(text = stringResource(id = descriptionId), color = Color.DarkGray)
                    }
                }

                Spacer(Modifier.height(32.dp))

                Column(modifier = Modifier.onGloballyPositioned { coordinates ->
                    reviewsOffset.value = coordinates.positionInParent().y.toInt()
                }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Student Reviews", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold, color = Color(0xFF003399)))
                        if (!showAddToLaButton && !hasUserReview) {
                            IconButton(onClick = { onAddReview(null, null) }) {
                                Icon(Icons.Default.Add, contentDescription = "Add Review", tint = Color(0xFF003399))
                            }
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    reviews.forEachIndexed { index, (name, rating, content) ->
                        val text = when (content) {
                            is Int -> stringResource(id = content)
                            is String -> content
                            else -> ""
                        }

                        Box(modifier = Modifier.fillMaxWidth()) {
                            ReviewCard(
                                studentName = name,
                                rating = rating,
                                reviewText = text,
                                studentImage = CommonHelper.reviewerImageRes(name),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(enabled = name != "Anna Ruzzoli") {
                                        onNavigateToProfile(name)
                                    }
                            )

                            if (name == "Anna Ruzzoli") {
                                Row(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 12.dp)
                                ) {
                                    IconButton(onClick = {
                                        onAddReview(rating, text)
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit Review", tint = Color(0xFF003399))
                                    }
                                    IconButton(onClick = {
                                        showDeleteDialog = true
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete Review", tint = Color.Red)
                                    }
                                }
                            }
                        }

                        if (index != reviews.lastIndex) Spacer(Modifier.height(12.dp))
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(8.dp)
                    .align(Alignment.TopCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val blue = Color(0xFF003399)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(detailsOffset.value)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = blue)
                    ) {
                        Text("Details", color = Color.White)
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                scrollState.animateScrollTo(reviewsOffset.value)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = blue)
                    ) {
                        Text("Reviews", color = Color.White)
                    }
                }

                if (showAddToLaButton) {
                    val green = Color(0xFF4CAF50) // Verde brillante
                    Button(
                        onClick = {
                            showAddToLaConfirmDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = green)
                    ) {
                        Text("Add to LA", color = Color.White)
                    }
                }

            }



            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            CommonHelper.deleteReview(examInfo.name)
                            reviews = CommonHelper.getReviewsForExam(examInfo.name)
                            hasUserReview = false
                            showDeleteDialog = false
                        }) {
                            Text("Delete", color = Color.Red)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    title = { Text("Delete Review?") },
                    text = { Text("Are you sure you want to delete your review?") }
                )
            }

            if (showAddToLaConfirmDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showAddToLaConfirmDialog = false
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showAddToLaConfirmDialog = false
                        }) {
                            Text("No")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showAddToLaConfirmDialog = false
                            if (homeUniversityExam == null) throw Exception("Missing home university exam")
                            addExamToLa(learningAgreementId, examInfo, homeUniversityExam)
                            onExamAdded()
                        }) {
                            Text("Confirm")
                        }
                    },
                    title = { Text("Attention", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    text = { Text("Are you sure you want to add this exam to your Learning Agreement?") }
                )
            }
        }
    }
}

fun addExamToLa(learningAgreementId: String, hostUniversityExam: HostUniversityExam, homeUniversityExam: HomeUniversityExam) {
    if (learningAgreementId == "new") {
        val targetAgreement = newUnsavedAgreement
        newUnsavedAgreement = targetAgreement?.copy(
            associations = targetAgreement.associations + (hostUniversityExam to homeUniversityExam)
        )
        return
    }

    val id = learningAgreementId.toInt()
    val learningAgreement = allLearningAgreements.find { it.id == id }

    if (learningAgreement == null) {
        throw Exception("Can't find a learning agreement")
    }

    val updatedAgreement = learningAgreement.copy(
        associations = learningAgreement.associations + (hostUniversityExam to homeUniversityExam)
    )

    allLearningAgreements.removeIf { it.id == id }
    allLearningAgreements.add(updatedAgreement)
}
