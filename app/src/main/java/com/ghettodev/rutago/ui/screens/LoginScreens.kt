package com.ghettodev.rutago.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.domain.validator.AuthValidator
import com.ghettodev.rutago.R

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onRegisterClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val emailError = if (email.isNotEmpty()) AuthValidator.getEmailError(email) else null
    val passwordError = if (password.isNotEmpty()) AuthValidator.getPasswordError(password) else null
    val formularioValido = emailError == null && passwordError == null &&
            email.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rutago),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(20.dp))
        )
        Text("RutaGo", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, null, tint = Color(0xFF34A853)) },
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null,
            supportingText = { if (emailError != null) Text(emailError, color = MaterialTheme.colorScheme.error) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color(0xFF34A853)) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = passwordError != null,
            supportingText = { if (passwordError != null) Text(passwordError, color = MaterialTheme.colorScheme.error) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (formularioValido) {
                    Toast.makeText(context, "Iniciando sesión...", Toast.LENGTH_SHORT).show()
                    onLoginClick(email, password)
                }
            },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A853)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Iniciar sesión", fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onRegisterClick) {
            Text("¿No tienes cuenta? Regístrate", color = Color(0xFF34A853))
        }
    }
}