package com.ghettodev.rutago.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

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
        startDestination = "login"
    ) {

        // 🔐 LOGIN
        composable("login") {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegistroClick = {          // ✅ bien escrito
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
        // Dentro de NavHost, en la ruta "explorar_rutas":
        composable("explorar_rutas") {
            ERutasS(
                rutaRepository = rutaRepository,   // ← pasamos el repositorio
                onReporteClic = { navController.navigate("reporte") }
            )
        }
        // 📍 DETALLE
        composable("rutas_populares") {
            RutasPopularesScreen(
                rutaRepository = rutaRepository,
                onRutaClick = { idRuta ->
                    navController.navigate("detalle_ruta/$idRuta")
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            "detalle_ruta/{idRuta}",
            arguments = listOf(navArgument("idRuta") { type = NavType.IntType })
        ) { backStackEntry ->
            val idRuta = backStackEntry.arguments?.getInt("idRuta")
            if (idRuta != null) {
                DetalleParada(
                    idRuta = idRuta,
                    rutaRepository = rutaRepository,
                    onBack = { navController.popBackStack() }
                )
            }
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