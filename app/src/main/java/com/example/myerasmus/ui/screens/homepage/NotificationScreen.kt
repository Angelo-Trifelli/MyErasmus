package com.example.myerasmus.ui.screens.homepage

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
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
import java.text.SimpleDateFormat
import java.util.*

// DATA MODEL

data class Notification(
    val title: String,
    val subtitle: String,
    val type: String,
    val date: String,
    val iconRes: Int,
    var isFavorite: Boolean,
    val body: String,
    var isNew: Boolean = false
)

@Composable
fun NotificationsScreen() {
    var selectedTab by remember { mutableStateOf("all") }
    var search by remember { mutableStateOf("") }
    val notifications = remember {
        mutableStateListOf(
            Notification("University of Bologna", "Deadline to upload LA - 01/07/2025", "home", "18/06/2025", R.drawable.unibo_icon, false, "Remember that July 1st, 2025 is the last day to upload your Learning Agreement to the portal. It must include your RAM's signature and your Erasmus coordinator's signature at the host university.", isNew = true),
            Notification("ESN Barcelona", "Welcome event - July 25th", "esn", "16/06/2025", R.drawable.esn_icon, false, "Join our first ESN event to meet other Erasmus students! We've sent you an email with all the details.", isNew = true),
            Notification("University of Bologna", "Erasmus documents approved", "home", "14/06/2025", R.drawable.unibo_icon, false, "Your Erasmus documents have been approved by your home university.", isNew = true),
            Notification("University of Barcelona", "Welcome to the student portal", "host", "20/05/2025", R.drawable.ub_icon, true, "Log in with your credentials to complete your registration with the University of Barcelona.", isNew = false),
            Notification("University of Bologna", "Erasmus rankings published!", "home", "05/05/2025", R.drawable.unibo_icon, false, "The Erasmus 2025/2026 rankings have just been published. Visit www.unibo/erasmus.it", isNew = false),
            Notification("University of Bologna", "Reminder: Erasmus application deadline", "home", "20/03/2025", R.drawable.unibo_icon, false, "Reminder: the deadline to apply for Erasmus is March 28, 2025.", isNew = true),
            Notification("University of Bologna", "New Erasmus calls for all departments published", "home", "14/02/2025", R.drawable.unibo_icon, false, "On February 14, 2025, the new Erasmus 2025/2026 calls were published on www.unibo/erasmus.it. The application deadline is March 28, 2025.", isNew = false)
        )
    }

    val filtered = when (selectedTab) {
        "favorites" -> notifications.filter { it.isFavorite && it.title.contains(search, true) }
        else -> notifications.filter { it.title.contains(search, true) || it.subtitle.contains(search, true) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        HeaderSection(selectedTab, { selectedTab = it }, search, { search = it })

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filtered) { notification ->
                NotificationItem(notification) {
                    notification.isFavorite = !notification.isFavorite
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    search: String,
    onSearchChange: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_myerasmus),
                contentDescription = "Logo MyErasmus",
                modifier = Modifier.size(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterTab("All messages", selectedTab == "all") { onTabSelected("all") }
            FilterTab("Favorites", selectedTab == "favorites") { onTabSelected("favorites") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = search,
            onValueChange = onSearchChange,
            placeholder = { Text("Search...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}

@Composable
fun FilterTab(label: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color(0xFF003399) else Color.White,
            contentColor = if (selected) Color.White else Color.Black
        ),
        border = BorderStroke(1.dp, Color(0xFF003399)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(label)
    }
}

fun formatDateToIOStyle(dateString: String): String {
    return try {
        val parser = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = parser.parse(dateString)
        val formatter = SimpleDateFormat("d MMM", Locale("en"))
        formatter.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}

@Composable
fun NotificationItem(notification: Notification, onToggleFavorite: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var isFav by remember { mutableStateOf(notification.isFavorite) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (notification.isNew) Color(0xFFE3F2FD) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    expanded = !expanded
                    notification.isNew = false
                }
                .padding(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(48.dp)) {
                    Image(
                        painter = painterResource(id = notification.iconRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .border(1.dp, Color.LightGray, CircleShape)
                    )
                    if (notification.isNew) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color.Red)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(notification.title, fontWeight = FontWeight.Bold)
                    Text(notification.subtitle, style = MaterialTheme.typography.bodySmall)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = formatDateToIOStyle(notification.date),
                        style = MaterialTheme.typography.labelSmall
                    )
                    IconButton(onClick = {
                        onToggleFavorite()
                        isFav = !isFav
                    }) {
                        Icon(
                            imageVector = if (isFav) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = "Favorite",
                            tint = if (isFav) Color(0xFFFFC107) else Color.Gray
                        )
                    }
                }
            }

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = notification.body,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}