package com.example.myerasmus.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LibraryBooks
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myerasmus.ui.components.enums.BottomBarDestination

@Composable
fun CustomNavigationBar(
    currentDestination: BottomBarDestination,
    onItemSelected: (BottomBarDestination) -> Unit
) {
    NavigationBar(
        containerColor = Color(0xFF003399)
    ) {
        BottomBarDestination.entries.forEach { destination ->
            val icon = when (destination) {
                BottomBarDestination.Homepage -> Icons.Default.Home
                BottomBarDestination.FindExamPage -> Icons.Default.Search
                BottomBarDestination.LearningAgreementHomepage -> Icons.AutoMirrored.Filled.LibraryBooks
                BottomBarDestination.Messages -> Icons.Default.ChatBubble
                BottomBarDestination.Profile -> Icons.Default.Person
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = destination.name,
                        tint = Color(0xFFFFCC00),
                        modifier = Modifier.size(30.dp)
                    )
                },
                selected = destination == currentDestination,
                onClick = { onItemSelected(destination) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFCC00),
                    unselectedIconColor = Color(0xFFFFCC00),
                    indicatorColor = Color(0xFF3366CC)
                )
            )
        }
    }
}
