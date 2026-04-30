package com.ghettodev.rutago.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*

import com.ghettodev.rutago.data.location.LocationService
import com.ghettodev.rutago.data.repository.RutaRepository
import com.ghettodev.rutago.data.database.AppDatabase // 👈 AJUSTA NOMBRE SI ES DISTINTO

import com.ghettodev.rutago.ui.screens.*

@Composable
fun NavigationWrapper(){

    val navController = rememberNavController()
    val context = LocalContext.current

    // 🔥 Instancia de la DB
    val db = AppDatabase.getDatabase(context) // 👈 o getInstance()

    // 🔥 Repository con TUS DAOs
    val rutaRepository = remember {
        RutaRepository(
            rutaDao = db.rutaDao(),
            paradaDao = db.paradaDao(),
            rutaParadaDao = db.rutaParadaDao(),
            context = context
        )
    }

    NavHost(
        navController = navController,
        startDestination = Login
    ) {

        composable<Login>{
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Main)
                },
                onRegistroClick = {
                    navController.navigate(Registro)
                }
            )
        }

        composable<Main>{
            HomeScreen(
                onExplorarRutas = {
                    navController.navigate(ERutasS)
                },
                onVerTodas = {
                    navController.navigate(RutasPopulares)
                }
            )
        }

        composable<ERutasS> {

            ERutasS(
                locationService = LocationService(context),
                rutaRepository = rutaRepository, // ✅ AQUÍ SE PASA
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

        composable<Registro>{
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