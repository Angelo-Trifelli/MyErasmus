package com.example.myerasmus.ui.screens.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    onGoBack: () -> Unit,
    onPasswordResetRequest: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var email by remember { mutableStateOf("") }
    var invalidInstitutionalEmail = remember { mutableStateOf(false) }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .padding(vertical = 64.dp, horizontal = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { onGoBack() },
                Modifier.clip(CircleShape)
                    .background(color = Color(0xFF003399))
                    .border(width = 1.dp, color = Color.White, shape = CircleShape)

            ) {
                Icon(
                    Icons.Default.ArrowBackIosNew,
                    contentDescription = "Go Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Back to Login",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
        ) {
            Text(
                text = "Forgot password",
                color = Color(0xFF003399),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Enter your institutional e-mail address and we will send you instructions on how to reset your password",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Your institutional E-mail") },
                label = { Text("Institutional E-mail") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    if (isLoginValid(email)) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("E-mail sent")
                        }
                        onPasswordResetRequest()
                    } else {
                        invalidInstitutionalEmail.value = true
                    }
                },
                enabled = email.isNotBlank(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399))
            ) {
                Text("Confirm", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.ExtraBold)
            }
        }

        when {
            invalidInstitutionalEmail.value -> {
                AlertDialog(
                    onDismissRequest = { invalidInstitutionalEmail.value = true },
                    confirmButton = {
                        TextButton(onClick = { invalidInstitutionalEmail.value = false }) {
                            Text("Ok")
                        }
                    },
                    title = { Text("Warning", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                    text = { Text("Please insert a valid institutional email") }
                )
            }
        }
    }
}

fun isLoginValid(email: String) : Boolean {
    val validEmailSuffix = listOf("studenti.uniroma1.it", "studio.unibo.it", "ub.edu")

    var isValidSuffixPresent = false

    for (elem in validEmailSuffix) {
        if (email.endsWith(elem)) {
            isValidSuffixPresent = true
            break
        }
    }

    return isValidSuffixPresent
}