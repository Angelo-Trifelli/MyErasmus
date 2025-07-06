package com.example.myerasmus.ui.screens.help

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpDetailScreen(
    sectionId: String,
    onBack: () -> Unit
) {
    val title = when (sectionId) {
        "homepage" -> "Homepage"
        "learningagreement" -> "Learning Agreement"
        "findexam" -> "Find an Exam"
        "social" -> "Social"
        "profile" -> "Profile"
        else -> "Help"
    }

    val content = when (sectionId) {
        "homepage" -> """
        The Homepage is your central hub for navigating the MyErasmus app. From here, you can quickly access the most important features and stay updated on your Erasmus experience.

        Main features:
        • View university deadlines and Erasmus notifications at a glance.
        • Select af favorite the notifications that seems crucial for you

        Suggestions:
        • Use this screen as your starting point every time you open the app.
    """.trimIndent()

        "findexam" -> """
        The Find an Exam section helps you browse and discover exams offered by the host university.

        Key features:
        • Apply filters by university, faculty, department, course, year, semester, ECTS credits, and language.
        • View detailed descriptions of each exam.
        • Check available schedules and compare content.
        • Click on an exam to open its full detail page.
        • You can leave a review and rating for an exam — this is available only if the system confirms you have actually taken it.
        • In the detail page you will find a useful reviews from past Erasmus students about the exam.
    """.trimIndent()

        "learningagreement" -> """
        This section is dedicated to managing your official Learning Agreement.

        What you can do:
        • Create a new agreement or edit an existing one.
        • Select exams from your home university.
        • Receive suggestions for equivalent host exams based on your choices.
        • Manually choose alternative host exams using advanced filters.
        • Associate and save pairs of courses (home ↔ host).
        • View a visual timeline showing approval status from both universities.
        • Access a weekly calendar to detect overlapping schedules.

        Suggestions:
        • Use the timeline dots to monitor approval progress in real time.
    """.trimIndent()

        "social" -> """
        The Social section allows you to connect with other Erasmus students.

        What’s inside:
        • Explore public and private group chats.
        • Find Erasmus students and chat with them.
        • Create your own group
        • Share updates, tips, or plan meetups.

        Additional features:
        • Group profiles show description, members, and visibility status (public/private).
        • Join public groups instantly or request to join private ones.
        • Use the search bar in the char section to find faster a past conversation.
        • Use the search bar in the explore section to find new students or groups.

        Suggestions:
        • Be active! Social interaction improves the Erasmus experience.
    """.trimIndent()

        "profile" -> """
        Your profile is your personal page in the app and lets others know who you are.

        What you can do:
        • Change your profile picture.
        • Add or edit your bio to describe yourself
        • Reach the User's Manual through "Help" section 
        • Logout

        Suggestions:
        • Keep your profile updated — it helps others recognize and connect with you.
        • Use a clear and friendly profile photo.
    """.trimIndent()

        else -> "No help available for this section."
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontSize = 20.sp, color = Color.Black) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            content.lines().forEach { line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    color = Color.DarkGray
                )
            }
        }
    }
}
