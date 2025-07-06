package com.example.myerasmus.ui.screens.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.layout.navigationBarsPadding
import com.example.myerasmus.R
import com.example.myerasmus.utils.DynamicGroupRepository
import com.example.myerasmus.utils.profileImageRes
import com.example.myerasmus.utils.PublicGroupProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    onBack: () -> Unit,
    onGroupCreated: (String) -> Unit
) {
    var groupName by remember { mutableStateOf("") }
    var groupDescription by remember { mutableStateOf("") }
    var isPublic by remember { mutableStateOf(true) }
    var selectedImage by remember { mutableStateOf(R.drawable.group_icon) }

    var showImageOptions by remember { mutableStateOf(false) }
    var showBackConfirmation by remember { mutableStateOf(false) }

    val allUsers = listOf(
        "Lucía Fernández", "Carolina Monterini", "Luca Agnellini",
        "Martina Monelli", "Giulia Casaldi", "Oliver Bennett"
    )
    val selectedMembers = remember { mutableStateListOf<String>() }

    val canSave = groupName.isNotBlank() && groupDescription.isNotBlank() && selectedMembers.isNotEmpty()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Create Group") },
                navigationIcon = {
                    IconButton(onClick = { showBackConfirmation = true }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(
                    onClick = {
                        if (canSave) {
                            DynamicGroupRepository.addGroup(
                                PublicGroupProfile(
                                    name = groupName,
                                    description = groupDescription,
                                    imageRes = R.drawable.group_icon,
                                    members = selectedMembers.toList()
                                )
                            )
                            onGroupCreated(groupName)
                        }
                    },
                    enabled = canSave,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (canSave) Color(0xFF003399) else Color(0xFFB0BEC5),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Save")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = selectedImage),
                        contentDescription = "Group image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .clickable { showImageOptions = true }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Tap to change",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = groupDescription,
                onValueChange = { groupDescription = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Public Group")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = isPublic, onCheckedChange = { isPublic = it })
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Add Members", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                allUsers.forEach { name ->
                    val imageId = profileImageRes(name)
                    val isSelected = selectedMembers.contains(name)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isSelected) selectedMembers.remove(name)
                                else selectedMembers.add(name)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) Color(0xFFD9EEFF) else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                        ) {
                            Checkbox(
                                checked = isSelected,
                                onCheckedChange = {
                                    if (it) selectedMembers.add(name)
                                    else selectedMembers.remove(name)
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = imageId),
                                contentDescription = "$name image",
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(name, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // spazio extra per evitare overlap
        }

        if (showImageOptions) {
            ModalBottomSheet(onDismissRequest = { showImageOptions = false }) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Select from gallery",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // TODO: open gallery
                                showImageOptions = false
                            }
                            .padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        if (showBackConfirmation) {
            AlertDialog(
                onDismissRequest = { showBackConfirmation = false },
                title = { Text("Discard group?") },
                text = { Text("If you go back now, your changes will be lost.") },
                confirmButton = {
                    TextButton(onClick = { onBack() }) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showBackConfirmation = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
