package com.example.myerasmus.ui.screens.learningagreement

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.utils.allLearningAgreements
import kotlinx.coroutines.launch

@Composable
fun LearningAgreementScreen(
    onNavigate: (String) -> Unit,
    justCreated: Boolean = false
) {
    val latestId = remember { allLearningAgreements.maxOfOrNull { it.id } ?: -1 }
    var showSuccessMessage by remember {
        mutableStateOf(justCreated && latestId != -1)
    }

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
                        .background(Color(0xFFBA68C8), shape = RoundedCornerShape(12.dp))
                        .clickable {
                            val hasApproved = allLearningAgreements.any { it.status == 3 }
                            if (!hasApproved) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        "You need a LA with “Approved By Host University” status."
                                    )
                                }
                            } else {
                                // TODO: implement real upload functionality here
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            onNavigate("learningAgreementEditor/${agreement.id}")
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_document),
                            contentDescription = "Document",
                            modifier = Modifier
                                .size(36.dp)
                                .padding(end = 12.dp)
                        )
                        Column {
                            Text(agreement.title, fontWeight = FontWeight.Bold)
                            Text(
                                "${agreement.associations.size} associations",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
