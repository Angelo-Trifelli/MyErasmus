package com.example.myerasmus.ui.screens.social

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.utils.profileImageRes
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

data class ChatPreview(
    val name: String,
    val lastMessage: String,
    val isGroup: Boolean,
    val isUserMessage: Boolean,
    val isSeen: Boolean,
    val senderName: String? = null
)

@Composable
fun MessagesScreen(onNavigate: (String) -> Unit) {
    var selectedTab by remember { mutableStateOf("chat") }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.Messages,
                onItemSelected = { destination -> onNavigate(destination.route) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabButton("Chat", selectedTab == "chat") { selectedTab = "chat" }
                TabButton("Explore", selectedTab == "explore") { selectedTab = "explore" }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Search") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (selectedTab == "chat") {
                ChatList(searchText, onNavigate)
            } else {
                ExploreList(searchText)
            }
        }
    }
}

@Composable
fun TabButton(label: String, selected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Color(0xFF003399) else Color.LightGray,
            contentColor = if (selected) Color.White else Color.Black
        ),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.defaultMinSize(minWidth = 80.dp)
    ) {
        Text(label)
    }
}

@Composable
fun ChatList(search: String, onNavigate: (String) -> Unit) {
    val chats = listOf(
        ChatPreview("Carolina Monterini", "Mamma mia veramente 😂", false, true, false),
        ChatPreview("Lucía Fernández", "Absolutamente✋", false, false, false),
        ChatPreview("Barcelona Erasmus 25/26!😎✈️🇪🇸", "Hello to everyone from Germany🇩🇪", true, false, false, "Lukas Schneider"),
        ChatPreview("Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸", "Scrivimi😘", true, false, false, "Carolina Monterini")
    ).filter { it.name.contains(search, ignoreCase = true) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        chats.forEach { chat ->
            ChatItem(
                name = chat.name,
                lastMessage = chat.lastMessage,
                isGroup = chat.isGroup,
                isUserMessage = chat.isUserMessage,
                isSeen = chat.isSeen,
                senderName = chat.senderName
            ) {
                val encodedName = URLEncoder.encode(chat.name, StandardCharsets.UTF_8.toString())
                onNavigate("chatDetail/$encodedName/${chat.isGroup}")
            }
        }
    }
}

data class GroupPreview(
    val name: String,
    val description: String,
    val imageRes: Int,
    val membersCount: Int
)

val groups = listOf(
    GroupPreview(
        "GymBros in Barcelona💪🏋️‍♂️",
        "Hey Erasmus friends! 💪 Looking to stay fit during your adventure in Barcelona? Join our gym group and train together with other international students! Whether you're into weightlifting, cardio, or just want some motivation buddies, this group is all about staying healthy, sharing tips, and having fun while working out. Let's get stronger together — and maybe grab a smoothie after!",
        R.drawable.gym_group,
        42
    ),
    GroupPreview(
        "Concerts & Events in Barcelona",
        "Welcome to the Erasmus Concerts & Events Hub in Barcelona! 🎶🎭 Whether you're into indie gigs, techno nights, jazz sessions, food festivals, or cultural exhibitions — this group is for you! Meet other students with similar tastes, share upcoming events, and find buddies to attend with. Let’s explore the city’s amazing nightlife and cultural scene together.",
        R.drawable.events_group,
        58
    ),
    GroupPreview(
        "Erasmus Study Buddies📚🧑‍🎓🌍",
        "Welcome to the Erasmus Study Buddies Barcelona! 📘🤓 Struggling with lectures, assignments, or exam prep? Join our friendly study group made for Erasmus students in Barcelona. Connect with peers studying the same subjects, form study sessions (virtual or face‑to‑face), exchange notes and tips, and boost your learning together.",
        R.drawable.study_group,
        27
    )
)

@Composable
fun ExploreList(search: String) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val filteredGroups = groups.filter {
        it.name.contains(search, ignoreCase = true)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Surface(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = data.visuals.message,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        },
        containerColor = Color.Transparent
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filteredGroups.forEach { group ->
                ExploreItem(group) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Your request was sent correctly",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun ExploreItem(group: GroupPreview, onRequestJoin: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = group.imageRes),
                    contentDescription = "Group Icon",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(group.name, fontWeight = FontWeight.Bold)
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    Text(group.description, style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Members: ${group.membersCount}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF003399), // blu Erasmus
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(onClick = onRequestJoin) {
                            Text("Request to join")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ChatItem(
    name: String,
    lastMessage: String,
    isGroup: Boolean,
    isUserMessage: Boolean,
    isSeen: Boolean,
    senderName: String? = null,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp)
        ) {
            val imageRes = if (isGroup) {
                when (name) {
                    "Barcelona Erasmus 25/26!😎✈️🇪🇸" -> R.drawable.barcelona_group
                    "Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸" -> R.drawable.italians_group
                    else -> R.drawable.group_icon
                }
            } else {
                profileImageRes(name)
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "$name profile image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(name, fontWeight = FontWeight.Bold)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isUserMessage) {
                        Icon(
                            painter = painterResource(
                                id = if (isSeen)
                                    R.drawable.ic_check_double
                                else
                                    R.drawable.ic_check_single
                            ),
                            contentDescription = "Message status",
                            modifier = Modifier
                                .size(16.dp)
                                .padding(end = 4.dp)
                        )
                    }

                    val previewText = if (isGroup) {
                        if (isUserMessage) {
                            "You: $lastMessage"
                        } else {
                            "${senderName ?: "?"}: $lastMessage"
                        }
                    } else {
                        lastMessage
                    }

                    Text(
                        text = previewText,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
