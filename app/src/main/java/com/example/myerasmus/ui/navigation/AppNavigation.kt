package com.example.myerasmus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.ui.screens.authentication.LoginScreen
import com.example.myerasmus.ui.screens.authentication.RegisterScreen
import com.example.myerasmus.ui.screens.homepage.HomepageScreen
import com.example.myerasmus.ui.screens.profile.ProfileScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {

        // Login
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(BottomBarDestination.Homepage.route) {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // Register
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

        // Homepage
        composable(BottomBarDestination.Homepage.route) {
            HomepageScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(BottomBarDestination.Homepage.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        // Profile
        composable(BottomBarDestination.Profile.route) {
            ProfileScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(BottomBarDestination.Homepage.route)
                        launchSingleTop = true
                    }
                }
            )
        }

        // Placeholder for FindExamPage
        composable(BottomBarDestination.FindExamPage.route) {
            // TODO: Sostituire con la vera schermata
        }

        // Placeholder for LearningAgreementHomepage
        composable(BottomBarDestination.LearningAgreementHomepage.route) {
            // TODO: Sostituire con la vera schermata
        }

        // Placeholder for Messages
        composable(BottomBarDestination.Messages.route) {
            // TODO: Sostituire con la vera schermata
        }
    }
}
