// MiApp.kt
package com.ghettodev.rutago

import android.app.Application
import org.maplibre.android.MapLibre

class MiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            MapLibre.getInstance(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}