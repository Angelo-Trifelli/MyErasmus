package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.CustomTopBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.utils.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningAgreementEditorScreen(
    agreement: LearningAgreement?,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showStatusDialog by remember { mutableStateOf(false) }
    var showSendDialog by remember { mutableStateOf(false) }
    var showSentConfirmation by remember { mutableStateOf(false) }


    val learningAgreement = agreement ?: newUnsavedAgreement ?: LearningAgreement(
        id = -1,
        title = "New Learning Agreement",
        status = 0,
        associations = emptyList()
    )

    var learningAgreementTitle by remember { mutableStateOf(learningAgreement.title) }
    var learningAgreementStatusLabel by remember { mutableStateOf(setStatusLabel(learningAgreement)) }

    //Track in a separate variable in order to update the UI in real-time
    var statusLevel by remember { mutableStateOf(learningAgreement.status) }

    val associations = remember {
        mutableStateListOf<Pair<HostUniversityExam, HomeUniversityExam>>().apply {
            addAll(learningAgreement.associations)
        }
    }

    if (agreement == null) {
        newUnsavedAgreement = LearningAgreement(-1, learningAgreementTitle, learningAgreement.status, associations)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = learningAgreementStatusLabel,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            IconButton(
                                onClick = { showStatusDialog = true },
                                modifier = Modifier.size(24.dp) // Shrink button padding
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF2196F3))
                                ) {
                                    Text(
                                        "?",
                                        color = Color.White,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }

                        if (showStatusDialog) {
                            AlertDialog(
                                onDismissRequest = { showStatusDialog = false },
                                confirmButton = {
                                    TextButton(onClick = { showStatusDialog = false }) {
                                        Text("Close")
                                    }
                                },
                                title = { Text("Agreement Progress") },
                                text = {
                                    LearningAgreementStatusStepper(statusLevel)
                                }
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (learningAgreement.id == -1 && newUnsavedAgreement != null) {
                            val newId = nextLearningAgreementId++
                            val saved = newUnsavedAgreement!!.copy(id = newId)
                            allLearningAgreements.add(saved)
                            newUnsavedAgreement = null
                            onNavigate("learningAgreementHomepage?created=true")
                        } else {
                            onBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    if ((statusLevel == 0 || statusLevel == 2) && associations.isNotEmpty()) {
                        IconButton(onClick = { showSendDialog = true }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "Send",
                                tint = Color.White
                            )
                        }
                    }

                    if (showSendDialog) {
                        AlertDialog(
                            onDismissRequest = { showSendDialog = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    statusLevel += 1
                                    learningAgreement.status += 1
                                    learningAgreementStatusLabel = setStatusLabel(learningAgreement)

                                    showSendDialog = false
                                    showSentConfirmation = true
                                }) {
                                    Text("Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showSendDialog = false }) {
                                    Text("Cancel")
                                }
                            },
                            title = { Text("Send Learning Agreement") },
                            text = { Text("Do you want to send this Learning Agreement to prof@example.com?") }
                        )
                    }
                }
            )
        },
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.LearningAgreementHomepage,
                onItemSelected = { onNavigate(it.route) }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = {
                        val examIds = associations.map { it.first.code }
                        val joinedIds = examIds.joinToString("_")
                        onNavigate("calendar/$joinedIds")
                    },
                    containerColor = Color(0xFF3F51B5)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_custom_calendar),
                        contentDescription = "Calendar",
                        modifier = Modifier.size(28.dp)
                    )
                }

                if (statusLevel == 0) {
                    FloatingActionButton(
                        onClick = {
                            if (learningAgreement.id == -1) {
                                val newId = nextLearningAgreementId++
                                val newAgreement = LearningAgreement(
                                    id = newId,
                                    title = learningAgreementTitle,
                                    status = statusLevel,
                                    associations = associations.toList()
                                )
                                allLearningAgreements.add(newAgreement)
                                newUnsavedAgreement = null
                                onNavigate("learningAgreementHomepage?created=true")
                            } else {
                                val updated = learningAgreement.copy(
                                    title = learningAgreementTitle,
                                    status = statusLevel,
                                    associations = associations.toList()
                                )
                                allLearningAgreements.removeIf { it.id == updated.id }
                                allLearningAgreements.add(updated)
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("All changes have been saved.")
                                }
                                onNavigate("learningAgreementHomepage")
                            }
                        },
                        containerColor = Color(0xFF4CAF50)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Save", tint = Color.White)
                    }

                    FloatingActionButton(
                        onClick = {
                            val laId = if (learningAgreement.id == -1) "new" else learningAgreement.id.toString()
                            onNavigate("selectHomeExam/$laId")
                        },
                        containerColor = Color(0xFF2196F3)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color(0xFF81C784),
                    contentColor = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = learningAgreementTitle,
                onValueChange = { learningAgreementTitle = it },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            associations.forEach { (hostExam, homeExam) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("${hostExam.name} (${hostExam.ects} ECTS)", style = MaterialTheme.typography.titleMedium)
                            Text(hostExam.courseCode, style = MaterialTheme.typography.titleMedium)
                            Text("↕", color = Color.Gray)
                            Text("${homeExam.name} (${homeExam.cfu} CFU)")
                            Text(homeExam.courseCode)
                        }

                        when {
                            statusLevel == 0 -> {
                                Row {
                                    IconButton(onClick = {
                                        associations.remove(Pair(hostExam, homeExam))
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (showSentConfirmation) {
                LaunchedEffect(Unit) {
                    delay(3000)
                    showSentConfirmation = false
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        color = Color(0xFFDFFFE0),
                        shape = MaterialTheme.shapes.medium,
                        shadowElevation = 2.dp
                    ) {
                        Text(
                            text = "Your Learning Agreement was sent correctly",
                            modifier = Modifier.padding(12.dp),
                            color = Color(0xFF2E7D32),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LearningAgreementStatusStepper(statusLevel: Int) {
    val steps = listOf(
        "Not Sent",
        "Sent to Home Coordinator",
        "Approved by Home Coordinator",
        "Sent to Host Coordinator",
        "Approved by Host Coordinator"
    )

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        steps.forEachIndexed { index, label ->
            val isActive = index <= statusLevel
            val circleColor = if (isActive) Color(0xFF4CAF50) else Color.Gray
            val lineColor = if (index < statusLevel) Color(0xFF4CAF50) else Color.LightGray

            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(circleColor)
                    )
                    if (index < steps.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .width(2.dp)
                                .height(24.dp)
                                .background(lineColor)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isActive) Color.Black else Color.Gray
                )
            }
        }
    }
}

fun setStatusLabel(selectedLearningAgreement: LearningAgreement) : String {
    //The LA still needs to be sent
    if (selectedLearningAgreement.status == 0) {
        return "Drafting"
    }

    if (selectedLearningAgreement.status == 1 || selectedLearningAgreement.status == 3) {
        return "Sent"
    }

    if (selectedLearningAgreement.status == 2) {
        return "Approved by Home Coordinator"
    }

    if (selectedLearningAgreement.status == 4) {
        return "Approved by Host Coordinator"
    }

    return "Status"
}
