package com.example.myerasmus.ui.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myerasmus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf(false) }
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
            .padding(32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 120.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(id = R.drawable.logo_myerasmus),
                    contentDescription = "Logo MyErasmus",
                    modifier = Modifier.size(300.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        loginError = false
                    },
                    placeholder = { Text("Your institutional email") },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        loginError = false
                    },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    }
                )

                if (loginError) {
                    Text(
                        text = "email or password incorrect",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = Color(0xFF2F53A4),
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Forgot Password?")
                                }
                            },
                            textAlign = TextAlign.End,
                            modifier = Modifier.clickable{ onForgotPasswordClick() }
                        )
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

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (isLoginValid(email, password, invalidInstitutionalEmail)) {
                        onLoginSuccess()
                    } else {
                        password = ""
                        loginError = !invalidInstitutionalEmail.value
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399)),
                enabled = email.isNotBlank() && password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = 40.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFFC107),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Register")
                        }
                    },
                    modifier = Modifier.clickable { onRegisterClick() }
                )
            }
        }
    }
}


fun isLoginValid(email: String, password: String, invalidInstitutionalEmail: MutableState<Boolean>) : Boolean {
    val validEmail = "ruzzoli.1234567@studio.unibo.it"
    val validPassword = "PinkFloyd1973"

    //For validation only
    val validEmail2 = "1"
    val validPassword2 = "1"

    if (email == validEmail2 && password == validPassword2) {
        return true
    }

    val validEmailSuffix = listOf("studenti.uniroma1.it", "studio.unibo.it", "ub.edu")

    var isValidSuffixPresent = false

    for (elem in validEmailSuffix) {
        if (email.endsWith(elem)) {
            isValidSuffixPresent = true
            break
        }
    }

    if (!isValidSuffixPresent) {
        invalidInstitutionalEmail.value = true
        return false
    }

    return email == validEmail && password == validPassword
}
