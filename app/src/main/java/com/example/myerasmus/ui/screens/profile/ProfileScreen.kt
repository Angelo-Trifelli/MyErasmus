package com.example.myerasmus.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination

@Composable
fun ProfileScreen(onNavigate: (String) -> Unit) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.Profile,
                onItemSelected = { destination ->
                    onNavigate(destination.route)
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("Ruzzoli", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("1234567", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(5.dp))
                        Text("ruzzoli.1234567@studio.unibo.it", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Description",
                    style = MaterialTheme.typography.titleLarge
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .border(1.dp, Color.Gray)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Hi! I'm a student about to start my Erasmus adventure. I love traveling, discovering new cultures, and spending time in nature. In my free time, I enjoy reading, drawing, and listening to music. I'm really looking forward to meeting new people and making unforgettable memories!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Next Erasmus Experience:",
                        style = MaterialTheme.typography.titleLarge
                    )

                    InfoRow("- Country:", "Spain")
                    InfoRow("- City:", "Barcelona")
                    InfoRow("- University:", "Universitat de Barcelona")
                    InfoRow("- Year:", "2025")
                    InfoRow("- Semester:", "1°")
                }
            }

            // BOTTOM ICON BUTTONS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 120.dp, start = 24.dp, end = 24.dp), // 👈 Aggiunto spazio per la BottomBar
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                // ICONA BLU MODIFICA (fittizia)
                IconButton(onClick = { /* no-op */ }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit_profile),
                        contentDescription = "Edit Profile",
                        modifier = Modifier.size(42.dp),
                        colorFilter = ColorFilter.tint(Color(0xFF003399))
                    )
                }

                // ICONA ROSSA LOGOUT
                IconButton(onClick = { showLogoutDialog = true }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Logout",
                        modifier = Modifier.size(42.dp),
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                }
            }

            // DIALOGO LOGOUT
            if (showLogoutDialog) {
                Dialog(onDismissRequest = { showLogoutDialog = false }) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 6.dp,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(
                                text = "Are you sure you want to log out?",
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TextButton(
                                    onClick = {
                                        showLogoutDialog = false
                                        onNavigate("login")
                                    }
                                ) {
                                    Text("Confirm")
                                }

                                TextButton(onClick = { showLogoutDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        }
                    }
                }

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
