package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.utils.LearningAgreement
import com.example.myerasmus.utils.allLearningAgreements
import kotlinx.coroutines.launch

@Composable
fun LearningAgreementScreen(
    onNavigate: (String) -> Unit,
    onUpload: (LearningAgreement) -> Unit,
    justCreated: Boolean = false
) {
    val latestId = remember { allLearningAgreements.maxOfOrNull { it.id } ?: -1 }
    var showSuccessMessage by remember {
        mutableStateOf(justCreated && latestId != -1)
    }

    var showUploadPopup by remember { mutableStateOf(false) }
    var learningAgreementToUpload : LearningAgreement? by remember { mutableStateOf(null) }

    var showDeletePopup by remember { mutableStateOf(false) }
    var agreementToDeleteId by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.LearningAgreementHomepage,
                onItemSelected = { onNavigate(it.route) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Add Learning Agreement Button
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .background(Color(0xFF800080), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            onNavigate("learningAgreementEditor/new")
                        }
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Add\nLearning\nAgreement",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Upload Learning Agreement Button (with warning)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .background(Color(0xFF800080), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            val approvedAgreement = allLearningAgreements.find { it.status == 4 }
                            if (approvedAgreement == null) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        "You need a LA with “Approved By Host Coordinator” status"
                                    )
                                }
                            } else {
                                learningAgreementToUpload = approvedAgreement
                                showUploadPopup = true
                            }
                        }
                        .padding(start = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CloudUpload,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Upload\nLearning\nAgreement",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }

            if (showSuccessMessage) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFDFFFE0))
                ) {
                    Text(
                        text = "Learning Agreement created successfully!",
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFF2E7D32),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(3000)
                    showSuccessMessage = false
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            allLearningAgreements.forEach { agreement ->
                Card(
                    elevation = CardDefaults.cardElevation(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (agreement.status == 4) Color(0xFF43A047) else Color(
                            0xFF437CF8
                        ),
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onNavigate("learningAgreementEditor/${agreement.id}?isUploaded=${false}") }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_document),
                                contentDescription = "Document",
                                colorFilter = ColorFilter.tint(color = Color.White),
                                modifier = Modifier
                                    .size(36.dp)
                                    .padding(end = 12.dp)
                            )
                            Column {
                                Text(agreement.title, fontWeight = FontWeight.Bold)
                                Text("Status: ${setStatusLabel(agreement)}", fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "${agreement.associations.size} associations",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        if (agreement.status == 0) {
                            Row{
                                IconButton(onClick = {
                                    showDeletePopup = true
                                    agreementToDeleteId = agreement.id
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }

            if (showUploadPopup) {
                AlertDialog(
                    onDismissRequest = {
                        showUploadPopup = false
                        learningAgreementToUpload = null
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showUploadPopup = false
                            learningAgreementToUpload = null
                        }) {
                            Text("No")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showUploadPopup = false

                            if (learningAgreementToUpload != null) {
                                onUpload(learningAgreementToUpload!!)
                            }
                        }) {
                            Text("Confirm")
                        }
                    },
                    title = { Text("Confirm Final Upload", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    text = {
                        Column {
                            Text("Are you sure you want to finalize and upload the Learning Agreement titled:")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                learningAgreementToUpload?.title ?: "",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                )
            }

            if (showDeletePopup) {
                AlertDialog(
                    onDismissRequest = {
                        showDeletePopup = false
                        agreementToDeleteId = 0
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showDeletePopup = false
                            agreementToDeleteId = 0
                        }) {
                            Text("No")
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeletePopup = false
                            allLearningAgreements.removeIf { it.id == agreementToDeleteId }
                            agreementToDeleteId = 0

                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    "Learning Agreement deleted successfully"
                                )
                            }
                        }) {
                            Text("Confirm")
                        }
                    },
                    title = { Text("Warning", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    text = {
                        Text("Are you sure you want to delete this learning agreement?")
                    }
                )
            }
        }
    }
}
