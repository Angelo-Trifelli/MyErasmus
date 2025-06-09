package com.example.myerasmus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myerasmus.ui.screens.authentication.LoginScreen
import com.example.myerasmus.ui.screens.authentication.RegisterScreen
import com.example.myerasmus.ui.screens.homepage.HomepageScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {

        // Login Route
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("homepage") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }
        // Register Route
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                },
                onLoginClick = {
                    navController.navigate("login")
                }
            )
        }

        // Homepage Route
        composable("homepage") {
            HomepageScreen(
                onNavigate = {
                    //TODO: implement navigation
                }
            )
        }
    }
}