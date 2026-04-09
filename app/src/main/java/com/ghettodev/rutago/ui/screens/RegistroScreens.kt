package com.ghettodev.rutago.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ghettodev.rutago.R
import com.ghettodev.rutago.domain.validator.AuthValidator
import com.ghettodev.rutago.ui.theme.RutaGoTheme

@Composable
fun RegisterScreen(
    onRegisterClick: (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmarPasswordVisible by remember { mutableStateOf(false) }

    // Lógica de validación
    val isNombreValido = AuthValidator.isValidName(nombre)
    val isEmailValido = AuthValidator.isValidEmail(email)
    val isTelefonoValido = AuthValidator.isValidPhone(telefono)
    val isPasswordValido = AuthValidator.isValidPassword(password)
    val lasContrasenasCoinciden = password == confirmarPassword && password.isNotEmpty()

    val formularioValido = isNombreValido && isEmailValido &&
            isTelefonoValido && isPasswordValido && lasContrasenasCoinciden

    val colorPrimario = Color(0xFF34A853)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rutago),
            contentDescription = "Logo RutaGo",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(20.dp))
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Crear cuenta", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text("Regístrate para continuar", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        // NOMBRE
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre completo") },
            leadingIcon = { Icon(Icons.Default.Person, null, tint = colorPrimario) },
            modifier = Modifier.fillMaxWidth(),
            isError = nombre.isNotEmpty() && !isNombreValido,
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // EMAIL
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, null, tint = colorPrimario) },
            modifier = Modifier.fillMaxWidth(),
            isError = email.isNotEmpty() && !isEmailValido,
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // TELÉFONO
        OutlinedTextField(
            value = telefono,
            onValueChange = { if (it.length <= 10) telefono = it },
            label = { Text("Teléfono (10 dígitos)") },
            leadingIcon = { Icon(Icons.Default.Phone, null, tint = colorPrimario) },
            modifier = Modifier.fillMaxWidth(),
            isError = telefono.isNotEmpty() && !isTelefonoValido,
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // CONTRASEÑA
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = colorPrimario) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = colorPrimario
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = password.isNotEmpty() && !isPasswordValido,
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // CONFIRMAR CONTRASEÑA
        OutlinedTextField(
            value = confirmarPassword,
            onValueChange = { confirmarPassword = it },
            label = { Text("Confirmar contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = colorPrimario) },
            trailingIcon = {
                IconButton(onClick = { confirmarPasswordVisible = !confirmarPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmarPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        tint = colorPrimario
                    )
                }
            },
            visualTransformation = if (confirmarPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            isError = confirmarPassword.isNotEmpty() && !lasContrasenasCoinciden,
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        if (confirmarPassword.isNotEmpty() && !lasContrasenasCoinciden) {
            Text("Las contraseñas no coinciden", color = Color.Red, fontSize = 12.sp, modifier = Modifier.align(Alignment.Start))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÓN
        Button(
            onClick = { onRegisterClick(nombre, email, telefono, password) },
            enabled = formularioValido,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrimario,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text("Registrarse", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onLoginClick) {
            Text("¿Ya tienes cuenta? Inicia sesión", color = colorPrimario)
        }
    }
}