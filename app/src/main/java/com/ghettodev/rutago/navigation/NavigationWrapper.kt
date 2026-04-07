package com.ghettodev.rutago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ghettodev.rutago.ui.screens.HomeScreen

@Composable
fun NavigationWrapper(){
    //craer objeto para la navegacion
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Main) {
        composable<Main>{
            HomeScreen()
        }
    }
}