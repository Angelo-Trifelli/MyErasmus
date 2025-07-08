package com.example.myerasmus.ui.screens.exam

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myerasmus.ui.components.StarRating

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    onSaveReview: (Int, String) -> Unit,
    onBack: () -> Unit,
    prefillRating: Int = 0,
    prefillText: String? = null
) {
    var selectedRating by remember { mutableStateOf(prefillRating) }
    var reviewText by remember { mutableStateOf(TextFieldValue(prefillText ?: "")) }
    var showExitDialog by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }


    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    onBack()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("No")
                }
            },
            title = { Text("Discard review?") },
            text = { Text("Are you sure you want to go back? Your review will be lost.") }
        )
    }


    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    onSaveReview(selectedRating, reviewText.text)

                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Submit Review") },
            text = { Text("Are you sure you want to submit this review?") }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Add Review",
                        fontSize = 22.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            val isEnabled = selectedRating > 0 && reviewText.text.isNotBlank()

            FloatingActionButton(
                onClick = { if (isEnabled) showConfirmDialog = true },
                containerColor = if (isEnabled) Color(0xFF003399) else Color.LightGray,
                contentColor = Color.White
            ) {
                Text("Save", color = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text("Your Rating:", style = MaterialTheme.typography.titleLarge)
            StarRating(
                rating = selectedRating,
                onRatingChanged = { selectedRating = it },
                starSize = 40.dp
            )

            Text("Write your review:", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                placeholder = { Text("Type your review here...") }
            )
        }
    }
}
