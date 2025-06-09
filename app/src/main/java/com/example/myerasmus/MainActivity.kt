package com.example.myerasmus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.myerasmus.ui.navigation.AppNavigation
import com.example.myerasmus.ui.theme.MyErasmusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyErasmusTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

