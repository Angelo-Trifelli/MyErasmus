package com.example.myerasmus.ui.screens.homepage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.enums.BottomBarDestination

@Composable
fun HomepageScreen(
    onNavigate: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            CustomNavigationBar(
                currentDestination = BottomBarDestination.Homepage,
                onItemSelected = { destination ->
                    onNavigate(destination.route)
                }
            )
        }
    ) {
        // ✅ Inserisci la nuova schermata notifiche moderna
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NotificationsScreen()
        }
    }
}
