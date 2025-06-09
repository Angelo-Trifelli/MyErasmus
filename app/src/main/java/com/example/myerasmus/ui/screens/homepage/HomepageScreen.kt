package com.example.myerasmus.ui.screens.homepage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import com.example.myerasmus.ui.components.CustomNavigationBar
import com.example.myerasmus.ui.components.NotificationCard
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
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_myerasmus),
                contentDescription = "Logo MyErasmus",
                modifier = Modifier.size(300.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            //Notifications
            NotificationCard(title = "Notification Title 1")
            NotificationCard(title = "Notification Title 2")
        }
    }

}