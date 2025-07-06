package com.example.myerasmus.ui.screens.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myerasmus.utils.profileImageRes
import com.example.myerasmus.R

data class PublicProfile(
    val description: String,
    val country: String,
    val city: String,
    val university: String,
    val year: String,
    val semester: String,
    val title: String = "My Next Erasmus Experience"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicProfileScreen(
    name: String,
    onBack: () -> Unit,
    onStartChat: (String) -> Unit // ✅ nuovo nome corretto
) {
    val profile = when (name) {
        "Carolina Monterini" -> PublicProfile(
            description = "Hi! I'm Carolina, a 22-year-old language student. I'm cheerful, open-minded, and love meeting new people and discovering different cultures.\n\nI enjoy traveling, taking photos, baking sweets, and having long chats over coffee.\n\nI'm super excited about my Erasmus in Barcelona — it's been a dream of mine for years! I can't wait to explore the city, practice my Spanish, and live this amazing adventure. 🇪🇸✨",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2025", semester = "1°"
        )
        "Lucía Fernández" -> PublicProfile(
            description = "Hi! I'm Lucía, a 23-year-old student from Colombia. I'm passionate about art, languages, and Latin American culture. I'm friendly, curious, and I love learning from others.\n\nIn my free time, I enjoy painting, reading poetry, and dancing salsa — it's in my blood!\n\nI'm beyond excited to start my Erasmus in Barcelona. The city’s energy, diversity, and cultural richness feel like the perfect place for this new adventure! 🇨🇴✨🇪🇸",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2025", semester = "1°"
        )
        "Luca Agnellini" -> PublicProfile(
            description = "Ciao! I'm Luca, a law student who loves calcio, cinema and carbonara. Ready to take over Barcelona 🇮🇹🇪🇸",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Martina Monelli" -> PublicProfile(
            description = "Hi! I'm Martina, curious and creative. I study marketing and can’t wait to discover tapas, Gaudí and sunsets in Barcelona!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Giulia Casaldi" -> PublicProfile(
            description = "Hi! I'm Giulia, I study design and love photography, languages and Spanish culture. Barcelona here I come!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Oliver Bennett" -> PublicProfile(
            description = "Hi! I'm Oliver, a business student from the UK. I'm curious, friendly, and passionate about discovering new cultures. Can't wait to live the Barcelona vibe!",
            country = "Spain",
            city = "Barcelona",
            university = "Universitat de Barcelona",
            year = "2025",
            semester = "1°",
        )"Lukas Schneider" -> PublicProfile(
            description = "Hallo! I'm Lukas, a 22-year-old engineering student from Germany. I love football, tech, and travelling. Excited to start this amazing chapter in Barcelona!",
            country = "Spain",
            city = "Barcelona",
            university = "Universitat de Barcelona",
            year = "2025",
            semester = "2°",
        )


        else -> PublicProfile(
            description = "Erasmus student excited for the next adventure!",
            country = "Unknown", city = "Unknown", university = "Unknown", year = "-", semester = "-"
        )
    }

     Scaffold(
         topBar = {
             TopAppBar(
                 title = { Text(name) },
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
                     painter = painterResource(id = profileImageRes(name)),
                     contentDescription = "$name profile",
                     contentScale = ContentScale.Crop,
                     modifier = Modifier
                         .size(120.dp)
                         .clip(CircleShape)
                 )

                 Spacer(modifier = Modifier.height(16.dp))

                 Text(name, style = MaterialTheme.typography.headlineSmall)

                 Spacer(modifier = Modifier.height(8.dp))

                 Text(
                     text = profile.description,
                     style = MaterialTheme.typography.bodyMedium,
                     color = Color.Gray,
                     modifier = Modifier.padding(horizontal = 24.dp)
                 )

                 Spacer(modifier = Modifier.height(24.dp))

                 Text(
                     text = profile.title,
                     style = MaterialTheme.typography.titleMedium,
                     color = Color(0xFF003399)
                 )

                 Spacer(modifier = Modifier.height(12.dp))

                 Column(horizontalAlignment = Alignment.CenterHorizontally) {
                     InfoRow(label = "Country", value = profile.country)
                     InfoRow(label = "City", value = profile.city)
                     InfoRow(label = "University", value = profile.university)
                     InfoRow(label = "Year", value = profile.year)
                     InfoRow(label = "Semester", value = profile.semester)
                 }
             }

             FloatingActionButton(
                 onClick = { onStartChat(name) },
                 containerColor = Color(0xFF003399),
                 shape = CircleShape,
                 modifier = Modifier
                     .align(Alignment.BottomEnd)
                     .padding(16.dp)
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.ic_chat),
                     contentDescription = "Start chat",
                     tint = Color.White,
                     modifier = Modifier.size(28.dp)
                 )
             }



         }
     }

 }

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
