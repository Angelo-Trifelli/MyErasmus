package com.example.myerasmus.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.ui.screens.authentication.LoginScreen
import com.example.myerasmus.ui.screens.authentication.RegisterScreen
import com.example.myerasmus.ui.screens.exam.ExamScreen
import com.example.myerasmus.ui.screens.exam.FindExamPage
import com.example.myerasmus.ui.screens.homepage.HomepageScreen
import com.example.myerasmus.ui.screens.profile.ProfileScreen
import com.example.myerasmus.ui.screens.social.MessagesScreen
import com.example.myerasmus.ui.screens.social.ChatDetailScreen
import com.example.myerasmus.ui.screens.social.PublicProfileScreen
import com.example.myerasmus.ui.screens.social.PublicGroupProfileScreen
import java.net.URLDecoder
import java.net.URLEncoder

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
                        launchSingleTop = true
                    }
                }
            )
        }

        // FindExamPage
        composable(BottomBarDestination.FindExamPage.route) {
            FindExamPage(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // ExamPage
        composable(
            route = "examPage/{examName}?from={from}",
            arguments = listOf(
                navArgument("examName") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("from") {
                    type = NavType.StringType
                    defaultValue = "homepage"   //fall back origin
                }
            )
        ) { backStackEntry ->
            val examName = backStackEntry.arguments?.getString("examName") ?: ""
            val from = backStackEntry.arguments?.getString("from") ?: "homepage"

            ExamScreen(
                examName = examName,
                onBack = {
                    navController.popBackStack(from, inclusive = false)
                }
            )
        }

        // LearningAgreementHomepage
        composable(BottomBarDestination.LearningAgreementHomepage.route) {
            // TODO: implementare
        }

        // Messages
        composable(BottomBarDestination.Messages.route) {
            MessagesScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // Profilo pubblico
        composable(
            route = "publicProfile/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val rawName = backStackEntry.arguments?.getString("name") ?: ""
            val decodedName = URLDecoder.decode(rawName, "UTF-8")

            // Logica per distinguere utente vs gruppo
            val isGroup = decodedName.contains("Barcelona Erasmus") ||
                    decodedName.contains("Italiani a Barcellona")

            if (isGroup) {
                PublicGroupProfileScreen(
                    name = decodedName,
                    onBack = { navController.popBackStack() },
                    onNavigateToProfile = { profileName ->
                        navController.navigate("publicProfile/$profileName")
                    }
                )
            } else {
                PublicProfileScreen(
                    name = decodedName,
                    onBack = { navController.popBackStack() },
                    onStartChat = { targetName ->
                        val encoded = URLEncoder.encode(targetName, "UTF-8")
                        navController.navigate("chatDetail/$encoded/false")
                    }
                )
            }
        }

        // Dettaglio chat
        composable(
            route = "chatDetail/{name}/{isGroup}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("isGroup") { type = NavType.BoolType }
            )
        ) { backStackEntry ->
            val name = URLDecoder.decode(backStackEntry.arguments?.getString("name") ?: "", "UTF-8")
            val isGroup = backStackEntry.arguments?.getBoolean("isGroup") == true

            ChatDetailScreen(
                contactName = name,
                isGroup = isGroup,
                onBack = { navController.popBackStack() },
                onProfileClick = { profileName ->
                    val encoded = URLEncoder.encode(profileName, "UTF-8")
                    navController.navigate("publicProfile/$encoded")
                }
            )
        }
    }
}
