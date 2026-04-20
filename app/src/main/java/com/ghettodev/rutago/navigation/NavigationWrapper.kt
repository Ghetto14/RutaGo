package com.ghettodev.rutago.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ghettodev.rutago.ui.screens.LoginScreen
import com.ghettodev.rutago.ui.screens.RegisterScreen
import com.ghettodev.rutago.ui.screens.HomeScreen
// Asegúrate de que estos objetos se importen de tu archivo ScreenObjects
import com.ghettodev.rutago.navigation.Login
import com.ghettodev.rutago.navigation.Registro
import com.ghettodev.rutago.navigation.Main

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onLoginClick = { email, pass ->
                    navController.navigate(Main)
                },
                onRegisterClick = {
                    navController.navigate(Registro)
                }
            )
        }

        composable<Registro> {
            RegisterScreen(
                onLoginClick = {
                    navController.popBackStack()
                },
                onRegisterClick = { n, e, t, p ->
                    navController.navigate(Login)
                }
            )
        }

        composable<Main> {
            HomeScreen()
        }
    }
}