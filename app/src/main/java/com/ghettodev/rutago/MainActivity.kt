package com.ghettodev.rutago

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ghettodev.rutago.navigation.NavigationWrapper
import com.ghettodev.rutago.ui.theme.RutaGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔍 Manejador global de crashes (para ver el error exacto)
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("RutaGo-CRASH", "===== CRASH EN MAINACTIVITY =====", throwable)
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    this,
                    "Error: ${throwable.message}\nRevisa Logcat",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        enableEdgeToEdge()

        setContent {
            RutaGoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    NavigationWrapper()
                }
            }
        }
    }
}