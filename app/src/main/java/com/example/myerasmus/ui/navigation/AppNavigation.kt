package com.example.myerasmus.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myerasmus.ui.components.enums.BottomBarDestination
import com.example.myerasmus.ui.screens.authentication.ForgotPasswordScreen
import com.example.myerasmus.ui.screens.authentication.LoginScreen
import com.example.myerasmus.ui.screens.authentication.RegisterScreen
import com.example.myerasmus.ui.screens.exam.ExamScreen
import com.example.myerasmus.ui.screens.exam.FindExamPage
import com.example.myerasmus.ui.screens.homepage.HomepageScreen
import com.example.myerasmus.ui.screens.learningagreement.*
import com.example.myerasmus.ui.screens.profile.ProfileScreen
import com.example.myerasmus.ui.screens.social.*
import com.example.myerasmus.utils.*
import java.net.URLEncoder
import java.net.URLDecoder
import com.example.myerasmus.ui.screens.exam.FilteredExamResultsScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(navController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color(0xFFDFFFE0),
                    contentColor = Color(0xFF2E7D32)
                )
            }
        }
    ) { @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
        NavHost(navController = navController, startDestination = "login") {

            composable(BottomBarDestination.Homepage.route) {
                HomepageScreen(onNavigate = { navController.navigate(it) })
            }

            composable(BottomBarDestination.FindExamPage.route) {
                FindExamPage(onNavigate = { navController.navigate(it) })
            }

            composable(BottomBarDestination.LearningAgreementHomepage.route) {
                LearningAgreementScreen(onNavigate = { navController.navigate(it) })
            }

            composable("learningAgreementHomepage?created={created}",
                arguments = listOf(navArgument("created") {
                    type = NavType.BoolType
                    defaultValue = false
                })
            ) { backStackEntry ->
                val created = backStackEntry.arguments?.getBoolean("created") ?: false
                LearningAgreementScreen(
                    onNavigate = { navController.navigate(it) },
                    justCreated = created
                )
            }

            composable("learningAgreementEditor/new") {
                LearningAgreementEditorScreen(
                    agreement = null,
                    onNavigate = { navController.navigate(it) },
                    onBack = {
                        navController.navigate("learningAgreementHomepage") {
                            popUpTo("learningAgreementHomepage") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("learningAgreementEditor/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                val agreement = allLearningAgreements.find { it.id == id } ?: return@composable

                LearningAgreementEditorScreen(
                    agreement = agreement,
                    onNavigate = { navController.navigate(it) },
                    onBack = {
                        navController.navigate("learningAgreementHomepage") {
                            popUpTo("learningAgreementHomepage") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("calendar/{examCodes}",
                arguments = listOf(navArgument("examCodes") { type = NavType.StringType })
            ) { backStackEntry ->
                val codesRaw = backStackEntry.arguments?.getString("examCodes").orEmpty()
                val codes = codesRaw.split("_").filter { it.isNotBlank() }
                val exams = getAllHostExams().filter { it.code in codes }

                CalendarScreen(
                    hostExams = exams,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(BottomBarDestination.Messages.route) {
                MessagesScreen(
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(
                route = "publicProfile/{name}",
                arguments = listOf(navArgument("name") { type = NavType.StringType })
            ) { backStackEntry ->
                val rawName = backStackEntry.arguments?.getString("name") ?: ""
                val decodedName = URLDecoder.decode(rawName, "UTF-8")

            val isGroup = DynamicGroupRepository.exists(decodedName) ||
                    decodedName.contains("Barcelona Erasmus") ||
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




        composable(
            route = "chatDetail/{name}/{isGroup}?createdByUser={createdByUser}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("isGroup") { type = NavType.BoolType },
                navArgument("createdByUser") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { backStackEntry ->
            val name = URLDecoder.decode(backStackEntry.arguments?.getString("name") ?: "", "UTF-8")
            val isGroup = backStackEntry.arguments?.getBoolean("isGroup") == true
            val createdByUser = backStackEntry.arguments?.getBoolean("createdByUser") ?: false

            ChatDetailScreen(
                contactName = name,
                isGroup = isGroup,
                createdByUser = createdByUser, // ✅
                onBack = {
                    navController.navigate("messages") {
                        popUpTo("messages") { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onProfileClick = { profileName ->
                    val encoded = URLEncoder.encode(profileName, "UTF-8")
                    navController.navigate("publicProfile/$encoded")
                }
            )
        }

            composable("selectHomeExam/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val idArg = backStackEntry.arguments?.getString("id") ?: return@composable
                val agreement = if (idArg == "new") newUnsavedAgreement else allLearningAgreements.find { it.id == idArg.toIntOrNull() }

                if (agreement != null) {
                    val usedCodes = agreement.associations.map { it.second.code }
                    SelectHomeExamScreen(
                        alreadyUsed = usedCodes,
                        onExamSelected = { selectedHomeExam ->
                            val suggestedHost = getSuggestedExam(selectedHomeExam.name)
                            val encodedHome = URLEncoder.encode(selectedHomeExam.name, "UTF-8")
                            val encodedHost = URLEncoder.encode(suggestedHost.name, "UTF-8")
                            navController.navigate("loadingRecommendation/$encodedHome/$encodedHost/$idArg")
                        },
                        onBack = { navController.popBackStack() }
                    )
                } else {
                    navController.popBackStack()
                }
            }

            composable(
                "loadingRecommendation/{homeName}/{hostName}/{laId}",
                arguments = listOf(
                    navArgument("homeName") { type = NavType.StringType },
                    navArgument("hostName") { type = NavType.StringType },
                    navArgument("laId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val homeName = URLDecoder.decode(backStackEntry.arguments?.getString("homeName") ?: "", "UTF-8")
                val hostName = URLDecoder.decode(backStackEntry.arguments?.getString("hostName") ?: "", "UTF-8")
                val laId = backStackEntry.arguments?.getString("laId") ?: "new"

                val homeExam = getAllHomeExams().find { it.name == homeName } ?: return@composable
                val hostExam = getAllHostExams().find { it.name == hostName } ?: return@composable

                LoadingRecommendationScreen(
                    onFinish = {
                        val encodedHome = URLEncoder.encode(homeExam.name, "UTF-8")
                        val encodedHost = URLEncoder.encode(hostExam.name, "UTF-8")
                        navController.navigate("recommendedExam/$encodedHome/$encodedHost/$laId") {
                            popUpTo("learningAgreementHomepage") { inclusive = false }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable("recommendedExam/{homeName}/{hostName}/{laId}",
                arguments = listOf(
                    navArgument("homeName") { type = NavType.StringType },
                    navArgument("hostName") { type = NavType.StringType },
                    navArgument("laId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val homeName = URLDecoder.decode(backStackEntry.arguments?.getString("homeName") ?: "", "UTF-8")
                val hostName = URLDecoder.decode(backStackEntry.arguments?.getString("hostName") ?: "", "UTF-8")
                val laId = backStackEntry.arguments?.getString("laId") ?: "new"

                val homeExam = getAllHomeExams().find { it.name == homeName } ?: return@composable
                val hostExam = getAllHostExams().find { it.name == hostName } ?: return@composable

                RecommendedExamScreen(
                    hostExam = hostExam,
                    laId = laId,
                    onViewExam = {
                        navController.navigate(
                            "examPage/${hostExam.name}?from=recommendedExam/${homeName}/${hostName}/${laId}&showAddToLaButton=${true}&laId=${laId}&homeExamName=${homeName}"
                        )
                    },
                    onChooseAnother = { id ->
                        navController.navigate("chooseAnotherExam/${URLEncoder.encode(homeExam.name, "UTF-8")}/$id")
                    },
                    onBack = {
                        val target = if (laId == "new") "learningAgreementEditor/new" else "learningAgreementEditor/$laId"
                        navController.navigate(target) {
                            popUpTo("recommendedExam/$homeName/$hostName/$laId") { inclusive = true }
                        }
                    }
                )
            }

            composable(
                "chooseAnotherExam/{homeExamName}/{laId}",
                arguments = listOf(
                    navArgument("homeExamName") { type = NavType.StringType },
                    navArgument("laId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val homeExamName = URLDecoder.decode(backStackEntry.arguments?.getString("homeExamName") ?: "", "UTF-8")
                val laId = backStackEntry.arguments?.getString("laId") ?: "new"

                ChooseAnotherExamScreen(
                    learningAgreementId = laId,
                    onBack = {
                        navController.popBackStack()
                    },
                    onExamSelected = { selectedHostExam ->
                        navController.navigate(
                            "examPage/${selectedHostExam.name}?from=chooseAnotherExam/${homeExamName}/${laId}&showAddToLaButton=${true}&laId=${laId}&homeExamName=${homeExamName}"
                        )
                    }
                )
            }

        composable(BottomBarDestination.Profile.route) {
            ProfileScreen(onNavigate = { navController.navigate(it) })
        }

            composable(
                route = "filteredExamResults?university={university}&faculty={faculty}&department={department}&course={course}&year={year}&semester={semester}&credits={credits}&language={language}",
                arguments = listOf(
                    navArgument("university") { defaultValue = ""; type = NavType.StringType },
                    navArgument("faculty") { defaultValue = ""; type = NavType.StringType },
                    navArgument("department") { defaultValue = ""; type = NavType.StringType },
                    navArgument("course") { defaultValue = ""; type = NavType.StringType },
                    navArgument("year") { defaultValue = ""; type = NavType.StringType },
                    navArgument("semester") { defaultValue = ""; type = NavType.StringType },
                    navArgument("credits") { defaultValue = ""; type = NavType.StringType },
                    navArgument("language") { defaultValue = ""; type = NavType.StringType }
                )
            ) { backStackEntry ->

                val args = backStackEntry.arguments!!
                val queryParams = mapOf(
                    "university" to URLDecoder.decode(args.getString("university") ?: "", "UTF-8"),
                    "faculty" to URLDecoder.decode(args.getString("faculty") ?: "", "UTF-8"),
                    "department" to URLDecoder.decode(args.getString("department") ?: "", "UTF-8"),
                    "course" to URLDecoder.decode(args.getString("course") ?: "", "UTF-8"),
                    "year" to URLDecoder.decode(args.getString("year") ?: "", "UTF-8"),
                    "semester" to URLDecoder.decode(args.getString("semester") ?: "", "UTF-8"),
                    "credits" to URLDecoder.decode(args.getString("credits") ?: "", "UTF-8"),
                    "language" to URLDecoder.decode(args.getString("language") ?: "", "UTF-8")
                )

                FilteredExamResultsScreen(
                    queryParams = queryParams,
                    onBack = { navController.popBackStack() },
                    onNavigate = { route -> navController.navigate(route) }
                )
            }

            composable(
                route = "examPage/{examName}?from={source}&showAddToLaButton={isAddToLaButtonVisible}&laId={laId}&homeExamName={homeExamName}",
                arguments = listOf(
                    navArgument("examName") { type = NavType.StringType },
                    navArgument("source") {
                        type = NavType.StringType
                        defaultValue = "default"
                    },
                    navArgument("isAddToLaButtonVisible") {
                        type = NavType.BoolType
                        defaultValue = false
                    },
                    navArgument("laId") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("homeExamName") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val examName = backStackEntry.arguments?.getString("examName") ?: ""
                val showAddToLaButton = backStackEntry.arguments?.getBoolean("isAddToLaButtonVisible") == true
                val learningAgreementId = backStackEntry.arguments?.getString("laId") ?: ""
                val homeExamName = backStackEntry.arguments?.getString("homeExamName") ?: ""
                val homeUniversityExam = getAllHomeExams().find { it.name == homeExamName }

                ExamScreen(
                    examName = examName,
                    showAddToLaButton = showAddToLaButton,
                    learningAgreementId = learningAgreementId,
                    homeUniversityExam = homeUniversityExam,
                    onBack = {
                        navController.popBackStack()
                    },
                    onExamAdded = {
                        if (learningAgreementId == "new") {
                            navController.navigate("learningAgreementEditor/new")
                        } else {
                            navController.navigate("learningAgreementEditor/${learningAgreementId.toInt()}")
                        }
                    }
                )
            }

            composable("createGroup") {
                CreateGroupScreen(
                    onBack = { navController.popBackStack() },
                    onGroupCreated = { groupName ->
                        val encoded = URLEncoder.encode(groupName, "UTF-8")
                        navController.navigate("chatDetail/$encoded/true?createdByUser=true") {
                            popUpTo(BottomBarDestination.Messages.route)
                        }
                    }
                )
            }


            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(BottomBarDestination.Homepage.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onRegisterClick = { navController.navigate("register") },
                    onForgotPasswordClick = { navController.navigate("forgotPassword")}
                )
            }

            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    },
                    onLoginClick = { navController.navigate("login") }
                )
            }

            composable("forgotPassword") {
                ForgotPasswordScreen(
                    snackbarHostState = snackbarHostState,
                    coroutineScope = coroutineScope,
                    onGoBack = { navController.navigate("login") },
                    onPasswordResetRequest = { navController.navigate("login") }
                )
            }
        }
    }


}
