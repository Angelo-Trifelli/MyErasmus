package com.example.myerasmus.ui.screens.social

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.example.myerasmus.ui.classes.CommonHelper

// ✅ Data class for public profile

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
    onStartChat: (String) -> Unit
) {
    val profile = when (name) {
        "Carolina Monterini" -> PublicProfile(
            description = "Hi! I'm Carolina, a 22-year-old language student. I'm cheerful, open-minded, and love meeting new people and discovering different cultures.\n\nI enjoy traveling, taking photos, baking sweets, and having long chats over coffee.\n\nI'm super excited about my Erasmus in Barcelona — it's been a dream of mine for years! 🇪🇸✨",
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
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2025", semester = "1°"
        )
        "Lukas Schneider" -> PublicProfile(
            description = "Hallo! I'm Lukas, a 22-year-old engineering student from Germany. I love football, tech, and travelling. Excited to start this amazing chapter in Barcelona!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2025", semester = "2°"
        )
        // ✅ Add reviewer profiles here
        "Sophie Dubois" -> PublicProfile(
            description = "Hi! I'm Sophie, an economics student from France. I love discussing global markets and enjoy French pastries during study breaks!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Marek Nowak" -> PublicProfile(
            description = "Hi! I'm Marek, from Poland. Passionate about numbers and finance. Always looking for intellectual challenges and new friends abroad.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Luigi Conti" -> PublicProfile(
            description = "Ciao! I'm Luigi, from Italy. Studying economics and loving the sun, soccer, and good vibes in Barcelona 🇮🇹☀️",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Elena Petrova" -> PublicProfile(
            description = "Hi! I’m Elena from Bulgaria. I study political science and love learning about public policy and governance.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Jonas Berg" -> PublicProfile(
            description = "Hej! I'm Jonas from Sweden. I enjoy winter sports, sustainability studies, and fika with Erasmus friends.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Inés Muñoz" -> PublicProfile(
            description = "Hola! I'm Inés from Spain. I love branding, traveling, and sharing cultures with people from all over the world.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Claire Dupont" -> PublicProfile(
            description = "Bonjour! I'm Claire from France. I'm studying marketing and love photography, street food and Erasmus life in Spain!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Alexander Ivanov" -> PublicProfile(
            description = "Hi! I'm Alexander from Bulgaria. I enjoy marketing analytics, cultural exchange and city exploration.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Theresa Schmidt" -> PublicProfile(
            description = "Hallo! I'm Theresa, a student from Germany who loves data, nature walks and late-night chats about business trends.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Daniel Johansson" -> PublicProfile(
            description = "Hi! I’m Daniel from Sweden. Interested in development economics and always curious about inequality and policy impacts.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Yasmin Ben Saïd" -> PublicProfile(
            description = "Hi! I'm Yasmin from Tunisia. I'm passionate about economic justice, equality and Mediterranean culture 🌍✨",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Kim Lee" -> PublicProfile(
            description = "안녕하세요! I'm Kim from South Korea. Behavioral finance enthusiast, K-pop fan and foodie 🇰🇷🧠💸",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Eduardo Silva" -> PublicProfile(
            description = "Olá! I'm Eduardo from Portugal. I love numbers, surfing and discovering the Latin vibe of Barcelona 🌊📈",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Sofia Rossi" -> PublicProfile(
            description = "Ciao! I'm Sofia from Italy. I study finance, enjoy yoga and love exploring cultural differences in Europe.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Sven Müller" -> PublicProfile(
            description = "Hallo! I'm Sven from Germany. I'm into data analysis, coffee brewing, and long Erasmus nights out!",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Amélie Laurent" -> PublicProfile(
            description = "Salut! I'm Amélie from France. I’m a stats nerd with a passion for travel, writing and intercultural friendships.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Nina Bălan" -> PublicProfile(
            description = "Salut! I'm Nina from Romania. HR student with a soft spot for storytelling and international teamwork 💼🌍",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Omar Haddad" -> PublicProfile(
            description = "Hi! I'm Omar from Morocco. Passionate about HR, psychology and everything related to diversity and inclusion 🌐🤝",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Julia Nowicka" -> PublicProfile(
            description = "Cześć! I'm Julia from Poland. Studying international relations and dreaming of working at the UN 🌍🕊️",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Mohammed Al-Farsi" -> PublicProfile(
            description = "Salam! I'm Mohammed from Oman. I'm curious about diplomacy, history, and multicultural cooperation 🤝🌏",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Felipe García" -> PublicProfile(
            description = "Hola! I'm Felipe from Mexico 🇲🇽. Passionate about strategy, tacos and Spanish culture 🌮📊",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Karin Schneider" -> PublicProfile(
            description = "Hallo! I'm Karin from Austria. I love structure, innovation and all things related to strategic leadership.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
        )
        "Linda Svensson" -> PublicProfile(
            description = "Hej! I'm Linda from Sweden. I'm creative, practical and always ready to build new ideas with international peers.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2023", semester = "1°",
            title = "My Erasmus Experience"
        )
        "Ricardo Costa" -> PublicProfile(
            description = "Olá! I'm Ricardo from Portugal. Business-minded and curious, I enjoy meeting fellow students from around the world.",
            country = "Spain", city = "Barcelona", university = "Universitat de Barcelona", year = "2024", semester = "2°",
            title = "My Erasmus Experience"
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
                    painter = painterResource(
                        id = if (name in listOf(
                                "Carolina Monterini",
                                "Lucía Fernández",
                                "Luca Agnellini",
                                "Martina Monelli",
                                "Giulia Casaldi",
                                "Oliver Bennett",
                                "Lukas Schneider"
                            )) profileImageRes(name)
                        else CommonHelper.reviewerImageRes(name)
                    ),

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
