package com.ghettodev.rutago.data.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.tasks.await

class LocationService(private val context: Context) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Comprueba si la app tiene permisos otorgados
    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Obtiene la ubicación actual del dispositivo
    suspend fun getCurrentLocation(): Location? {
        // Primero verifica que tiene permiso
        if (!hasLocationPermission()) {
            return null // Sin permiso, no hace nada
        }

        return try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(
                        callback: OnTokenCanceledListener
                    ): CancellationToken {
                        callback.onCanceled()
                        return this
                    }

                    override fun isCancellationRequested(): Boolean = false
                }
            ).await() // ← Aquí está el await() de kotlinx-coroutines-play-services
        } catch (e: SecurityException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}