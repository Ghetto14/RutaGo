package com.ghettodev.rutago.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*

import com.ghettodev.rutago.data.location.LocationService
import com.ghettodev.rutago.data.repository.RutasRepository
import com.ghettodev.rutago.data.database.AppDatabase

import com.ghettodev.rutago.ui.screens.*

@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()
    val context = LocalContext.current

    // 🔥 DB
    val db = AppDatabase.getDatabase(context)

    // 🔥 Repository
    val rutaRepository = remember {
        RutasRepository(
            rutaDao = db.rutaDao(),
            paradaDao = db.paradaDao(),
            rutaParadaDao = db.rutaParadaDao(),
            parser = com.ghettodev.rutago.parser.GeoJsonParser()
        )
    }

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        // 🔐 LOGIN
        composable("login") {
            LoginScreen(
                onLoginClick = { _, _ ->
                    // 🔥 acceso permitido → main
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("registro")
                }
            )
        }

        // 🏠 HOME
        composable("main") {
            HomeScreen(
                onExplorarRutas = {
                    navController.navigate("explorar_rutas")
                },
                onVerTodas = {
                    navController.navigate("rutas_populares")
                }
            )
        }

        // 🗺️ MAPA
        composable("explorar_rutas") {
            ERutasS(
                rutaRepository = rutaRepository,
                onReporteClic = { navController.navigate("reporte") }
            )
        }

        // 📍 DETALLE
        composable("rutas_populares") {
            DetalleParada(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 📝 REGISTRO
        composable("registro") {
            RegisterScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // 🚨 REPORTE
        composable("reporte") {
            ReportarBloqueoScreen(
                onCancelar = {
                    navController.popBackStack()
                }
            )
        }
    }
}