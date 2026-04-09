package com.ghettodev.rutago.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ghettodev.rutago.ui.screens.DetalleParada
import com.ghettodev.rutago.ui.screens.ERutasS
import com.ghettodev.rutago.ui.screens.HomeScreen
import com.ghettodev.rutago.ui.screens.LoginScreen
import com.ghettodev.rutago.ui.screens.RegisterScreen
import com.ghettodev.rutago.ui.screens.ReportarBloqueoScreen

@Composable
fun NavigationWrapper(){
    //craer objeto para la navegacion
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {

        //pantalla login
        composable<Login>{//espera el objeto Login
            LoginScreen (//composable a mostrar
                onLoginClick = { email, password ->
                    navController.navigate(Main)//navegar al objeto main
                },
                onRegistroClick = {
                    navController.navigate(Registro)
                }
            )
        }

        //pantalla main
        composable<Main>{//espera objeto main
            HomeScreen(//composable a mostrar
                onExplorarRutas = {
                    navController.navigate(ERutasS)//navegar a erutass
                },
                onVerTodas = {
                    navController.navigate(RutasPopulares)
                }
            )
        }

        composable <ERutasS>{//espera objeto erutass
            ERutasS(//composable a mostrar
                onReporteClic = {
                    navController.navigate(Reporte)
                }
            )
        }

        composable<RutasPopulares>{
            DetalleParada(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable <Registro>{
            RegisterScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Reporte>{
            ReportarBloqueoScreen(
                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

    }
}