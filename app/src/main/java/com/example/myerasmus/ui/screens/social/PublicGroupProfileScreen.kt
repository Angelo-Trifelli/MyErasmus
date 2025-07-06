package com.example.myerasmus.ui.screens.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.myerasmus.utils.DynamicGroupRepository
import com.example.myerasmus.utils.PublicGroupProfile
import com.example.myerasmus.utils.profileImageRes
import java.net.URLEncoder

fun isPrivateGroup(name: String): Boolean {
    return name == "Barcelona Erasmus 25/26!😎✈️🇪🇸" || name == "Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicGroupProfileScreen(
    name: String,
    onBack: () -> Unit,
    onNavigateToProfile: (String) -> Unit
) {
    var showLeaveDialog by remember { mutableStateOf(false) }

    val group = when (name) {
        "Barcelona Erasmus 25/26!😎✈️🇪🇸" -> PublicGroupProfile(
            name = name,
            description = "Group of international Erasmus students heading to Barcelona 25/26!",
            imageRes = R.drawable.barcelona_group,
            members = listOf("Oliver Bennett", "Lucía Fernández", "Lukas Schneider", "Carolina Monterini")
        )
        "Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸" -> PublicGroupProfile(
            name = name,
            description = "Italian students sharing their Erasmus in Barcellona 🇪🇸",
            imageRes = R.drawable.italians_group,
            members = listOf("Luca Agnellini", "Martina Monelli", "Carolina Monterini", "Giulia Casaldi")
        )
        else -> {
            DynamicGroupRepository.getGroup(name) ?: PublicGroupProfile(
                name = name,
                description = "Erasmus group",
                imageRes = R.drawable.group_icon,
                members = emptyList()
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(group.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = group.imageRes),
                    contentDescription = "Group image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(group.name, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(8.dp))

// Mostra tipo gruppo (privato/pubblico) con icona
                val isPrivate = isPrivateGroup(group.name)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isPrivate) "Private Group" else "Public Group",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isPrivate) Color.Red else Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        painter = painterResource(
                            id = if (!isPrivate) R.drawable.lock_open else R.drawable.lock
                        ),
                        contentDescription = null,
                        tint = if (isPrivate) Color.Red else Color(0xFF4CAF50),
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))


                Text(
                    text = group.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Group Members",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF003399)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    group.members.forEach { member ->
                        MemberCard(name = member) {
                            val encoded = URLEncoder.encode(member, "UTF-8")
                            onNavigateToProfile(encoded)
                        }
                    }
                }
            }

            FloatingActionButton(
                onClick = { showLeaveDialog = true },
                containerColor = Color(0xFFD32F2F),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_leave_group),
                    contentDescription = "Leave group",
                    modifier = Modifier.size(32.dp)
                )
            }

            if (showLeaveDialog) {
                AlertDialog(
                    onDismissRequest = { showLeaveDialog = false },
                    title = { Text("Leave group?") },
                    text = { Text("Are you sure you want to leave this group?") },
                    confirmButton = {
                        TextButton(onClick = { showLeaveDialog = false }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLeaveDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MemberCard(name: String, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            val imageRes = profileImageRes(name)

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$name profile",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
