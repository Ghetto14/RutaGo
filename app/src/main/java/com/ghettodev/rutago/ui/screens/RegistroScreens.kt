package com.ghettodev.rutago.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.R
import com.ghettodev.rutago.domain.validator.AuthValidator

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmarVisible by remember { mutableStateOf(false) }

    // ✅ Errores individuales
    val nombreError = if (nombre.isNotEmpty()) AuthValidator.getNameError(nombre) else null
    val emailError = if (email.isNotEmpty()) AuthValidator.getEmailError(email) else null
    val telefonoError = if (telefono.isNotEmpty()) AuthValidator.getPhoneError(telefono) else null
    val passwordError = if (password.isNotEmpty()) AuthValidator.getPasswordError(password) else null
    val confirmarError = if (confirmarPassword.isNotEmpty() && password != confirmarPassword)
        "Las contraseñas no coinciden" else null

    val formularioValido = listOf(nombreError, emailError, telefonoError, passwordError, confirmarError)
        .all { it == null } && listOf(nombre, email, telefono, password, confirmarPassword)
        .all { it.isNotEmpty() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Crear cuenta", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, null, tint = Color(0xFF34A853)) },
            modifier = Modifier.fillMaxWidth(),
            isError = nombreError != null,
            supportingText = { if (nombreError != null) Text(nombreError, color = MaterialTheme.colorScheme.error) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))


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
            value = telefono,
            onValueChange = { if (it.length <= 10) telefono = it },
            label = { Text("Teléfono (10 dígitos)") },
            leadingIcon = { Icon(Icons.Default.Phone, null, tint = Color(0xFF34A853)) },
            modifier = Modifier.fillMaxWidth(),
            isError = telefonoError != null,
            supportingText = { if (telefonoError != null) Text(telefonoError, color = MaterialTheme.colorScheme.error) },
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

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color(0xFF34A853)) },
            trailingIcon = {
                IconButton(onClick = { confirmarVisible = !confirmarVisible }) {
                    Icon(
                        imageVector = if (confirmarVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (confirmarVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmarError != null,
            supportingText = { if (confirmarError != null) Text(confirmarError, color = MaterialTheme.colorScheme.error) },
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (formularioValido) {
                    Toast.makeText(context, "¡Éxito! Usuario registrado", Toast.LENGTH_LONG).show()
                    onRegisterClick(nombre, email, telefono, password)
                }
            },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A853)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Registrarse", fontWeight = FontWeight.Bold)
        }

        TextButton(onClick = onLoginClick) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF34A853))
        }
    }
}