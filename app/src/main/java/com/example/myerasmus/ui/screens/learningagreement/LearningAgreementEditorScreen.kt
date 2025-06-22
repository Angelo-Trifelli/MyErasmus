package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
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
    val isNew = agreement == null
    val initialState = agreement ?: newUnsavedAgreement ?: LearningAgreement(
        id = -1,
        title = "New Learning Agreement",
        status = 0,
        associations = emptyList()
    )

    var title by remember { mutableStateOf(initialState.title) }
    var statusLevel by remember { mutableStateOf(initialState.status) }
    val associations = remember {
        mutableStateListOf<Pair<HostUniversityExam, HomeUniversityExam>>().apply {
            addAll(initialState.associations)
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showStatusDialog by remember { mutableStateOf(false) }
    var showSendDialog by remember { mutableStateOf(false) }
    var showSentConfirmation by remember { mutableStateOf(false) }

    if (isNew) {
        newUnsavedAgreement = LearningAgreement(-1, title, statusLevel, associations)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Status", style = MaterialTheme.typography.titleLarge)
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2196F3))
                                .clickable { showStatusDialog = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("?", color = Color.White, style = MaterialTheme.typography.labelSmall)
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
                        if (initialState.id == -1 && newUnsavedAgreement != null) {
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
                            tint = Color(0xFF003399)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showSendDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Send",
                            tint = Color(0xFF003399)
                        )
                    }

                    if (showSendDialog) {
                        AlertDialog(
                            onDismissRequest = { showSendDialog = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showSendDialog = false
                                    if (statusLevel == 0) statusLevel = 1
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
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
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

                FloatingActionButton(
                    onClick = {
                        if (initialState.id == -1) {
                            val newId = nextLearningAgreementId++
                            val newAgreement = LearningAgreement(
                                id = newId,
                                title = title,
                                status = statusLevel,
                                associations = associations.toList()
                            )
                            allLearningAgreements.add(newAgreement)
                            newUnsavedAgreement = null
                            onNavigate("learningAgreementHomepage?created=true")
                        } else {
                            val updated = initialState.copy(
                                title = title,
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
                        val laId = if (initialState.id == -1) "new" else initialState.id.toString()
                        onNavigate("selectHomeExam/$laId")
                    },
                    containerColor = Color(0xFF2196F3)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
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
                value = title,
                onValueChange = { title = it },
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
                            Text("↕", color = Color.Gray)
                            Text("${homeExam.name} (${homeExam.cfu} CFU)")
                        }
                        Row {
                            IconButton(onClick = {
                                associations.remove(Pair(hostExam, homeExam))
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
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
        "Sent",
        "Approved by Home University",
        "Approved by Host University"
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
