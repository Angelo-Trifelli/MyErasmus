package com.example.myerasmus.ui.screens.authentication

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.withStyle
import com.example.myerasmus.ui.components.SelectField
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onEmailInserted: () -> Unit,
    onLoginClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    var email by remember { mutableStateOf("") }
    var invalidInstitutionalEmail = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 64.dp)
        ) {
            Text(
                text = "Create an account",
                color = Color(0xFF003399),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Use your official university email address to verify your academic affiliation and link your account.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(64.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Institutional E-mail") },
                placeholder = { Text("Your institutional E-mail") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isRegistrationValid(email, invalidInstitutionalEmail)) {
                        onEmailInserted()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF003399)),
                enabled = email.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }

        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFFC107),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Log in")
                        }
                    },
                    modifier = Modifier.clickable { onLoginClick() }
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


fun isRegistrationValid(email: String, invalidInstitutionalEmail: MutableState<Boolean>) : Boolean {
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

    return true
}
