@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.myerasmus.ui.screens.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(onNavigate: (String) -> Unit) {
    var showMenu by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showProfilePreview by remember { mutableStateOf(false) }
    var showEditOptions by remember { mutableStateOf(false) }

    var isEditingDescription by remember { mutableStateOf(false) }
    var currentDescription by remember {
        mutableStateOf(
            "Hi! I'm a student about to start my Erasmus adventure. I love traveling, discovering new cultures, and spending time in nature. In my free time, I enjoy reading, drawing, and listening to music. I'm really looking forward to meeting new people and making unforgettable memories!"
        )
    }
    var editedDescription by remember { mutableStateOf(currentDescription) }
    var showCancelDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var pendingAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "User Profile",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = {
                        if (isEditingDescription) {
                            pendingAction = { showMenu = true }
                            showCancelDialog = true
                        } else {
                            showMenu = true
                        }
                    }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Change image profile") },
                            onClick = {
                                showMenu = false
                                if (isEditingDescription) {
                                    pendingAction = { showProfilePreview = true }
                                    showCancelDialog = true
                                } else {
                                    showProfilePreview = true
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Change description") },
                            onClick = {
                                showMenu = false
                                isEditingDescription = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Help") },
                            onClick = {
                                showMenu = false
                                if (isEditingDescription) {
                                    pendingAction = { onNavigate("help") }
                                    showCancelDialog = true
                                } else {
                                    onNavigate("help")
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Logout", color = Color.Red) },
                            onClick = {
                                showMenu = false
                                if (isEditingDescription) {
                                    pendingAction = { showLogoutDialog = true }
                                    showCancelDialog = true
                                } else {
                                    showLogoutDialog = true
                                }
                            }
                        )
                    }

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF003399))
            )
        }
        ,
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.Profile,
                onItemSelected = { destination ->
                    if (isEditingDescription) {
                        pendingAction = { onNavigate(destination.route) }
                        showCancelDialog = true
                    } else {
                        onNavigate(destination.route)
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color(0xFFDFFFE0),
                    contentColor = Color(0xFF2E7D32)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.user_profile),
                            contentDescription = "User Profile",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text("Anna", style = MaterialTheme.typography.bodyLarge)
                        Text("Ruzzoli", style = MaterialTheme.typography.bodyLarge)
                        Text("1234567", style = MaterialTheme.typography.bodyLarge)
                        Text("ruzzoli.1234567@studio.unibo.it", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Description", style = MaterialTheme.typography.titleLarge)

                if (isEditingDescription) {
                    OutlinedTextField(
                        value = editedDescription,
                        onValueChange = { editedDescription = it },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 10
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { showCancelDialog = true }) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            currentDescription = editedDescription
                            isEditingDescription = false
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Description modified")
                            }
                        }) {
                            Text("Save")
                        }
                    }
                } else {
                    val scrollState = rememberScrollState()
                    val isLongText = currentDescription.length > 300

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(
                                if (isLongText)
                                    Modifier.height(180.dp).verticalScroll(scrollState)
                                else
                                    Modifier.wrapContentHeight()
                            )
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = currentDescription,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.DarkGray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Next Erasmus Experience:", style = MaterialTheme.typography.titleLarge)
                    InfoRow("- Country:", "Spain")
                    InfoRow("- City:", "Barcelona")
                    InfoRow("- University:", "Universitat de Barcelona")
                    InfoRow("- Year:", "2025")
                    InfoRow("- Semester:", "1°")
                }
            }

            if (showProfilePreview) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.75f))
                        .clickable { showProfilePreview = false },
                    contentAlignment = Alignment.Center
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Image(
                            painter = painterResource(id = R.drawable.user_profile),
                            contentDescription = "Full Image",
                            modifier = Modifier
                                .size(300.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                        )
                        IconButton(
                            onClick = { showEditOptions = true },
                            modifier = Modifier
                                .offset(x = (-8).dp, y = (-8).dp)
                                .background(Color.White, CircleShape)
                                .size(64.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_edit_profile),
                                contentDescription = "Edit Image",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }

            if (showEditOptions) {
                ModalBottomSheet(
                    onDismissRequest = { showEditOptions = false },
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp) // margini più contenuti
                    ) {
                        Text(
                            text = "Choose from gallery",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showEditOptions = false
                                    showProfilePreview = false
                                    // TODO: apri galleria
                                }
                        )

                        Spacer(modifier = Modifier.height(32.dp)) // <-- solo spazio tra le due opzioni

                        Text(
                            text = "Delete",
                            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    showEditOptions = false
                                    showProfilePreview = false
                                    // TODO: elimina immagine
                                }
                        )
                    }
                }


            }

            if (showLogoutDialog) {
                Dialog(onDismissRequest = { showLogoutDialog = false }) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 6.dp,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text("Are you sure you want to log out?", style = MaterialTheme.typography.headlineSmall)
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Ora Cancel è a sinistra
                                TextButton(onClick = { showLogoutDialog = false }) {
                                    Text("Cancel")
                                }
                                TextButton(onClick = {
                                    showLogoutDialog = false
                                    onNavigate("login")
                                }) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }
                }

            }

            if (showCancelDialog) {
                AlertDialog(
                    onDismissRequest = { showCancelDialog = false },
                    title = { Text("Discard changes?") },
                    text = { Text("Are you sure you want to discard your changes?") },
                    confirmButton = {
                        TextButton(onClick = {
                            editedDescription = currentDescription
                            isEditingDescription = false
                            showCancelDialog = false
                            pendingAction?.invoke()
                            pendingAction = null
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showCancelDialog = false
                            pendingAction = null
                        }) {
                            Text("No")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        Text(value, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
    }
}
