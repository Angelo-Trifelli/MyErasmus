package com.example.myerasmus.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.myerasmus.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ProfileScreen(
    onNavigate: (String) -> Unit
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            // User profile and personal data
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
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Profile",
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.TopEnd)
                            .padding(4.dp),
                        tint = Color.DarkGray
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

            // Description
            Text(text = "Description", style = MaterialTheme.typography.titleMedium)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .border(1.dp, Color.Gray)
                    .padding(8.dp)
            ) {
                Text("Hi! I'm a student about to start my Erasmus adventure. I love traveling, discovering new cultures, " +
                        "and spending time in nature. In my free time, I enjoy reading, drawing, and listening to music. " +
                        "I'm really looking forward to meeting new people and making unforgettable memories!", color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp), // spazio tra righe
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Next Erasmus Experience:", style = MaterialTheme.typography.titleMedium)

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("- Country:",fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Italy", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("- City:",fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Bologna", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("- University:",fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("Università di Bologna", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("- Year:",fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("2025", modifier = Modifier.weight(1f))
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("- Semester:",fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    Text("1°", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
